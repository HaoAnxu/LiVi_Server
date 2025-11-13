package com.anxu.smarthomeunity;

import io.micrometer.common.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

/**
 * @Author: haoanxu
 * @Description: TODO
 * @Date: 2025/11/12 10:42
 * @Version: 1.0
 */
public class Test {
    public static void main(String[] args) {

        String deliveryTime = "2025年11月15日";
        String date = LocalDate.parse(deliveryTime,DateTimeFormatter.ofPattern("yyyy年MM月dd日"))
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(date);

        String productModel = "RWK-500-FSD(星三角)";
        if(productModel.contains("-")){
            String model = productModel.substring(0,productModel.indexOf("-"));
            System.out.println(model);
        }else {
            String model = productModel;
            System.out.println(model);
        }

        String phone = " 138 1234 5678 ";
        phone = phone.replaceAll(" ","");
        boolean isPhone = phone.matches("1[3-9]\\d{9}");
        System.out.println(isPhone);

        String salesStaff = " 张小三 ";
        salesStaff = salesStaff.trim();
        if(StringUtils.isBlank(salesStaff)){
            salesStaff = "销售人员:未知";
        }else {
            salesStaff = "销售人员:" + salesStaff;
        }
        System.out.println(salesStaff);

        String productCodes = "PROD001,PROD002,PROD003,PROD004";
        String[] split = productCodes.split(",");
        StringJoiner sj = new StringJoiner("|");
        for (String s : split) {
            sj.add(s);
        }
        System.out.println(sj);
    }
}
