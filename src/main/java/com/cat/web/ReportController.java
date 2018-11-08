package com.cat.web;

import com.cat.annotation.RoleAuth;
import com.cat.module.dto.result.ResultConstant;
import com.cat.module.dto.result.Results;
import com.cat.module.enums.Role;
import com.cat.service.ReportService;
import com.cat.util.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
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
    @RoleAuth(include = Role.ADMIN)
    public Results downloadRecord(HttpServletResponse response) {
        SXSSFWorkbook workbook = reportService.getAllOrderRecordWorkbook();
        String fileName = "所有订单延期还清记录" + DateUtils.getDate() + ".xls";
        response.setContentType("application/octet-stream");
        OutputStream outputStream= null;
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"),"ISO-8859-1"));
            response.flushBuffer();
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.dispose();
        } catch (IOException e) {
            logger.info("下载报表异常", e);
            return new Results(ResultConstant.INNER_ERROR);
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                logger.info("报表流异常", e);
            }
        }
        return Results.ok();
    }
}
