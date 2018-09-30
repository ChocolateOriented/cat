package com.cat.util;

/**
 * Created by cyuan on 2018/9/29.
 */
public class DesensitizationUtill {
    /**
     * 给银行卡信息脱敏---6222 **** **** 0704 970
     * @param bankNo 银行卡号
     * @return
     */
    public static String hideBankNo(String bankNo) {
        return bankNo.replaceAll("(\\d{4})\\d{8}(\\d{4})(\\d*)", "$1 **** **** $2 $3");
    }

    public static void main(String[] args) {
//        String bankNo = "6222111111110704970".;
//        System.out.println(bankNo);
    }
}
