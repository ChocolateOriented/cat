package com.cat.service;

import com.cat.module.dto.CurrentOrderDto;
import com.cat.module.entity.Organization;
import com.cat.module.entity.User;
import com.cat.module.enums.BehaviorStatus;
import com.cat.module.enums.OrderStatus;
import com.cat.module.vo.DayRepaymentOrderVo;
import com.cat.module.vo.DayTaskVo;
import com.cat.repository.ActionRepository;
import com.cat.repository.OrganizationRepository;
import com.cat.repository.UserRepository;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cyuan on 2018/10/26.
 */
@Service
public class CollectorService {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private ActionRepository actionRepository;
    /**
     * 获取催收员当天任务详情
     * @param collectorId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public DayTaskVo getDayTaskInfo(String collectorId) {
        //获取催收员信息
        User one = userRepository.findOne(collectorId);
        if (one == null) {
            throw new RuntimeException("催收员不存在");
        }
        Organization organization = new Organization();
        if (one.getOrganizationId() == null) {
            organization.setName("未指定机构");
        } else {
            organization = organizationRepository.findOne(one.getOrganizationId());
            if (organization == null) {
                organization = new Organization();
            }
        }

        DayTaskVo dayTask = new DayTaskVo();
        //获取当日订单
        List<CurrentOrderDto> currentOrder = taskService.getTaskCount(collectorId);
        //已完成的订单
        CurrentOrderDto finishedOrder = getStatusOrder(currentOrder, BehaviorStatus.FINISHED.name());
        //延期的订单
        CurrentOrderDto postponeOrder = getStatusOrder(currentOrder, BehaviorStatus.POSTPONE.name());
        Integer inOrder = taskService.getInOrderCount(collectorId);
//        //所有新增的订单
//        CurrentOrderDto inOrder = getStatusOrder(currentOrder, BehaviorStatus.IN.name());
//        //出去的订单
//        CurrentOrderDto outOrder = getStatusOrder(currentOrder, BehaviorStatus.OUT.name());
//        //真实新增的订单=所有新增的订单-移除的订单
//        inOrder.setSumRepaymentAmount(inOrder.getSumRepaymentAmount().subtract(outOrder.getSumRepaymentAmount()));
//        inOrder.setCount(inOrder.getCount() - outOrder.getCount());


        //获取应催订单
        Integer count = taskService.getShouldPayOrder(collectorId, OrderStatus.PAYMENT.name());

        //获取单日催收记录次数
        int actionCount = (int)actionRepository.getCurrentActionCount(collectorId);
        //今日还款订单
        int allPayCount = finishedOrder.getCount() + postponeOrder.getCount();
        //计算当天比率:今日已处理订单/(当前应催订单+今日已还清订单+今日已续期订单)
        BigDecimal percent = BigDecimal.ZERO;
        if ((count + allPayCount) != 0) {
            percent =  new BigDecimal((double)(actionCount * 100)/ (count + allPayCount)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        dayTask.setCollectorId(collectorId);
        dayTask.setCollectorName(one.getName());
        dayTask.setOrganizationName(organization.getName() == null ? "指定机构不存在" : organization.getName());
        dayTask.setTaskPeriod(one.getCollectCycle());
        dayTask.setPayoffOrder(finishedOrder.getCount());
        dayTask.setPostponeOrder(postponeOrder.getCount());
        //催回总金额=完成总金额+延期总金额
        dayTask.setCollectAmount(postponeOrder.getSumRepaymentAmount().add(finishedOrder.getSumRepaymentAmount()));
        dayTask.setNewOrder(inOrder);
        //今日已还款订单 = 完成订单数量 + 延期订单数量
        dayTask.setRepaymentOrder(allPayCount);
        //应催订单总数量
        dayTask.setShouldPushOrder(count);
        dayTask.setPayoffAmount(finishedOrder.getSumRepaymentAmount());
        dayTask.setPostponeAmount(postponeOrder.getSumRepaymentAmount());
        //催收记录
        dayTask.setDealOrder(actionCount);
        //百分比
        dayTask.setPercent(percent);

        return dayTask;

    }

    /**
     * 根据订单行为状态获取List里面对应的订单
     * @param currentOrderDtos
     * @param status
     * @return
     */
    private CurrentOrderDto getStatusOrder(List<CurrentOrderDto> currentOrderDtos, String status) {
        if (currentOrderDtos == null) {
            return initCurrentOrder(status);
        } else {
            List<CurrentOrderDto> collect = currentOrderDtos.stream().filter(item -> item.checkStatus(status)).collect(Collectors.toList());
            if (collect.isEmpty()) {
                return initCurrentOrder(status);
            } else {
                return collect.get(0);
            }
        }
    }

    /**
     * 初始化订单
     * @param status
     * @return
     */
    private CurrentOrderDto initCurrentOrder(String status) {
        CurrentOrderDto currentOrderDto = new CurrentOrderDto();
        currentOrderDto.setBehaviorStatus(status);
        currentOrderDto.setCount(0);
        currentOrderDto.setSumRepaymentAmount(BigDecimal.ZERO);
        return currentOrderDto;
    }

    public PageInfo<DayRepaymentOrderVo> getDayRepaymentOrder(String collectorId, Integer pageNum, Integer pageSize) {
        PageInfo<DayRepaymentOrderVo> orders = taskService.getDayOrderPage(collectorId, pageNum, pageSize);
        return orders;
    }
}
