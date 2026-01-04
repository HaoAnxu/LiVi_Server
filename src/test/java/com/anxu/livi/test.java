package com.anxu.livi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 【】
 *
 * @Author: haoanxu
 * @Date: 2026/1/4
 */
public class test {
    public static void main(String[] args) {
        StringBuilder orderNoBuilder = new StringBuilder();
        orderNoBuilder.append("LIVI")
                .append(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now()))
                .append(UUID.randomUUID().toString().replace("-",""),0, 10);
        String orderNo = orderNoBuilder.toString();
        System.out.println(orderNo);
    }
}
