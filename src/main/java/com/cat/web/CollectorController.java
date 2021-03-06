package com.cat.web;

import com.cat.module.dto.PageResponse;
import com.cat.module.dto.result.ResultConstant;
import com.cat.module.dto.result.Results;
import com.cat.module.vo.DayRepaymentOrderVo;
import com.cat.module.vo.DayTaskVo;
import com.cat.service.CollectorService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cyuan on 2018/10/26.
 */
@RestController
@RequestMapping("/cat/v1/collector")
public class CollectorController extends BaseController{

    @Autowired
    private CollectorService collectorService;
    /**
     * 获取单日催收员催收情况
     */
    @GetMapping(value = "get_day_task_info")
    public Results getDayTaskInfo(@RequestHeader("User-Id") String collectorId) {
        try {
            DayTaskVo dayTaskVo = collectorService.getDayTaskInfo(collectorId);
            return Results.ok().putData(dayTaskVo);
        } catch (Exception e) {
            logger.info("获取单日催收员催收情况接口异常", e);
            return new Results(ResultConstant.INNER_ERROR, "请稍后重试");
        }
    }

    @GetMapping("list_day_repayment_order")
    public PageResponse< DayRepaymentOrderVo > getRepaymentOrder(@RequestHeader("User-Id") String collectorId,@RequestParam(defaultValue = BaseController.DEFAULT_PAGE_NUM) Integer pageNum,
                                     @RequestParam(defaultValue = BaseController.DEFAULT_PAGE_SIZE) Integer pageSize) {
        PageInfo<DayRepaymentOrderVo> dayRepaymentOrders = collectorService.getDayRepaymentOrder(collectorId, pageNum, pageSize);
        PageResponse< DayRepaymentOrderVo > pageResp = new PageResponse<>(dayRepaymentOrders.getList(), pageNum, pageSize, dayRepaymentOrders.getTotal());
        return pageResp;
    }

}
