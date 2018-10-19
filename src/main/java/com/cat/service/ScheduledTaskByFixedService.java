package com.cat.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cat.mapper.TaskLogMapper;
import com.cat.mapper.TaskMapper;
import com.cat.module.bean.Dict;
import com.cat.module.dto.DivisionUserDto;
import com.cat.module.entity.Task;
import com.cat.module.entity.TaskLog;
import com.cat.module.enums.BehaviorStatus;
import com.cat.module.enums.CollectTaskStatus;
import com.cat.repository.TaskRepository;
import com.cat.util.DictUtils;
import com.cat.util.ListSortUtil;

/**
 * 按人员固定天数分案规则
 */
@Service
public class ScheduledTaskByFixedService extends BaseService{
	
	
	public static final String  C0 = "Q0";      //  提醒0-0
	public static final String  Q1 = "Q1";	 
	public static final String  Q2 = "Q2";	 
	public static final String  Q3 = "Q3";	 
	
	public static final String  AUTO_ADMIN = "auto_admin";	 
	
	public static final String RULES_TYPE = "dunningCycleFixed";  // 规则类别
	
	private static Logger logger = Logger.getLogger(ScheduledTaskByFixedService.class);
	

	@Autowired
	private TaskMapper tMisDunningTaskDao;

	@Autowired
	private TaskLogMapper tMisDunningTaskLogDao;
	
	@Autowired
	private TaskRepository taskRepository;
	
	/**
	 * 测试使用时间
	 * @return
	 */
	public Date newDateTest(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String value =  DictUtils.getDictValue("newDateTest", "newDateTest", sdf.format(date).toString());
		System.out.println(value);
		try {
			return sdf.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}
	
	
	/**
	 *  Q1-Q3分案 
	 */
	@Transactional(readOnly = false)
	public void autoFixedAssign(String productType){
		logger.info("autoFixedAssign固定天数分案规则"+ RULES_TYPE + new Date());
	    /**
	     * 选择催收周期段type
	     */
		List<Dict> dicts = DictUtils.getDictList(RULES_TYPE);
		/**
		 * 倒叙Q3-Q0
		 */
		ListSortUtil.sort(dicts, "label", "desc");
		for(Dict dict : dicts){
			String begin = dict.getValue().split("_")[0];
			String end = dict.getValue().split("_")[1];
			if(!("").equals(begin) && !("").equals(end)){
				/**
				 * 逾期分配
				 */
				this.autoFixedAssignCycle(CollectTaskStatus.TASK_IN_PROGRESS.toString(),dict.getLabel(),begin,end,productType);
				
			}else{
				logger.warn(productType+"产品"+dict.getLabel() + "队列" +dict.getValue() + "周期异常,未分案"+ newDateTest());
			}
		}
		/**
		 * 新入催收
		 */
		this.autoFixedAssignNewOrder(productType);
	}
	
	
	/**
	 * 过期自动分案
	 */
	@Transactional(readOnly = false)
	public void autoFixedAssignCycle(String dunningtaskstatus, String dunningcycle,String begin,String end,String productType) {
		logger.info("队列：" + dunningcycle +  newDateTest());
			try {
				logger.info("过期分案"+productType+"产品-" + "开始过期催收任务"+ newDateTest());
				logger.info("过期分案"+productType+"产品-" +"newfindDelayTaskByDunningcycle-dunningtaskstatus"+ dunningtaskstatus+ "-dunningcycle" + dunningcycle + "-begin" + begin+ "-end" + end  + "-productType"+ productType + newDateTest());
				/** 周期中的过期任务读取出日志  */
				List<TaskLog>  outDunningTaskLogs = tMisDunningTaskDao.newfindDelayTaskByDunningcycle(newDateTest(),dunningtaskstatus,dunningcycle,begin,end,productType);
				logger.info("过期分案"+productType+"产品-" +"newfindDelayTaskByDunningcycle-查询"+dunningcycle+"队列过期任务数" +outDunningTaskLogs.size()  + "条-"  + newDateTest());
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
						dunningTaskLog.setCreateTime(newDateTest());
						dunningTaskLog.setCreateBy(AUTO_ADMIN);
						/**
						 * 本次迁徙该移入的周期段
						 */
						Dict dict = this.getCycleDict(dunningTaskLog.getOverdueDays());
						if(null == dict){
							dunningTaskLog.setBehaviorStatus(BehaviorStatus.OUT_ERROR);
							logger.warn("过期分案"+productType+"产品-" +  "行为状态out_error：逾期"+dunningTaskLog.getOverdueDays() +"天，无法对应周期队列，dealcode:" + dunningTaskLog.getOrderId() + "任务taskID:" + dunningTaskLog.getTaskId()+"不做分配");
							continue;
						}
						System.out.println("过期分案"+productType+"产品-dunningTaskLog.getOverduedays()-" + dunningTaskLog.getOverdueDays() + "dict:" + dict);
						/**
						 * 任务task修改
						 */
						Task dunningTask = new Task();
						dunningTask.setId(dunningTaskLog.getTaskId());
						dunningTask.setCollectCycle(dict.getLabel());
						dunningTask.setCollectPeriodBegin(Integer.parseInt(dict.getValue().split("_")[0]));
						dunningTask.setCollectPeriodEnd(Integer.parseInt(dict.getValue().split("_")[1]));
						dunningTask.setUpdateBy(AUTO_ADMIN);
						dunningTask.setUpdateTime(newDateTest());
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
					System.out.println("过期分案"+productType+"产品-tMisDunningTaskLogDao.batchInsertTaskLog-保存移出任务Log");
					
					/**  移入的任务Log集合   */
					List<TaskLog> inDunningTaskLogs = new ArrayList<TaskLog>();
					/**
					 * 循环队列的任务集合
					 */
					for (Map.Entry<String, List<Task>> entry : mapCycleTaskNum.entrySet()) {
						/**
						 * 根据周期查询催收人员按金额排序
						 */
						List<DivisionUserDto> dunningPeoples = taskRepository.findPeopleSumcorpusamountByDunningcycle(entry.getKey(),productType);
						System.out.println("产品过期"+productType+"分案-findPeopleSumcorpusamountByDunningcycle:"+ dunningPeoples.size() + "个人员");
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
							System.out.println("过期分案"+productType+"产品-" + "姓名"+dunningPeoples.get(j).getName()+ "-周期总金额" + dunningPeoples.get(j).getSumCorpusAmount()+"-分配金额"+dunningTask.getLoanAmount());
							
							
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
												dunningTask.getCollectRulesType(),
												BehaviorStatus.IN_WARN));
								logger.warn("过期分案"+productType+"产品-" + "行为状态in_warn：任务taskID:" +dunningTask.getId() + "移入" + dunningTask.getCollectCycle() + "队列" +dunningTask.getCollectorName() +"数据缺失" );
			//					continue;
							}
							
							inDunningTaskLogs.add(inDunningTaskLogsMap.get(dunningTask.getId()));
						}
						System.out.println("过期分案"+productType+"产品-batchUpdateExpiredTask");
						/**
						 * 批量更新每个队列的任务集合
						 */
						tMisDunningTaskDao.batchUpdateExpiredTask(tasks);
					}
					/** 
					 * 保存移入任务Log
					 */
					tMisDunningTaskLogDao.batchInsertTaskLog(inDunningTaskLogs);
				}else{
					logger.info("过期分案"+productType+"产品-" +  dunningcycle + "队列没有过期任务！" + newDateTest());
				}
			} catch (Exception e) {
				logger.error("过期分案"+productType+"产品-" +  dunningcycle + "队列分配任务失败,全部事务回滚");
				logger.error("错误"+productType+"信息"+e.getMessage());
				throw new RuntimeException(e);
			} finally {
				logger.info("过期分案"+productType+"产品-" + dunningcycle + "队列任务结束" + newDateTest());
			}
//		}
	}
	
	/**
	 *  新增未生成催收任务(task)的订单
	 */
	@Transactional(readOnly = false)
	public void autoFixedAssignNewOrder(String productType) {
			try {
				logger.info("产品"+productType+"开始"+productType+"新增催收任务"+ newDateTest());
				/**
				 * 根据逾期天数查询未生成任务task的订单
				 */
				String begin_Q0 = this.getCycleDict_Q0().get("begin");
				logger.info("产品"+productType+"newfingDelayOrderByNotTask_day-begin_Q0"+ begin_Q0 + "-productType"+ productType + newDateTest());
				List<TaskLog>  newDunningTaskLogs = tMisDunningTaskDao.newfingDelayOrderByNotTask(newDateTest(),begin_Q0,productType);
				
				logger.info("产品"+productType+"newfingDelayOrderByNotTask-查询"+productType+"新的逾期周期订单并生成任务" +newDunningTaskLogs.size()  + "条-"  + newDateTest());
				
				if(!newDunningTaskLogs.isEmpty()){
					Map<String, List<Task>> mapCycleTaskNum = new HashMap<String, List<Task>>();
					Map<Long, TaskLog> inDunningTaskLogsMap = new HashMap<Long, TaskLog>();
					
					for(TaskLog dunningTaskLog : newDunningTaskLogs){
						/**
						 * 本次迁徙该移入的周期段
						 */
						Dict dict = this.getCycleDict(dunningTaskLog.getOverdueDays());
						if(null == dict){
							logger.warn("产品"+productType+ "行为状态out_error：逾期"+dunningTaskLog.getOverdueDays() +"天，无法对应周期队列，dealcode:" + dunningTaskLog.getOrderId() + "任务taskID:" + dunningTaskLog.getTaskId()+"不做分配");
							continue;
						}
						logger.info("产品"+productType+"逾期"+productType+"天数:"+dunningTaskLog.getOverdueDays()+ "对应队列："+ dict.getLabel()  + newDateTest());
						
						Task  dunningTask = this.createNewDunningTask(dunningTaskLog,dict);
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
					
					
					/**  新增任务Log集合   */
					List<TaskLog> inDunningTaskLogs = new ArrayList<TaskLog>();
					/** 
					 * 循环队列任务
					 */
					for (Map.Entry<String, List<Task>> entry : mapCycleTaskNum.entrySet()) {
						/**
						 * 根据周期查询催收人员按金额排序
						 */
						List<DivisionUserDto> dunningPeoples = taskRepository.findPeopleSumcorpusamountByDunningcycle(entry.getKey(),productType);
						/**
						 * 平均分配队列集合的催收人员
						 */
						List<Task> tasks = entry.getValue();
						logger.info("产品-productType"+ productType  +  "共"+ mapCycleTaskNum.entrySet().size()+"个队列，正在分配"+entry.getKey().toString()+"队列"+tasks.size()+"条，此队列有"+dunningPeoples.size()+"个催收员" + newDateTest());
						
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
							System.out.println("产品"+productType+"姓名"+dunningPeoples.get(j).getName()+ "-周期总金额" + dunningPeoples.get(j).getSumCorpusAmount()+"-分配金额"+dunningTask.getLoanAmount());
							
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
								inDunningTaskLogsMap.get(dunningTask.getId()).setCollectRulesType(dunningTask.getCollectRulesType());
								inDunningTaskLogsMap.get(dunningTask.getId()).setCreateTime(newDateTest());
								inDunningTaskLogsMap.get(dunningTask.getId()).setCreateBy(AUTO_ADMIN);
							}else{
								inDunningTaskLogsMap.put(dunningTask.getId(), 
										new TaskLog(dunningTask.getOrderId(), 
												dunningTask.getCollectorId(),
												dunningTask.getCollectorName(),
												dunningTask.getCollectCycle(),
												dunningTask.getCollectRulesType(),
												BehaviorStatus.IN_WARN)
										);
								logger.warn( "产品"+productType+"行为状态in_warn：任务taskID:" +dunningTask.getId() + "移入" + dunningTask.getCollectCycle() + "队列" +dunningTask.getCollectorName() +"数据缺失" );
							}
							inDunningTaskLogs.add(inDunningTaskLogsMap.get(dunningTask.getId()));
						}
						/**  批量保存每个队列的任务集合    */
						tMisDunningTaskDao.batchinsertTask(tasks);
						logger.info( "产品"+productType+"分配"+entry.getKey().toString()+"队列"+tasks.size()+"条，平均分配成功" + newDateTest());
					}
					/** 
					 * 保存移入任务Log
					 */
					tMisDunningTaskLogDao.batchInsertTaskLog(inDunningTaskLogs);
					logger.info("产品"+productType+"任务日志记录完毕" + newDateTest());
				}else{
					logger.info("产品"+productType+"newfingDelayOrderByNotTask-没有新的逾期周期订单任务" + newDateTest());
				}
			} catch (Exception e) {
				logger.error("产品"+productType+"新增未生成催收任务(task)的订单失败,全部事务回滚",e);
				logger.error("产品"+productType+"错误信息"+e);
				throw new RuntimeException(e);
			} finally {
				logger.info("产品"+productType+"新增未生成催收任务(task)的订单任务结束" + newDateTest());
			}
//		}
	}
	
	
	/**
	 * 查询Q0队列的周期区间
	 * @return
	 */
	public Map<String, String> getCycleDict_Q0(){
		Map<String, String> map = new HashMap<String, String>();
		String value =  DictUtils.getDictValue("Q0", RULES_TYPE, "-1_0");
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
	 * 根据逾期天数，返回该月该日的归属队列
	 * @param current
	 * @return
	 */
	public Dict getCycleDict(int current){
		Dict cycleDict = null;
		/**  选择催收周期段类型   */
		List<Dict> dicts = DictUtils.getDictList(RULES_TYPE);
		for(Dict dict : dicts){
			int min = !("").equals(dict.getValue().split("_")[0]) ?  Integer.parseInt(dict.getValue().split("_")[0]) : 0 ;
			int max = !("").equals(dict.getValue().split("_")[1]) ?  Integer.parseInt(dict.getValue().split("_")[1]) : 0 ;
			
			if(rangeInDefined(current, min, max)){
				cycleDict = dict;
			}
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
//		Date now = newDateTest();
//		task.setId(IdGen.uuid());
		task.setId(taskLog.getTaskId());
		task.setCollectCycle(dict.getLabel());
		task.setCollectRulesType(RULES_TYPE);
		int min = !("").equals(dict.getValue().split("_")[0]) ?  Integer.parseInt(dict.getValue().split("_")[0]) : 0 ;
		int max = !("").equals(dict.getValue().split("_")[1]) ?  Integer.parseInt(dict.getValue().split("_")[1]) : 0 ;
		task.setCollectPeriodBegin(min);
		task.setCollectPeriodEnd(max);
		task.setCollectTaskStatus(CollectTaskStatus.TASK_IN_PROGRESS);
		task.setUpdateBy(AUTO_ADMIN);
		task.setUpdateTime(newDateTest());
		return task;
	}
	
   

}
