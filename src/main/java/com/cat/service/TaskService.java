package com.cat.service;

import com.cat.module.enums.Role_n;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cat.module.dto.*;
import com.cat.module.entity.TaskLog;

import com.cat.module.vo.DayRepaymentOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.cat.annotation.ClustersSchedule;
import com.cat.mapper.TaskLogMapper;
import com.cat.mapper.TaskMapper;
import com.cat.module.bean.Dict;
import com.cat.module.entity.Contact;
import com.cat.module.entity.Organization;
import com.cat.module.entity.Task;
import com.cat.module.entity.User;
import com.cat.module.enums.BehaviorStatus;
import com.cat.repository.OrganizationRepository;
import com.cat.repository.TaskRepository;
import com.cat.repository.UserRepository;
import com.cat.util.DateUtils;
import com.cat.util.DictUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class TaskService extends BaseService {
	@Autowired 
	private	UserRepository userRepository;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private	ContactService contactService;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private OrganizationRepository organizationRepository;
	@Autowired
	private TaskLogMapper taskLogMapper;
	@Autowired
	private	ScheduledTaskByFixedService scheduledTaskByFixedService;

	/**
	 * 获取任务列表
	 * @param taskDto
	 * @param pageNum
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	public PageResponse<TaskDto> list(TaskDto taskDto, Integer pageNum, Integer pageSize, String userId) {
		//userid值为是管理员身份,不然去查userid是否是主管
		User user = userRepository.findOne(userId);
		if(user == null){
			logger.warn("该用户不存在,userID={}",userId);
			return null;
		}
		Role_n roleN = user.getRoleN() ;
		if(roleN == null){
			logger.warn("该用户没分配角色,userID={}",userId);
			return null;
		}
		
		//是主管
		if(roleN == Role_n.ORGANIZATION_LEADER){
			taskDto.setOrganizationLeaderId(userId);
			taskDto.setCollectorId(null);
		 }
		//是催收员
		if(roleN == Role_n.COLLECTOR){
			taskDto.setCollectorId(userId);
			taskDto.setOrganizationLeaderId(null);
		}
		//进行查询
		PageHelper.startPage(pageNum, pageSize);
		List<TaskDto> list = this.findList(taskDto);
		PageInfo<TaskDto> pageInfo = new PageInfo<>(list);
		return new PageResponse<TaskDto>(list, pageNum, pageSize, pageInfo.getTotal());
		
	}
		
	public List<TaskDto> findList(TaskDto taskDto) {
		return taskMapper.findList(taskDto);
	}

	/**
	 * 根据订单Id查询task实体
	 * @param orderId
	 * @return
	 */
	public Task findTaskByOrderId(String orderId) {
		return taskRepository.findTopByOrderId(orderId);
	}

	/**
	 * 根据手机号查询最新的一条订单任务
	 * @param mobile
	 * @return
	 */
	public Task findLastOrderTaskByMobile(String mobile) {
		return taskRepository.findTopByMobileOrderByOrderIdDesc(mobile);
	}

	/**
	 * 更新催收记录结果
	 * @param orderId
	 * @param actionFeedback
	 * @param remark
	 * @param collectTime
	 */
	public void updateTaskActionFeedback(String orderId, String actionFeedback, String remark, Date collectTime) {
		taskRepository.updateTaskActionByOrderId(orderId, actionFeedback, remark, collectTime);
	}

	/**
	 * 获取所有的催收员的id和姓名
	 * @return
	 */
		public List<User> findUserList() {
		return userRepository.findAll();
	}
		/**
		 * 获取所有的机构的id和姓名
		 * @return
		 */
		public List<Organization> findOrganizationList() {
			return organizationRepository.findAll();
		}
	/**
	 * 手动分案
	 * @param userId 
	 * 
	 * @param orderIds
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = false)
	public BaseResponse assign(AssignDto assignDto, String userId) {
		User user = userRepository.findOne(userId);
		if(assignDto == null ){
			logger.warn("手动分案失败,参数为null");
			return new BaseResponse(-1, "分案失败,必要参数为空");
		}
		List<String> collectIds = assignDto.getCollectIds();
		List<String> orderIds = assignDto.getOrderIds();
		if(collectIds == null || orderIds == null || collectIds.isEmpty() || orderIds.isEmpty()){
			logger.warn("手动分案失败,订单id或催收员id为空");
			return new BaseResponse(-1, "分案失败,必要参数为空");
		}
		//校验案件是否正确
		List<Task> listOrders = taskMapper.findAndValidateTaskList(assignDto);
		if(listOrders == null || listOrders.size() != orderIds.size()){
			logger.warn("手动分案失败,案件选择规则错误");
			return new BaseResponse(-1, "分案失败,案件选择有误");
		}
		//校验催收员是否正确
		List<User> listCollects = taskMapper.findAndValidateUserList(assignDto);
		if(listCollects == null || listCollects.size() != collectIds.size()){
			logger.warn("手动分案失败,催收员选择有误");
			return new BaseResponse(-1, "分案失败,催收员选择有误");
		}
		this.assignOperation(listOrders, listCollects,user.getName());
		return BaseResponse.success();
	}
	//手动分案
	private void assignOperation(List<Task> listOrders,List<User> listCollects,String createBy) {
		List<TaskLog> taskLogs = new ArrayList<TaskLog>();
		int taskNum = listOrders.size();
		int collectorNum = listCollects.size();
		logger.info("开始手动分案,案件总共{}条,分给{}个催收员",taskNum,collectorNum);
		for (int i = 0; i < listOrders.size(); i++) {
			Task task = listOrders.get(i);
			task.setUpdateBy(createBy);
			
			int overdueDay =DateUtils.getOverdueDay(new Date(), task.getRepaymentTime());
			
			//案件从旧催收员名下移出的tasklog
			TaskLog taskLogOut = new TaskLog(task);
			taskLogOut.setOverdueDays(overdueDay);
			taskLogOut.setId(this.generateId());
			taskLogOut.setBehaviorStatus(BehaviorStatus.OUT);
			taskLogs.add(taskLogOut);
			
			//更新task的催收员信息
			User user =  listCollects.get(i % collectorNum);
			task.setCollectorId(user.getId());
			task.setCollectorName(user.getName());
			
			//案件进入新催收员名下的tasklog
			TaskLog taskLogIN = new TaskLog(task);
			taskLogIN.setOverdueDays(overdueDay);
			taskLogIN.setId(this.generateId());
			taskLogIN.setBehaviorStatus(BehaviorStatus.IN);
			taskLogs.add(taskLogIN);
			
		}
		taskLogMapper.batchInsertTaskLog(taskLogs);
		taskMapper.batchUpdateAssignTask(listOrders);
		logger.info("手动分案结束啦!!!");
	}

	public void insert(Task task) {
		taskMapper.insert(task);
	}

	public Task findByOrderId(String orderId) {
		return taskMapper.findTaskByOrderId(orderId);
	}

	public void updateTaskStatus(Task dbTask) {
		taskMapper.updateByPrimaryKey(dbTask);
	}
	
	@Scheduled(cron = "0 30 0 * * ?")
	@ClustersSchedule
	public void synAddressBook(){
		Long maxCareateTime = contactService.maxCareateTime();
		logger.info("本次通讯录同步时间戳要大于{}",maxCareateTime);
		List<String>  listCustomeId= taskMapper.findCustomeId();
		List<AddressBook> list = contactService.getAddressBook(listCustomeId,maxCareateTime == null ? 0 : maxCareateTime);
		if(list == null || list.size() == 0){
			logger.info("本次未查到通讯录信息");
			return;
		}
		logger.info("获取通讯录完成,总获取到客户的通讯录数量为{}",list.size());
		for (AddressBook addressBook : list) {
			String contacts = addressBook.getContactList();
			String customerId = addressBook.getCustomerId();
			List<Contact> contactList;
			try {
				contactList = this.parseToContactInfo(contacts);
			} catch (Exception e) {
				logger.error(customerId+"通讯录格式异常",e);
				continue;
			}
			if(contactList == null || contactList.size() == 0){
				logger.info("客户id={}未查到通讯录",addressBook.getCustomerId());
				continue;
			}
			
			contactService.deleteBycustomerId(customerId);
			for (Contact contact : contactList) {
				contact.setId(this.generateId());
				contact.setCustomerId(customerId);
				contact.setCreateTime(addressBook.getCreateTime());
			}
			try {
				
				contactService.insertAll(contactList);
			} catch (Exception e) {
				logger.error(customerId+"定时插入数据库异常",e);
			}
		}
	 logger.info("通讯录同步完成");
	}
	 /**
     * 解析成联系人对象
     * @param message
     * @return
     */
    private List<Contact> parseToContactInfo(String cocString) throws Exception {
    	List<Contact> contactList = new ArrayList<>();
    	int firstIndex = cocString.indexOf("[");
    	int lastIndex = cocString.indexOf("]");
    	if(firstIndex < 0 || lastIndex < 0){
    		return null;
    	}
    	String substring = cocString.substring(firstIndex, lastIndex+1);
        contactList = JSON.parseArray(substring, Contact.class);
        return contactList;
    }

	/**
	 * 补拿获取通讯录
	 * @param customerIds 
	 */
	public void reloadAddressBook(List<String> customerIds) {
		List<AddressBook> list = contactService.reloadAddressBook(customerIds);
		if(list == null || list.size() == 0){
			logger.info("补拿未查到通讯录信息");
			return;
		}
		logger.info("补拿获取通讯录完成数量为{}",list.size());
		for (AddressBook addressBook : list) {
			String contacts = addressBook.getContactList();
			String customerId = addressBook.getCustomerId();
			List<Contact> contactList;
			try {
				contactList = this.parseToContactInfo(contacts);
			} catch (Exception e) {
				logger.error(customerId+"通讯录格式异常,补拿",e);
				continue;
			}
			if(contactList == null || contactList.size() == 0){
				logger.info("补拿,客户id={}未查到通讯录",addressBook.getCustomerId());
				continue;
			}
			
			contactService.deleteBycustomerId(customerId);
			for (Contact contact : contactList) {
				contact.setId(this.generateId());
				contact.setCustomerId(customerId);
				contact.setCreateTime(null);
			}
			try {
				
				contactService.insertAll(contactList);
			} catch (Exception e) {
				logger.error(customerId+"补插入数据库异常",e);
			}
		}
	 logger.info("补拿通讯录同步完成");
	}
	
	/**
	 * 手动分案查询催收人员
	 * @param collectDto
	 * @return
	 */
	public List<CollectDto> findCollectList(CollectDto collectDto) {
		
		return taskMapper.findCollectList(collectDto);
	}
	
	
	public static final String PRODUCT_TYPE = "productType"; 
	
	
	/**
	 *  各产品多规则自动分案任务
	 */
	@Transactional(readOnly = false)
	@Scheduled(cron = "0 10 0 * * ?")
	@ClustersSchedule
	public void autoAssign() {
		List<Dict> dicts = DictUtils.getDictList(PRODUCT_TYPE);
		for(Dict dict : dicts){
			if(dict.getRemarks().equals("open")){
				logger.info("定时分案产品"+dict.getValue()+"-" + "开始"+ new Date());
				scheduledTaskByFixedService.autoFixedAssign(dict.getValue());
				logger.info("定时分案产品"+dict.getValue()+"-" + "结束"+ new Date());
			}else{
				logger.info("定时分案产品"+dict.getValue()+"-" + "未开启"+ new Date());
			}
		}
	}

	/**
	 * 获取催收员每天任务次数
	 * @param collectorId
	 * @return
	 */
	public List<CurrentOrderDto> getTaskCount(String collectorId) {
		return taskLogMapper.getDayTaskCount(collectorId);
	}

	public Integer getShouldPayOrder(String collectorId, String status) {
		return taskLogMapper.getShouldPayOrder(collectorId, status);
	}

	public PageInfo<DayRepaymentOrderVo> getDayOrderPage(String collectorId, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TaskLog> list = taskLogMapper.getListOfDayOrder(collectorId);
		List<DayRepaymentOrderVo> DayRepaymentOrderVo = convertToDayRepaymentOrderVoList(list);
		PageInfo<DayRepaymentOrderVo> pageInfo = new PageInfo<>(DayRepaymentOrderVo);
		return pageInfo;
	}

	private List<DayRepaymentOrderVo> convertToDayRepaymentOrderVoList(List<TaskLog> list) {
		List<DayRepaymentOrderVo> dayRepaymentOrderVos = new ArrayList<>();
		list.forEach(item -> {
			DayRepaymentOrderVo dayRepaymentOrderVo = new DayRepaymentOrderVo();
			dayRepaymentOrderVo.setOrderId(item.getOrderId());
			dayRepaymentOrderVo.setOrderStatus(item.getBehaviorStatus().name());
			dayRepaymentOrderVo.setOverdueDays(item.getOverdueDays() == null ? 0 : item.getOverdueDays());
			dayRepaymentOrderVo.setRepaymentAmount(item.getRepaymentAmount());
			dayRepaymentOrderVo.setRepaymentTime(item.getCreateTime());
			dayRepaymentOrderVos.add(dayRepaymentOrderVo);
		});
		return dayRepaymentOrderVos;
	}

	public Integer getInOrderCount(String collectorId) {
		return taskLogMapper.getInOrderCount(collectorId);
	}
}
