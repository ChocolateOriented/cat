package com.cat.web;

import com.cat.module.dto.result.ResultConstant;
import com.cat.module.dto.result.Results;
import com.cat.service.ReportService;
import com.cat.util.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by cyuan on 2018/11/2.
 */
@RestController
@RequestMapping("cat/v1/report")
public class ReportController extends BaseController{

    @Autowired
    private ReportService reportService;
    @GetMapping("down_operation_record")
    public Results downloadRecord(HttpServletResponse response) {
        HSSFWorkbook workbook = reportService.getAllOrderRecordWorkbook();
        String fileName = "所有订单延期还清记录-" + DateUtils.getDate() + ".xls";
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            logger.info("下载报表异常", e);
            return new Results(ResultConstant.INNER_ERROR);
        }
        return Results.ok();
    }
}
