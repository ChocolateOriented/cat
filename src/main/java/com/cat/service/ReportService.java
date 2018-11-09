package com.cat.service;

import com.cat.mapper.TaskLogMapper;
import com.cat.module.dto.OrderDetailsReportDto;
import com.cat.util.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by cyuan on 2018/11/2.
 */
@Service
public class ReportService {

    private static final String SHEETNAME = "所有订单操作详情";

    @Autowired
    private TaskLogMapper taskLogMapper;
    /**
     * 获取所有数据表格
     * @return
     */
    @Transactional
    public SXSSFWorkbook getAllOrderRecordWorkbook() {
        List<OrderDetailsReportDto> reportDtos = getAllOrderDetails();
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet(SHEETNAME);
        createTitle(sheet);
        int rowNum = 2;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (OrderDetailsReportDto detailsReportDto : reportDtos) {
            Row row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(detailsReportDto.getCollectorName());
            row.createCell(1).setCellValue(detailsReportDto.getCollectCycle());
            row.createCell(2).setCellValue(detailsReportDto.getProductType());
            row.createCell(3).setCellValue(detailsReportDto.getPlatformext());
            row.createCell(4).setCellValue(detailsReportDto.getOrderId());
            if (detailsReportDto.getLendTime() != null) {
                row.createCell(5).setCellValue(simpleDateFormat.format(detailsReportDto.getLendTime()));
            }
            row.createCell(6).setCellValue(detailsReportDto.getCreditamount().toString());
            row.createCell(7).setCellValue(detailsReportDto.getOverdueDays() == null ? 0 : detailsReportDto.getOverdueDays());
            if (detailsReportDto.getBorrowTime() != null) {
                row.createCell(8).setCellValue(simpleDateFormat.format(detailsReportDto.getBorrowTime()));
            }
            row.createCell(9).setCellValue(detailsReportDto.getPostponeCount() == null ? 0 : detailsReportDto.getPostponeCount());
            if (detailsReportDto.getPostponeTime() != null) {
                row.createCell(10).setCellValue(simpleDateFormat.format(detailsReportDto.getPostponeTime()));
            }
            if (detailsReportDto.getRepaymentTime() != null) {
                row.createCell(11).setCellValue(simpleDateFormat.format(detailsReportDto.getRepaymentTime()));
            }
            row.createCell(12).setCellValue(detailsReportDto.getPostponeAmount() == null ? "0" : detailsReportDto.getPostponeAmount().toString());
            if (detailsReportDto.getPayoffTime() != null) {
                row.createCell(13).setCellValue(simpleDateFormat.format(detailsReportDto.getPayoffTime()));
            }
            row.createCell(14).setCellValue(detailsReportDto.getRepaymentAmount() == null ? "0" : detailsReportDto.getRepaymentAmount().toString());
            row.createCell(15).setCellValue(detailsReportDto.getOrderStatus());
            if (detailsReportDto.getCreatedTime() != null) {
                row.createCell(16).setCellValue(simpleDateFormat.format(detailsReportDto.getCreatedTime()));
            }
            row.createCell(17).setCellValue(detailsReportDto.getMobile());
            rowNum++;
        }

        return workbook;
    }

    private List<OrderDetailsReportDto> getAllOrderDetails() {
        return taskLogMapper.getAllOrderDetails();
    }

    private void createTitle(Sheet sheet) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("报表日期");
        row.createCell(1).setCellValue(DateUtils.getDateTime());

        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("催收员");
        row1.createCell(1).setCellValue("队列");
        row1.createCell(2).setCellValue("产品名称");
        row1.createCell(3).setCellValue("产品渠道");
        row1.createCell(4).setCellValue("订单编号");
        row1.createCell(5).setCellValue("放款日期");
        row1.createCell(6).setCellValue("应催金额");
        row1.createCell(7).setCellValue("逾期天数");
        row1.createCell(8).setCellValue("借款日期");
        row1.createCell(9).setCellValue("延期次数");
        row1.createCell(10).setCellValue("延期日期");
        row1.createCell(11).setCellValue("延期到期日期");
        row1.createCell(12).setCellValue("延期金额");
        row1.createCell(13).setCellValue("还清日期");
        row1.createCell(14).setCellValue("还清金额");
        row1.createCell(15).setCellValue("订单状态");
        row1.createCell(16).setCellValue("入催日期");
        row1.createCell(17).setCellValue("手机号");
    }
}
