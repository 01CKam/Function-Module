# Module-Component

# 目录
## 支付功能
- #### [支付宝支付](#支付宝支付)
- #### [微信支付](#微信支付)
---






---
## [支付宝支付](https://github.com/01CKam/Function-Module/tree/main/%E6%94%AF%E4%BB%98%E5%8A%9F%E8%83%BD/Alipay)<a id="支付宝支付"></a>
### 项目结构
```
Alipay/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── example/
        │           └── alipay/
        │               ├── AlipayApplication.java
        │               ├── controller/
        │               │   └── AlipayController.java
        │               └── config/
        │                   └── AlipayConfig.java
        └── resources/
            ├── application.properties
            ├── templates/
            │   ├── index.html
            │   ├── order_form.html
            │   ├── return_url.html
            │   └── notify_url.html
            └── static/
                └── css/
                    └── style.css
```
### 运行项目
1. ##### 清理和打包项目
```
mvn clean package
```
2. ##### 启动项目
```
mvn spring-boot:run
```
3. ##### 访问首页
打开浏览器访问 ```http://localhost:8080/```你应该会看到一个简单的首页，上面有一个“去下单”的链接。

### 使用ngrok暴露本地端口
1. ##### 下载并安装 ngrok：
你可以从 ngrok 官方网站 下载适合你操作系统的版本。

2. ##### 启动 ngrok：
在终端或命令提示符中运行以下命令，将本地的8080端口暴露到外网：
```
./ngrok http 8080
```
3. ##### 更新支付宝配置：
ngrok会生成一个公共URL（例如 http://xxxx.ngrok.io）。你需要将这个URL更新到支付宝开放平台的应用配置中。
- 登录支付宝开放平台。
- 进入你的应用配置。
- 更新 return_url 和 notify_url 为 http://xxxx.ngrok.io/return_url 和 http://xxxx.ngrok.io/notify_url。

4. ##### 重启应用
确保你的Spring Boot应用正在运行，并且能够处理来自ngrok转发的请求。

---
## [微信支付](https://github.com/01CKam/Function-Module/tree/main/%E6%94%AF%E4%BB%98%E5%8A%9F%E8%83%BD/WeChatPay)<a id="微信支付"></a>
### 项目结构
