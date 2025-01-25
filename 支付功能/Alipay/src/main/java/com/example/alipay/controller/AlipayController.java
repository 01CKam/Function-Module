package com.example.alipay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.example.alipay.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AlipayController {

    @Autowired
    private AlipayConfig alipayConfig;

    @GetMapping("/")
    public String index(Model model) {
        return "index"; // 返回index.html视图
    }

    @GetMapping("/order")
    public String orderForm(Model model) {
        return "order_form"; // 返回order_form.html视图
    }

    @PostMapping("/pay")
    public void pay(@RequestParam("out_trade_no") String outTradeNo,
                    @RequestParam("total_amount") String totalAmount,
                    @RequestParam("subject") String subject,
                    HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 创建API对应的request类
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());
        alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());

        // 填充业务参数
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\"," +
                "\"total_amount\":\"" + totalAmount + "\"," +
                "\"subject\":\"" + subject + "\"," +
                "\"body\":\"这是一个测试商品的描述\"," +
                "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        // 初始化客户端
        DefaultAlipayClient alipayClient = new DefaultAlipayClient(
                alipayConfig.getGatewayUrl(),
                alipayConfig.getAppId(),
                alipayConfig.getPrivateKey(),
                "json",
                alipayConfig.getCharset(),
                alipayConfig.getAlipayPublicKey(),
                alipayConfig.getSignType()
        );

        try {
            // 调用SDK生成表单
            String form = alipayClient.pageExecute(alipayRequest).getBody();

            response.setContentType("text/html;charset=" + alipayConfig.getCharset());
            response.getWriter().write(form); // 直接将完整的表单html输出到页面
            response.getWriter().flush();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/return_url")
    public String returnUrl(HttpServletRequest request, Model model) {
        // 获取支付宝GET过来反馈信息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>(parameterMap.size());
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String[] values = entry.getValue();
            StringBuilder valueStr = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                valueStr.append((i == values.length - 1) ? values[i] : values[i] + ",");
            }
            params.put(entry.getKey(), valueStr.toString());
        }

        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params,
                    alipayConfig.getAlipayPublicKey(),
                    alipayConfig.getCharset(),
                    alipayConfig.getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        if (signVerified) {
            // 验证成功
            model.addAttribute("message", "支付成功！");
        } else {
            // 验证失败
            model.addAttribute("message", "支付失败！");
        }

        return "return_url";
    }

    @PostMapping("/notify_url")
    @ResponseBody
    public String notifyUrl(HttpServletRequest request) {
        // 获取支付宝POST过来反馈信息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> params = new HashMap<>(parameterMap.size());
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String[] values = entry.getValue();
            StringBuilder valueStr = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                valueStr.append((i == values.length - 1) ? values[i] : values[i] + ",");
            }
            params.put(entry.getKey(), valueStr.toString());
        }

        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params,
                    alipayConfig.getAlipayPublicKey(),
                    alipayConfig.getCharset(),
                    alipayConfig.getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        if (signVerified) {
            // 验证成功
            return "success";
        } else {
            // 验证失败
            return "failure";
        }
    }
}



