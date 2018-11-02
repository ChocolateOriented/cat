package com.cat.web;

import com.cat.annotation.RoleAuth;
import com.cat.exception.ServiceException;
import com.cat.module.dto.result.ResultConstant;
import com.cat.module.dto.result.Results;
import com.cat.module.entity.ManualPayments;
import com.cat.module.enums.Role_n;
import com.cat.service.ManualPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author cyuan
 * @date 2018/10/10
 */
@RestController
@RequestMapping("/cat/v1/payment")
public class ManualPaymentController extends BaseController{

    @Autowired
    private ManualPaymentService manualPaymentService;

    /**
     * 手动还款
     * @param userId
     * @param manualPayments
     * @param bindingResult
     * @return
     */
    @PostMapping("/repay_loan")
    @RoleAuth(include = {Role_n.ADMIN})
    public Results repayLoan(@RequestHeader("User-Id") String userId, @Validated @RequestBody ManualPayments manualPayments, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Results(ResultConstant.EMPTY_PARAM, getFieldErrorsMessages(bindingResult));
        }
        try {
            manualPayments.setCreateBy(userId);
            manualPaymentService.disposeManualPayment(manualPayments);
            return Results.ok();
        } catch (ServiceException e) {
            logger.info("手动还款异常", e);
            return new Results(ResultConstant.SYSTEM_BUSY, e.getMessage());
        } catch (Exception e) {
            logger.info("手动还款系统内部异常,", e);
            return new Results(ResultConstant.INNER_ERROR);
        }
    }
}
