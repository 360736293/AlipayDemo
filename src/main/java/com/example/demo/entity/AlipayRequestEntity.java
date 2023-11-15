package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mfliu
 * @date 2023/6/1 9:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlipayRequestEntity {
    /**
     * 订单号
     */
    private String out_trade_no;

    /**
     * 商品代码
     */
    private String product_code;

    /**
     * 总金额,单位元
     */
    private String total_amount;

    /**
     * 商品名称
     */
    private String subject;

    /**
     * 商品描述
     */
    private String body;
}
