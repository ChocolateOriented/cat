package com.cat.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.cat.module.entity.Contact;
import com.cat.module.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cat.annotation.ClustersSchedule;
import com.cat.manager.RaptorManager;
import com.cat.mapper.TaskMapper;
import com.cat.module.dto.AddressBook;
import com.cat.module.dto.BaseResponse;
import com.cat.module.dto.PageResponse;
import com.cat.module.dto.ReliefAmountDto;
import com.cat.module.dto.TaskDto;
import com.cat.module.entity.User;
import com.cat.module.enums.Role;
import com.cat.repository.TaskRepository;
import com.cat.repository.UserRepository;
import com.cat.util.EncryptionUtils;
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
	private RaptorManager raptorManager;
	@Value("${feignClient.raptor.secret}")
    private String secret;

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
		Role role = user.getRole() ;
		if(role == null){
			logger.warn("该用户没分配角色,userID={}",userId);
			return null;
		}
		
		//是主管
		if(role == Role.ORGANIZATION_LEADER){
			taskDto.setOrganizationId(user.getOrganizationId());
			taskDto.setCollectorId(null);
		 }
		//是催收员
		if(role == Role.COLLECTOR){
			taskDto.setCollectorId(userId);
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
		public List<TaskDto> findUserList() {
		List<TaskDto> list = new ArrayList<>();
		Iterable<User> itreable = userRepository.findAll();
		Iterator<User> iterator = itreable.iterator();
		if(iterator.hasNext()){
			TaskDto taskDto = new TaskDto();
			User next = iterator.next();
			taskDto.setCollectorId(next.getId());
			taskDto.setCollectorName(next.getName());
			list.add(taskDto);
		}
		
		return list;
	}
	/**
	 * 手动分案
	 * 
	 * @param orderIds
	 * @param userId
	 * @return
	 */
	public boolean assign(List<String> orderIds, String userId) {
		return false;
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
		List<AddressBook> list = contactService.getAddressBook(maxCareateTime == null ? 0 : maxCareateTime);
		logger.info("获取通讯录完成");
		if(list == null || list.size() == 0){
			logger.info("未查到通讯录信息");
			return;
		}
		for (AddressBook addressBook : list) {
			String contacts = addressBook.getContactList();
			String customerId = addressBook.getCustomerId();
			List<Contact> contactList = this.parseToContactInfo(contacts);
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
			contactService.insertAll(contactList);
		}
	
	}
	 /**
     * 解析成联系人对象
     * @param message
     * @return
     */
    private List<Contact> parseToContactInfo(String cocString) {
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
	 * 
	 * @param orderId
	 * @param reliefAmount
	 * @param userId 
	 * @return
	 */
	public BaseResponse reliefAmount(String orderId, BigDecimal reliefAmount, String userId) {
		if(reliefAmount == null ){
			return  new BaseResponse(-1,"金额不能为空");
		}
		User user = userRepository.findOne(userId);
		//催收员没有减免权限
		if(user.getRole() == Role.COLLECTOR){
			return  new BaseResponse(-1,"您没有权限,请联系管理员");
		}
		ReliefAmountDto reliefAmountDto = new ReliefAmountDto(reliefAmount,orderId,"贷后系统","无");
		//做签名
		String toMd5String = "number="+reliefAmount+"&bundleId="+orderId+"&creator=贷后系统&reason=无&secret="+secret;
		String sign = EncryptionUtils.md5Encode(toMd5String);
		reliefAmountDto.setSign(sign);
		BaseResponse baseResponse = null;
		try {
			 baseResponse = raptorManager.send(reliefAmountDto);
		} catch (Exception e) {
			logger.error("减免出现异常,orderId={}", orderId, e);
			 return new BaseResponse(-1,"服务出现异常,稍后再试");
		}
		taskMapper.updateReliefAmount(orderId,reliefAmount,userId);
		logger.info("减免成功订单号={},减免金额={}",orderId,reliefAmount);
		return baseResponse;
	}

}
