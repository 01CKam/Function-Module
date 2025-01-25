package com.example.alipay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration

public class AlipayConfig {

    @Value("${alipay.app-id}")
    private String appId;

    @Value("${alipay.private-key}")
    private String privateKey;

    @Value("${alipay.alipay-public-key}")
    private String alipayPublicKey;

    @Value("${alipay.gateway-url}")
    private String gatewayUrl;

    @Value("${alipay.charset}")
    private String charset;

    @Value("${alipay.sign-type}")
    private String signType;

    @Value("${alipay.return-url}")
    private String returnUrl;

    @Value("${alipay.notify-url}")
    private String notifyUrl;

    public String getAppId() {
        return appId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public String getCharset() {
        return charset;
    }

    public String getSignType() {
        return signType;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }
}



