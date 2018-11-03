package com.cat.service;

import com.cat.mapper.TaskLogMapper;
import com.cat.module.dto.OrderDetailsReportDto;
import com.cat.util.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public HSSFWorkbook getAllOrderRecordWorkbook() {
        List<OrderDetailsReportDto> reportDtos = getAllOrderDetails();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(SHEETNAME);
        createTitle(sheet);
        int rowNum = 2;
        for (OrderDetailsReportDto detailsReportDto : reportDtos) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(detailsReportDto.getCollectorName());
            row.createCell(1).setCellValue(detailsReportDto.getCollectCycle());
            row.createCell(2).setCellValue(detailsReportDto.getProductType());
            row.createCell(3).setCellValue(detailsReportDto.getReviewMode());
            row.createCell(4).setCellValue(detailsReportDto.getPlatformext());
            row.createCell(5).setCellValue(detailsReportDto.getOrderId());
            row.createCell(6).setCellValue(detailsReportDto.getLendTime());
            row.createCell(7).setCellValue(detailsReportDto.getCreditamount().toString());
            row.createCell(8).setCellValue(detailsReportDto.getOverdueDays());
            row.createCell(9).setCellValue(detailsReportDto.getBorrowTime());
            row.createCell(10).setCellValue(detailsReportDto.getPostponeCount());
            row.createCell(11).setCellValue(detailsReportDto.getPostponeTime());
            row.createCell(12).setCellValue(detailsReportDto.getRepaymentTime());
            row.createCell(13).setCellValue(detailsReportDto.getPostponeAmount().toString());
            row.createCell(14).setCellValue(detailsReportDto.getPayoffTime());
            row.createCell(15).setCellValue(detailsReportDto.getRepaymentAmount().toString());
            row.createCell(16).setCellValue(detailsReportDto.getOrderStatus());
            row.createCell(17).setCellValue(detailsReportDto.getCreatedTime());
            rowNum++;
        }

        return workbook;
    }

    private List<OrderDetailsReportDto> getAllOrderDetails() {
        return taskLogMapper.getAllOrderDetails();
    }

    private void createTitle(HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("报表日期");
        row.createCell(1).setCellValue(DateUtils.getDateTime());

        HSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("催收员");
        row1.createCell(1).setCellValue("队列");
        row1.createCell(2).setCellValue("产品名称");
        row1.createCell(3).setCellValue("审核方式");
        row1.createCell(4).setCellValue("产品渠道");
        row1.createCell(5).setCellValue("订单编号");
        row1.createCell(6).setCellValue("放款日期");
        row1.createCell(7).setCellValue("应催金额");
        row1.createCell(8).setCellValue("逾期天数");
        row1.createCell(9).setCellValue("借款日期");
        row1.createCell(10).setCellValue("延期次数");
        row1.createCell(11).setCellValue("延期日期");
        row1.createCell(12).setCellValue("延期到期日期");
        row1.createCell(13).setCellValue("延期金额");
        row1.createCell(14).setCellValue("还清日期");
        row1.createCell(15).setCellValue("还清金额");
        row1.createCell(16).setCellValue("订单状态");
        row1.createCell(17).setCellValue("入催日期");
    }
}
