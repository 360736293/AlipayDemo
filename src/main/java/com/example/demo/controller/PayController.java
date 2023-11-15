package com.example.demo.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.example.demo.entity.AlipayRequestEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 支付宝的Web、Wap、App支付的三种Demo
 *
 * @author mfliu
 * @date 2023/6/1 9:00
 */
@Slf4j
@RestController
@RequestMapping("/pay")
public class PayController {
    @Value("${alipay.gatewayUrl}")
    public String gatewayUrl;
    @Value("${alipay.appId}")
    public String appId;
    @Value("${alipay.privateKey}")
    public String privateKey;
    @Value("${alipay.format}")
    public String format;
    @Value("${alipay.charset}")
    public String charset;
    @Value("${alipay.alipayPublicKey}")
    public String alipayPublicKey;
    @Value("${alipay.signType}")
    public String signType;
    @Value("${alipay.returnUrl}")
    public String returnUrl;
    @Value("${alipay.notifyUrl}")
    public String notifyUrl;
    @Value("${alipay.contentType}")
    public String contentType;

    /**
     * 支付宝Web支付
     */
    @RequestMapping("/alipayWeb")
    public void showAlipayWeb(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
        AlipayRequestEntity alipayRequestEntity = new AlipayRequestEntity(
                IdUtil.getSnowflake().nextIdStr(),
                "FAST_INSTANT_TRADE_PAY",
                "20",
                "小花",
                "真棒"
        );
        AlipayTradePagePayRequest webPayRequest = new AlipayTradePagePayRequest();
        //回调地址
        webPayRequest.setReturnUrl(returnUrl);
        //通知地址
        webPayRequest.setNotifyUrl(notifyUrl);
        webPayRequest.setBizContent(JSONUtil.parse(alipayRequestEntity).toString());
        String form = "";
        try {
            //调用SDK生成表单
            form = alipayClient.pageExecute(webPayRequest).getBody();
        } catch (AlipayApiException e) {
            log.error(e.getMessage());
        }
        response.setContentType(contentType);
        //直接将完整的表单html输出到页面
        response.getWriter().println(form);
        response.getWriter().close();
    }

    /**
     * 支付宝Wap支付（移动H5）
     */
    @RequestMapping("/alipayWap")
    public void showAlipayWap(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
        AlipayRequestEntity alipayRequestEntity = new AlipayRequestEntity(
                IdUtil.getSnowflake().nextIdStr(),
                "FAST_INSTANT_TRADE_PAY",
                "20",
                "小花",
                "真棒"
        );
        AlipayTradeWapPayRequest wapPayRequest = new AlipayTradeWapPayRequest();
        //回调地址
        wapPayRequest.setReturnUrl(returnUrl);
        //通知地址
        wapPayRequest.setNotifyUrl(notifyUrl);
        wapPayRequest.setBizContent(JSONUtil.parse(alipayRequestEntity).toString());
        String form = "";
        try {
            //调用SDK生成表单
            form = alipayClient.pageExecute(wapPayRequest).getBody();
        } catch (AlipayApiException e) {
            log.error(e.getMessage());
        }
        response.setContentType(contentType);
        //直接将完整的表单html输出到页面
        response.getWriter().println(form);
        response.getWriter().close();
    }

    /**
     * 支付宝App支付
     */
    @RequestMapping("/alipayApp")
    public ModelAndView showAlipayApp(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
        AlipayRequestEntity alipayRequestEntity = new AlipayRequestEntity(
                IdUtil.getSnowflake().nextIdStr(),
                "FAST_INSTANT_TRADE_PAY",
                "20",
                "小花",
                "真棒"
        );
        AlipayTradeAppPayRequest appPayRequest = new AlipayTradeAppPayRequest();
        //回调地址
        appPayRequest.setReturnUrl(returnUrl);
        //通知地址
        appPayRequest.setNotifyUrl(notifyUrl);
        appPayRequest.setBizContent(JSONUtil.parse(alipayRequestEntity).toString());
        String sdk = alipayClient.sdkExecute(appPayRequest).getBody();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pay");
        modelAndView.addObject("sdk", sdk);
        return modelAndView;
    }

    /**
     * 支付宝支付回调地址
     */
    @RequestMapping("/success")
    public ModelAndView success(String out_trade_no, Double total_amount) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        modelAndView.addObject("out_trade_no", out_trade_no);
        modelAndView.addObject("total_amount", total_amount);
        return modelAndView;
    }

    /**
     * 支付宝支付通知地址
     */
    @RequestMapping("/notified")
    public void notified() {
        log.info("success!");
    }
}
