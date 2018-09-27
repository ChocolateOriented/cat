package com.cat.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cat.exception.ServiceException;
import com.cat.mapper.TaskLogMapper;
import com.cat.mapper.TaskMapper;
import com.cat.module.bean.Dict;
import com.cat.module.bean.TmpMoveCycle;
import com.cat.module.dto.DivisionUserDto;
import com.cat.module.entity.Task;
import com.cat.module.entity.TaskLog;
import com.cat.module.enums.BehaviorStatus;
import com.cat.module.enums.CollectTaskStatus;
import com.cat.repository.TaskRepository;
import com.cat.util.DateUtils;
import com.cat.util.DictUtils;
import com.cat.util.ListSortUtil;
@Service
public class ScheduledTaskService extends BaseService{
	
	public static final String  AUTOQ0_ID_1 = "autoQ0_id_1";
	public static final String  AUTOQ0_NAME_1 = "autoQ0机器人1号";
	public static final String QUALITY_GOOD = "y";
	public static final String QUALITY_ORDINARY = "n";
	
	public static final String  C0 = "Q0";      //  提醒0-0
	public static final String  C_P1 = "Q1";	 
	public static final String  P1_P2 = "Q2";	 
	public static final String  P2_P3 = "Q3";	 
	public static final String  P3_P4 = "Q4";	 
	public static final String  P4_P5 = "Q5";	 
	
	public static final String  AUTO_ADMIN = "auto_admin";	 
	
	public static final String DEBTBIZ_TYPE = "DebtBizType";  // 产品类别
	
	private static Logger logger = Logger.getLogger(ScheduledTaskService.class);
	

	@Autowired
	private TaskMapper tMisDunningTaskDao;

	@Autowired
	private TaskLogMapper tMisDunningTaskLogDao;
	
	@Autowired
	private TaskRepository taskRepository;
	
	
	private static Date toDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 计算逾期天数，不满一天按一天计算
	 * @param repaymentDate 还款日
	 * @return
	 */
	public static int GetOverdueDay(Date repaymentDate)
	{
		Date now = new Date();
		long timeSub = toDate(now).getTime()-toDate(repaymentDate).getTime();
		double dayTimes = 24*60*60*1000d;
		return (int)Math.floor(timeSub/dayTimes);
	}
	
	/**
	 *  新自动分案
	 */
	@Transactional(readOnly = false)
	@Scheduled(cron = "0 18 2 * * ?") 
	public void autoAssign() {
		switch (getDaysOfMonth(new Date())) {
			/**
			 *  小月月分案规则
			 */
			case 30:
				switch (getDays()) {
				case 1:
					/**  Q0,Q1-Q4分案   */
					this.autoAssign_Q1_Q4();
					/**  0-0提醒分案  */
//					this.autoAssignCycle(TMisDunningTask.STATUS_DUNNING,C0,"> 0");
					return;
				case 16:
					/**  Q0,Q1-Q4分案  */
					this.autoAssign_Q1_Q4();
					/**  0-0提醒分案  */
//					this.autoAssignCycle(TMisDunningTask.STATUS_DUNNING,C0,"> 0");
					return;
				default:
//					String sqlMap = "NOT BETWEEN "+ this.getCycleDict_Q0().get("begin") + " AND  " + this.getCycleDict_Q0().get("end");
					this.autoAssignCycle(CollectTaskStatus.TASK_IN_PROGRESS.toString(),C0,this.getCycleDict_Q0().get("begin"),this.getCycleDict_Q0().get("end"));
					return;
				}
			/**
			 *  大月分案规则		`
			 */
			case 31:
				switch (getDays()) {
				case 1:
					/**  Q0,Q2-Q4分案 */
					this.autoAssign_Q1_Q4();
					/**  0-0提醒分案 */
//					this.autoAssignCycle(TMisDunningTask.STATUS_DUNNING,C0,"> 0");
					return;
				case 17:
					/**  Q0,Q1-Q4分案 */
					this.autoAssign_Q1_Q4();
					/**  0-0提醒分案	*/
//					this.autoAssignCycle(TMisDunningTask.STATUS_DUNNING,C0,"> 0");
					return;
				default:
//					String sqlMap = "NOT BETWEEN "+ this.getCycleDict_Q0().get("begin") + " AND  " + this.getCycleDict_Q0().get("end");
					this.autoAssignCycle(CollectTaskStatus.TASK_IN_PROGRESS.toString(),C0,this.getCycleDict_Q0().get("begin"),this.getCycleDict_Q0().get("end"));
					return;
				}
			/**
			 *  二月分案规则
			 */
			case 28:
				switch (getDays()) {
				case 1:
					/**  Q0,Q2-Q4分案 */
					this.autoAssign_Q1_Q4();
					return;
				case 14:
					/**  Q0,Q1-Q4分案 */
					this.autoAssign_Q1_Q4();
					return;
				default:
					this.autoAssignCycle(CollectTaskStatus.TASK_IN_PROGRESS.toString(),C0,this.getCycleDict_Q0().get("begin"),this.getCycleDict_Q0().get("end"));
					return;
				}
			default:
				return;
		}
	}
	
	
	/**
	 *  Q1-Q4分案 
	 */
	public void autoAssign_Q1_Q4(){
	    /**
	     * 选择催收周期段type
	     */
		String type = getDunningCycleType();
		List<Dict> dicts = DictUtils.getDictList(type);
		/**
		 * 倒叙Q5-Q0
		 */
		ListSortUtil.sort(dicts, "label", "desc");
		
		for(Dict dict : dicts){
			if(!dict.getLabel().equals(P4_P5) && !dict.getLabel().equals(P3_P4)){
				String begin = dict.getValue().split("_")[0];
				String end = dict.getValue().split("_")[1];
				
				if(!("").equals(begin) && !("").equals(end)){
					/**
					 * 逾期分配
					 */
//					this.autoAssignCycle(TMisDunningTask.STATUS_DUNNING,dict.getLabel(),"NOT BETWEEN "+begin+" AND  "+end);
					this.autoAssignCycle(CollectTaskStatus.TASK_IN_PROGRESS.toString(),dict.getLabel(),begin,end);
					
				}else{
					logger.warn(dict.getLabel() + "队列" +dict.getValue() + "周期异常,未分案"+ new Date());
				}
			}else{
				logger.info(dict.getLabel() +"队列" +dict.getValue() + "周期不做分案操作-"+ new Date());
			}
		}
	}
	
	
	
	public TmpMoveCycle getTmpMoveCycle(String cycle){
		TmpMoveCycle tmpMoveCycle = new TmpMoveCycle();
		try {
//			String Q0 =  DictUtils.getDictValue("Q0", "dunningCycle1", "-1_0");
			if("Q0".equals(cycle)){
				tmpMoveCycle.setDatetimestart(DateUtils.getDate(-1));
				tmpMoveCycle.setDatetimeend(DateUtils.getDate(-1));
				return tmpMoveCycle;
			}else{
				switch (getDaysOfMonth(new Date())) {
				case 30:
					if(getDays() < 16){
						tmpMoveCycle.setDatetimestart(DateUtils.getMonthFirstDayDate());
						tmpMoveCycle.setDatetimeend(DateUtils.getDate(0));
						return tmpMoveCycle;
					}else{
						tmpMoveCycle.setDatetimestart(DateUtils.getDateOfMonth(16));
						tmpMoveCycle.setDatetimeend(DateUtils.getDate(0));
						return tmpMoveCycle;
					}
				case 31:
					if(getDays() < 17){
						tmpMoveCycle.setDatetimestart(DateUtils.getMonthFirstDayDate());
						tmpMoveCycle.setDatetimeend(DateUtils.getDate(0));
						return tmpMoveCycle;
					}else{
						tmpMoveCycle.setDatetimestart(DateUtils.getDateOfMonth(17));
						tmpMoveCycle.setDatetimeend(DateUtils.getDate(0));
						return tmpMoveCycle;
					}
				case 28:
					if(getDays() < 14){
						tmpMoveCycle.setDatetimestart(DateUtils.getMonthFirstDayDate());
						tmpMoveCycle.setDatetimeend(DateUtils.getDate(0));
						return tmpMoveCycle;
					}else{
						tmpMoveCycle.setDatetimestart(DateUtils.getDateOfMonth(14));
						tmpMoveCycle.setDatetimeend(DateUtils.getDate(0));
						return tmpMoveCycle;
					}
				default:
					tmpMoveCycle.setDatetimestart(DateUtils.getDate(-1));
					tmpMoveCycle.setDatetimeend(DateUtils.getDate(-1));
					return tmpMoveCycle;
				}
			}
		} catch (Exception e) {
			logger.warn("tmpMoveCycle返回失败默认赋值昨天日期"+ new Date());
			logger.error("错误信息"+e.getMessage());
			tmpMoveCycle.setDatetimestart(DateUtils.getDate(-1));
			tmpMoveCycle.setDatetimeend(DateUtils.getDate(-1));
			return tmpMoveCycle;
		}
	}
	
	/**
	 * 过期自动分案
	 */
	@Transactional(readOnly = false)
	public void autoAssignCycle(String dunningtaskstatus, String dunningcycle,String begin,String end ) {
//		List<Dict> debtBizTypes = DictUtils.getDictList(DEBTBIZ_TYPE);
		logger.info("队列：" + dunningcycle +  new Date());
//		for(Dict debtBizType : debtBizTypes){
//			String debtbiztypename = debtBizType.getLabel(); 	// 产品名称
			
			try {
				logger.info("过期分案产品-" + "开始过期催收任务"+ new Date());
		//		List<TMisDunningPeople> dunningPeoples = tMisDunningPeopleDao.findPeopleByDunningcycle(C0);
		// 		 ==========================================提醒队列逾期分配begin ==========================================
				logger.info("过期分案产品-" +"newfindDelayTaskByDunningcycle-dunningtaskstatus"+ dunningtaskstatus+ "-dunningcycle" + dunningcycle + "-begin" + begin+ "-end" + end   + new Date());
				/** 周期中的过期任务读取出日志  */
				List<TaskLog>  outDunningTaskLogs = tMisDunningTaskDao.newfindDelayTaskByDunningcycle(dunningtaskstatus,dunningcycle,begin,end);
				logger.info("过期分案产品-" +"newfindDelayTaskByDunningcycle-查询"+dunningcycle+"队列过期任务数" +outDunningTaskLogs.size()  + "条-"  + new Date());
				if(!outDunningTaskLogs.isEmpty()){
			
					Map<Long, TaskLog> inDunningTaskLogsMap = new HashMap<Long, TaskLog>();
					/** 需要修改的任务 */
					Map<String, List<Task>> mapCycleTaskNum = new HashMap<String, List<Task>>();
		
					for(TaskLog dunningTaskLog : outDunningTaskLogs){
						/**
						 * log 催收周期过期移出记录
						 */
						dunningTaskLog.setId(this.generateId());
						dunningTaskLog.setBehaviorStatus(BehaviorStatus.OUT);
						dunningTaskLog.setCreateTime(new Date());
						dunningTaskLog.setCreateBy(AUTO_ADMIN);
						/**
						 * 本次迁徙该移入的周期段
						 */
						Dict dict = this.getCycleDict2(dunningTaskLog.getOverdueDays());
						if(null == dict){
							dunningTaskLog.setBehaviorStatus(BehaviorStatus.OUT_ERROR);
							logger.warn("过期分案产品-" +  "行为状态out_error：逾期"+dunningTaskLog.getOverdueDays() +"天，无法对应周期队列，dealcode:" + dunningTaskLog.getOrderId() + "任务taskID:" + dunningTaskLog.getTaskId()+"不做分配");
							continue;
						}
						System.out.println("过期分案产品-dunningTaskLog.getOverduedays()-" + dunningTaskLog.getOverdueDays() + "dict:" + dict);
						/**
						 * 任务task修改
						 */
						Task dunningTask = new Task();
						dunningTask.setId(dunningTaskLog.getTaskId());
						dunningTask.setCollectCycle(dict.getLabel());
						dunningTask.setCollectPeriodBegin(Integer.parseInt(dict.getValue().split("_")[0]));
						dunningTask.setCollectPeriodEnd(Integer.parseInt(dict.getValue().split("_")[1]));
						dunningTask.setUpdateBy(AUTO_ADMIN);
						dunningTask.setLoanAmount(dunningTaskLog.getLoanAmount());
						/**
						 * 每个周期的任务集合
						 */
						if (mapCycleTaskNum.containsKey(dict.getLabel())) {
							mapCycleTaskNum.get(dict.getLabel()).add(dunningTask);
						} else {
							List<Task> mapTasks = new ArrayList<Task>();
							mapTasks.add(dunningTask);
							mapCycleTaskNum.put(dict.getLabel(), mapTasks);
						}
						inDunningTaskLogsMap.put(dunningTaskLog.getTaskId(), dunningTaskLog);
					}
					/** 
					 * 保存移出任务Log
					 */
					tMisDunningTaskLogDao.batchInsertTaskLog(outDunningTaskLogs);
					System.out.println("过期分案产品-tMisDunningTaskLogDao.batchInsertTaskLog-保存移出任务Log");
					
					/**  移入的任务Log集合   */
					List<TaskLog> inDunningTaskLogs = new ArrayList<TaskLog>();
					/**
					 * 循环队列的任务集合
					 */
					for (Map.Entry<String, List<Task>> entry : mapCycleTaskNum.entrySet()) {
						/**
						 * 根据队列找出催收人员集合
						 */
	//					List<TMisDunningPeople> dunningPeoples = tMisDunningPeopleDao.findPeopleByDunningcycle(entry.getKey());
						
						/**
						 * 根据周期查询催收人员按金额排序
						 */
						TmpMoveCycle tmpMoveCycle = this.getTmpMoveCycle(entry.getKey());
						System.out.println("过期分案产品-findPeopleSumcorpusamountByDunningcycle:参数entry.getKey()"+entry.getKey() 
						+ "tmpMoveCycle.getDatetimestart()" +tmpMoveCycle.getDatetimestart()+ "tmpMoveCycle.getDatetimeend()" +tmpMoveCycle.getDatetimeend() );
						List<DivisionUserDto> dunningPeoples = taskRepository.findPeopleSumcorpusamountByDunningcycle(entry.getKey());
						System.out.println("过期分案产品-findPeopleSumcorpusamountByDunningcycle:"+ dunningPeoples.size() + "个人员");
						/**
						 *  平均分配队列集合的催收人员
						 */
						List<Task> tasks = entry.getValue();
	//					int j = 0;
						List<String> dealcodes = new ArrayList<>();
						for(int i= 0 ; i < tasks.size() ; i++ ){
							Task dunningTask = (Task)tasks.get(i);
							dealcodes.add(dunningTask.getOrderId());
							/**  平均分配法    */
							int j = i % dunningPeoples.size();                            			 // 平均分配法
							
							/**  蛇形分配法    */
	//						if (i / dunningPeoples.size() % 2 == 0) {
	//							j = i % dunningPeoples.size();
	//						} else {
	//							j = dunningPeoples.size() - 1 - i % dunningPeoples.size();
	//						}
							System.out.println("过期分案产品-" + "姓名"+dunningPeoples.get(j).getName()+ "-周期总金额" + dunningPeoples.get(j).getSumCorpusAmount()+"-分配金额"+dunningTask.getLoanAmount());
							
							
							/**  任务催收人员添加    */
							dunningTask.setCollectorId(dunningPeoples.get(j).getId().toString());
							dunningTask.setCollectorName(dunningPeoples.get(j).getName());
			//				dunningTask.setDunningtaskstatus(dunningtaskstatus);
							/**  任务log 催收人员添加    */
							if(inDunningTaskLogsMap.containsKey(dunningTask.getId())){
								inDunningTaskLogsMap.get(dunningTask.getId()).setId(this.generateId());
								inDunningTaskLogsMap.get(dunningTask.getId()).setBehaviorStatus(BehaviorStatus.IN);
								inDunningTaskLogsMap.get(dunningTask.getId()).setCollectorId(dunningTask.getCollectorId());
								inDunningTaskLogsMap.get(dunningTask.getId()).setCollectorName(dunningTask.getCollectorName());
								inDunningTaskLogsMap.get(dunningTask.getId()).setCollectCycle(dunningTask.getCollectCycle());
							}else{
								inDunningTaskLogsMap.put(dunningTask.getId(), 
										new TaskLog(dunningTask.getOrderId(), 
												dunningTask.getCollectorId(),
												dunningTask.getCollectorName(),
												dunningTask.getCollectCycle(),
												BehaviorStatus.IN_WARN));
								logger.warn("过期分案产品-" + "行为状态in_warn：任务taskID:" +dunningTask.getId() + "移入" + dunningTask.getCollectCycle() + "队列" +dunningTask.getCollectorName() +"数据缺失" );
			//					continue;
							}
							
							inDunningTaskLogs.add(inDunningTaskLogsMap.get(dunningTask.getId()));
						}
						System.out.println("过期分案产品-batchUpdateExpiredTask");
						/**
						 * 批量更新每个队列的任务集合
						 */
						tMisDunningTaskDao.batchUpdateExpiredTask(tasks);
					}
					/** 
					 * 保存移入任务Log
					 */
					tMisDunningTaskLogDao.batchInsertTaskLog(inDunningTaskLogs);
		//		=====================================提醒队列逾期分配end===================================================
				}else{
					logger.info("过期分案产品-" +  dunningcycle + "队列没有过期任务！" + new Date());
				}
			} catch (Exception e) {
				logger.error("过期分案产品-" +  dunningcycle + "队列分配任务失败,全部事务回滚");
				logger.error("错误信息"+e.getMessage());
//				throw new ServiceException(e);
			} finally {
				logger.info("过期分案产品-" + dunningcycle + "队列任务结束" + new Date());
			}
//		}
	}
	
	/**
	 *  新增未生成催收任务(task)的订单
	 */
	@Transactional(readOnly = false)
	@Scheduled(cron = "0 22 2 * * ?") 
	public void autoAssignNewOrder() {
//		List<Dict> debtBizTypes = DictUtils.getDictList(DEBTBIZ_TYPE);
//		logger.info("产品-" + debtBizTypes + "个,新增案件"+ new Date());
//		for(Dict debtBizType : debtBizTypes){
//			String debtbiztypename = debtBizType.getLabel(); 	// 产品名称
			
			try {
				logger.info("开始新增催收任务"+ new Date());
				/**
				 * 根据逾期天数查询未生成任务task的订单
				 */
//				String begin_Q0 = this.getCycleDict_Q0().get("begin");
				String begin_Q0 = "-1";
				logger.info("newfingDelayOrderByNotTask_day-begin_Q0"+ begin_Q0  + new Date());
				List<TaskLog>  newDunningTaskLogs = tMisDunningTaskDao.newfingDelayOrderByNotTask(begin_Q0);
				
				logger.info("newfingDelayOrderByNotTask-查询新的逾期周期订单并生成任务" +newDunningTaskLogs.size()  + "条-"  + new Date());
	//			Map<String, List<TMisDunningPeople>> cyclePeoplemMap = this.getDunningcyclePeopleLists();
				
				if(!newDunningTaskLogs.isEmpty()){
					Map<String, List<Task>> mapCycleTaskNum = new HashMap<String, List<Task>>();
					Map<Long, TaskLog> inDunningTaskLogsMap = new HashMap<Long, TaskLog>();
					
//					/**  * auto Q0 队列 begin */
//					String autoQ0 = DictUtils.getDictValue("autoQ0","Scheduled","false");
//					String autoQ0Tag = DictUtils.getDictValue("autoQ0Tag","Scheduled","false");
//					Map<String, String> atuoQ0DealcodeMap = new HashMap<String, String>();
//					List<Task> atuoDunningTasks = new ArrayList<Task>();
//					List<TaskLog> atuoQ0DunningTaskLogs = new ArrayList<TaskLog>();
//					if(autoQ0.equals("true") || autoQ0Tag.equals("true")){
//						List<String> atuoQ0Dealcodes = tMisDunningTaskDao.findAtuoQ0Dealcode(begin_Q0,"1");
//						logger.info("产品-" + debtbiztypename + "findAtuoQ0Dealcode-autoQ0查询历史借款逾期小于1天的用户订单" +atuoQ0Dealcodes.size()  + "条"  + new Date());
//						for(String atuoQ0Dealcode : atuoQ0Dealcodes){
//							atuoQ0DealcodeMap.put(atuoQ0Dealcode, atuoQ0Dealcode);
//						}
//					}
//					/** * auto Q0 队列 end  */
					
					for(TaskLog dunningTaskLog : newDunningTaskLogs){
						/**
						 * 本次迁徙该移入的周期段
						 */
						Dict dict = this.getCycleDict2(dunningTaskLog.getOverdueDays());
						if(null == dict){
							logger.warn( "行为状态out_error：逾期"+dunningTaskLog.getOverdueDays() +"天，无法对应周期队列，dealcode:" + dunningTaskLog.getOrderId() + "任务taskID:" + dunningTaskLog.getTaskId()+"不做分配");
							continue;
						}
						logger.info("逾期天数:"+dunningTaskLog.getOverdueDays()+ "对应队列："+ dict.getLabel()  + new Date());
						
//						/**  * auto Q0 队列 begin  */
//						if(autoQ0.equals("true") && C0.equals(dict.getLabel()) && atuoQ0DealcodeMap.containsKey(dunningTaskLog.getOrderId())){
//	//						logger.info("autoQ0查询历史借款逾期小于1天的用户订单"  + new Date());
//							Task autoDunningTask = this.autoCreateNewDunningTask(dunningTaskLog, dict,debtbiztypename);
//							TaskLog autoDunningTaskLog = this.autoCreateNewDunningTaskLog(dunningTaskLog, autoDunningTask);
//							atuoDunningTasks.add(autoDunningTask);
//							atuoQ0DunningTaskLogs.add(autoDunningTaskLog);
//							continue;
//						}
//						/** * auto Q0 队列 end  */
						
						Task dunningTask = null;
						
//						/**  * auto Q0 优质用户标记  begin  */
//						if(autoQ0Tag.equals("true") && C0.equals(dict.getLabel()) && atuoQ0DealcodeMap.containsKey(dunningTaskLog.getOrderId())){
//							/**
//							 * 创建任务 (标示优质用户)
//							 */
//							dunningTask = this.createNewDunningTask(dunningTaskLog,dict,QUALITY_GOOD,debtbiztypename);
//							/**  * auto Q0 优质用户标记  end  */
//						} else {
							/**
							 * 创建任务  (标示普通用户)
							 */
							dunningTask = this.createNewDunningTask(dunningTaskLog,dict);
//						}
						/**
						 * 每个周期的任务集合
						 */
						if (mapCycleTaskNum.containsKey(dict.getLabel())) {
							mapCycleTaskNum.get(dict.getLabel()).add(dunningTask);
						} else {
							List<Task> mapTasks = new ArrayList<Task>();
							mapTasks.add(dunningTask);
							mapCycleTaskNum.put(dict.getLabel(), mapTasks);
						}
						inDunningTaskLogsMap.put(dunningTask.getId(), dunningTaskLog);
					}
					
//					/**  * auto Q0 队列 begin  */
//					if(autoQ0.equals("true") && !atuoDunningTasks.isEmpty() && !atuoQ0DunningTaskLogs.isEmpty()){
//						tMisDunningTaskDao.batchinsertTask(atuoDunningTasks);
//						logger.info("产品-" + debtbiztypename + "保存autoQ0任务" + atuoDunningTasks.size()  + "条"  + new Date());
//						tMisDunningTaskLogDao.batchInsertTaskLog(atuoQ0DunningTaskLogs);
//						logger.info("产品-" + debtbiztypename + "保存autoQ0任务log" + atuoQ0DunningTaskLogs.size()  + "条"  + new Date());
//					}
//					/** * auto Q0 队列 end  */
					
					/**  新增任务Log集合   */
					List<TaskLog> inDunningTaskLogs = new ArrayList<TaskLog>();
					/** 
					 * 循环队列任务
					 */
					for (Map.Entry<String, List<Task>> entry : mapCycleTaskNum.entrySet()) {
						/**
						 * 根据队列找出催收人员集合
						 */
	//					List<TMisDunningPeople> dunningPeoples = tMisDunningPeopleDao.findPeopleByDunningcycle(entry.getKey().toString());
						/**
						 * 根据周期查询催收人员按金额排序
						 */
						TmpMoveCycle tmpMoveCycle = this.getTmpMoveCycle(entry.getKey());
						List<DivisionUserDto> dunningPeoples = taskRepository.findPeopleSumcorpusamountByDunningcycle(entry.getKey());
						
						/**
						 * 平均分配队列集合的催收人员
						 */
						List<Task> tasks = entry.getValue();
						logger.info( "共"+ mapCycleTaskNum.entrySet().size()+"个队列，正在分配"+entry.getKey().toString()+"队列"+tasks.size()+"条，此队列有"+dunningPeoples.size()+"个催收员" + new Date());
						
	//					int j = 0;
						for(int i= 0 ; i < tasks.size() ; i++ ){  
							Task dunningTask = (Task)tasks.get(i);
							int j = i % dunningPeoples.size();
							/**  蛇形分配法    */
	//						if (i / dunningPeoples.size() % 2 == 0) {
	//							j = i % dunningPeoples.size();
	//						} else {
	//							j = dunningPeoples.size() - 1 - i % dunningPeoples.size();
	//						}
							System.out.println("姓名"+dunningPeoples.get(j).getName()+ "-周期总金额" + dunningPeoples.get(j).getSumCorpusAmount()+"-分配金额"+dunningTask.getLoanAmount());
							
							/**  任务催收人员添加    */
							dunningTask.setCollectorId(dunningPeoples.get(j).getId().toString());
							dunningTask.setCollectorName(dunningPeoples.get(j).getName());
							/**  任务log 催收人员添加    */
							if(inDunningTaskLogsMap.containsKey(dunningTask.getId())){
								inDunningTaskLogsMap.get(dunningTask.getId()).setId(this.generateId());
								inDunningTaskLogsMap.get(dunningTask.getId()).setTaskId(dunningTask.getId());
								inDunningTaskLogsMap.get(dunningTask.getId()).setBehaviorStatus(BehaviorStatus.IN);
								inDunningTaskLogsMap.get(dunningTask.getId()).setCollectorId(dunningTask.getCollectorId());
								inDunningTaskLogsMap.get(dunningTask.getId()).setCollectorName(dunningTask.getCollectorName());
								inDunningTaskLogsMap.get(dunningTask.getId()).setCollectCycle(dunningTask.getCollectCycle());
								inDunningTaskLogsMap.get(dunningTask.getId()).setCreateTime(new Date());
								inDunningTaskLogsMap.get(dunningTask.getId()).setCreateBy(AUTO_ADMIN);
							}else{
								inDunningTaskLogsMap.put(dunningTask.getId(), 
										new TaskLog(dunningTask.getOrderId(), 
												dunningTask.getCollectorId(),
												dunningTask.getCollectorName(),
												dunningTask.getCollectCycle(),
												BehaviorStatus.IN_WARN)
										);
								logger.warn( "行为状态in_warn：任务taskID:" +dunningTask.getId() + "移入" + dunningTask.getCollectCycle() + "队列" +dunningTask.getCollectorName() +"数据缺失" );
							}
							inDunningTaskLogs.add(inDunningTaskLogsMap.get(dunningTask.getId()));
						}
						/**  批量保存每个队列的任务集合    */
						tMisDunningTaskDao.batchinsertTask(tasks);
						logger.info( "分配"+entry.getKey().toString()+"队列"+tasks.size()+"条，平均分配成功" + new Date());
					}
					/** 
					 * 保存移入任务Log
					 */
					tMisDunningTaskLogDao.batchInsertTaskLog(inDunningTaskLogs);
					logger.info("任务日志记录完毕" + new Date());
				}else{
					logger.info("newfingDelayOrderByNotTask-没有新的逾期周期订单任务" + new Date());
				}
			} catch (Exception e) {
				logger.error("新增未生成催收任务(task)的订单失败,全部事务回滚",e);
				logger.error("错误信息"+e);
//				throw new ServiceException(e);
			} finally {
				logger.info("新增未生成催收任务(task)的订单任务结束" + new Date());
			}
//		}
	}
	
	/**
	 * 创建autoQ0任务
	 * @return TMisDunningTask
	 */
//	private Task autoCreateNewDunningTask(TaskLog autoTaskLog,Dict dict,String debtbiztype) throws Exception{
//		Date now = new Date();
//		Task autoTask = new Task();
//		autoTask.setId(IdGen.uuid());
//		autoTask.setDunningpeopleid(AUTOQ0_ID_1);
//		autoTask.setDunningpeoplename(AUTOQ0_NAME_1);
//		autoTask.setOrderId(autoTaskLog.getOrderId());
//		autoTask.setCapitalamount(autoTaskLog.getCorpusamount());
//		autoTask.setBegin(toDate(now));
//		autoTask.setEnd(null);
//		autoTask.setDunningcycle(dict.getLabel());
//		int min = !("").equals(dict.getValue().split("_")[0]) ?  Integer.parseInt(dict.getValue().split("_")[0]) : 0 ;
//		int max = !("").equals(dict.getValue().split("_")[1]) ?  Integer.parseInt(dict.getValue().split("_")[1]) : 0 ;
//		autoTask.setDunningperiodbegin(min);
//		autoTask.setDunningperiodend(max);
//		autoTask.setDunnedamount(0);
//		autoTask.setIspayoff(false);
//		autoTask.setReliefamount(0);
//		autoTask.setDunningtaskstatus(Task.STATUS_DUNNING);
//		autoTask.setRepaymentTime(new java.sql.Date(autoTaskLog.getRepaymenttime().getTime()));
//		autoTask.setCreateBy(new User("auto_admin"));
//		autoTask.setCreateDate(new Date());
//		autoTask.setPlatformext(autoTaskLog.getPlatformext());
//		autoTask.setDebtbiztype(debtbiztype);
//		return autoTask;
//	}
//	/**
//	 * 创建autoQ0任务log
//	 * @return TMisDunningTasklog
//	 */
//	private TaskLog autoCreateNewDunningTaskLog(TaskLog autoTaskLog,Task autoDunningTask) throws Exception{
//		autoTaskLog.setTaskId(autoDunningTask.getId());
//		autoTaskLog.setBehaviorStatus(BehaviorStatus.IN);
//		autoTaskLog.setCollectorId(autoDunningTask.getCollectorId());
//		autoTaskLog.setCollectorName(autoDunningTask.getCollectorName());
//		autoTaskLog.setCollectCycle(autoDunningTask.getCollectCycle());
//		autoTaskLog.setCreateTime(new Date());
//		autoTaskLog.setCreateBy(AUTO_ADMIN);
//		return autoTaskLog;
//	}
	
	
	/**
	 * 查询Q0队列的周期区间
	 * @return
	 */
	public Map<String, String> getCycleDict_Q0(){
		Map<String, String> map = new HashMap<String, String>();
		String value =  DictUtils.getDictValue("Q0", "dunningCycle1", "-1_0");
		String begin = value.split("_")[0];
		String end = value.split("_")[1];
		map.put("begin", begin);
		map.put("end", end);
		return map;
	}
	
	/**
	 * 是否在此区间
	 * @param current
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean rangeInDefined(int current, int min, int max)  
    {  
        return Math.max(min, current) == Math.min(current, max);  
    }  
	
    /**
     * 选择催收周期段类型
     * @return
     */
	public static String getDunningCycleType() {
		switch (getDaysOfMonth(new Date())) {
		case 30:
			switch (getDays()) {
			case 1:
				return "dunningCycle1";
			case 16:
				return "dunningCycle1";
			default:
				return "dunningCycle1";
			}
		case 31:
			switch (getDays()) {
			case 1:
				return "dunningCycle2";
			case 17:
				return "dunningCycle1";
			default:
				return "dunningCycle1";
			}
		case 28:
			switch (getDays()) {
			case 1:
				return "dunningCycle3";
			case 14:
				return "dunningCycle1";
			default:
				return "dunningCycle1";
			}
		default:
			return "";
			
		}
	}  
	
	public static void main(String[] args) {
		System.out.println("ass");
			if(rangeInDefined(   -1   , -1, -2)){
				System.out.println("a");
			}
		
		
	}
	
	/**
	 * 根据逾期天数，返回该月该日的归属队列
	 * @param current
	 * @return
	 */
	public Dict getCycleDict2(int current){
		Dict cycleDict = null;
		/**  选择催收周期段类型   */
		String type = getDunningCycleType();
		List<Dict> dicts = DictUtils.getDictList(type);
		int cyclemax = 0;
		for(Dict dict : dicts){
			if(("dunningCycle2").equals(type) && dict.getLabel().equals(C_P1)){
				continue;
			}		
			int min = !("").equals(dict.getValue().split("_")[0]) ?  Integer.parseInt(dict.getValue().split("_")[0]) : 0 ;
			int max = !("").equals(dict.getValue().split("_")[1]) ?  Integer.parseInt(dict.getValue().split("_")[1]) : 0 ;
			
			int day = 0;

			if(("dunningCycle1").equals(type) && !dict.getLabel().equals(C0)){
				switch (getDaysOfMonth(new Date())) {
				case 30:
					day = getDays() % 15 == 0 ? 15 - 1 : getDays() % 15 - 1;
					break;
				case 31:
					day = (getDays()-1) % 15 == 0 ? 15 - 1 : (getDays()-1) % 15 - 1;
					break;
				case 28:
					day = (getDays()+2) % 15 == 0 ? 15 - 1 : (getDays()+2) % 15 - 1;
					break;
				default:
					break;
				}
				if(("dunningCycle1").equals(type) && dict.getLabel().equals(C_P1)){
					max += day;
				}else{
					min += day;
					max += day;
				}
			}
			if(rangeInDefined(current, min, max)){
				cycleDict = dict;
			}
			if(max > cyclemax){
				cyclemax = max;
			}
		}
		/**  逾期天数大于全部周期段时放入Q5    */
		if(null == cycleDict && current > cyclemax){
			String val = DictUtils.getDictValue("Q5", type, "48_62");
			cycleDict = new Dict();
			cycleDict.setType(type);
			cycleDict.setValue(val);
			cycleDict.setLabel("Q5");
		}
		return cycleDict;
	}
	
	/**
	 * 创建任务
	 * @param people
	 * @param order
	 * @param period
	 * @return
	 */
	private Task createNewDunningTask(TaskLog taskLog,Dict dict) throws Exception{
		Task task = new Task();
//		Date now = new Date();
//		task.setId(IdGen.uuid());
		task.setId(taskLog.getTaskId());
//		task.setOrderId(taskLog.getOrderId());
//		task.setLoanAmount(taskLog.getLoanAmount());
		task.setCollectCycle(dict.getLabel());
		int min = !("").equals(dict.getValue().split("_")[0]) ?  Integer.parseInt(dict.getValue().split("_")[0]) : 0 ;
		int max = !("").equals(dict.getValue().split("_")[1]) ?  Integer.parseInt(dict.getValue().split("_")[1]) : 0 ;
		task.setCollectPeriodBegin(min);
		task.setCollectPeriodEnd(max);
		task.setCollectTaskStatus(CollectTaskStatus.TASK_IN_PROGRESS);
		task.setUpdateBy(AUTO_ADMIN);
//		task.setRepaymentTime(new java.sql.Date(taskLog.getRepaymentTime().getTime()));
		
//		task.setBegin(toDate(now));
//		task.setEnd(null);
//		task.setDunnedamount(0);
//		task.setIspayoff(false);
//		task.setReliefamount(0);
//		task.setCreateBy(new User("auto_admin"));
//		task.setCreateDate(new Date());
//		task.setPlatformext(taskLog.getPlatformext());
		return task;
	}
	
	/**
	 * 判断月份天数
	 * @param date
	 * @return
	 */
    public static int getDaysOfMonth(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
    }  
   
    /**
	 * 返回今天号
	 * @param date
	 * @return
	 */
    public static int getDays() {  
    	Calendar c = Calendar.getInstance();
    	int datenum = c.get(Calendar.DATE);
		return datenum;
    }  

}