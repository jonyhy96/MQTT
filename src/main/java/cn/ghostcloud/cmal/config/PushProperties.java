package cn.ghostcloud.cmal.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 项目中的推送消息 相关配置
 */
@Configuration
@ConfigurationProperties(prefix = "push")
public class PushProperties {

    private final Http http = new Http();
    private final Mqtt mqtt = new Mqtt();

    public Http getHttp() {
        return http;
    }

    public Mqtt getMqtt() {
        return mqtt;
    }

    /**
     * HTTP 接口 所需配置
     */
    private static class Http {

        private String callbackUrl;
        private String secretKey;
        private String accessId;

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public String getAccessId() {
            return accessId;
        }

        public void setAccessId(String accessId) {
            this.accessId = accessId;
        }

        public String getCallbackUrl() {
            return callbackUrl;
        }

        public void setCallbackUrl(String callbackUrl) {
            this.callbackUrl = callbackUrl;
        }
    }

    /**
     * MQTT 所需配置
     */
    public static class Mqtt{

        private String brokerUrl;
        private String username;
        private String password;
        private Boolean subscriber;
        private Boolean publisher;


        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getBrokerUrl() {
            return brokerUrl;
        }

        public void setBrokerUrl(String brokerUrl) {
            this.brokerUrl = brokerUrl;
        }

        public Boolean getSubscriber() {
            return subscriber;
        }

        public void setSubscriber(Boolean subscriber) {
            this.subscriber = subscriber;
        }

        public Boolean getPublisher() {
            return publisher;
        }

        public void setPublisher(Boolean publisher) {
            this.publisher = publisher;
        }
    }
}
