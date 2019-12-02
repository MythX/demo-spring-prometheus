package com.amoser.demometrics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sqs")
public class LocalStackProperties {

    private String url;
    private int maxMessage = 10;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMaxMessage() {
        return maxMessage;
    }

    public void setMaxMessage(int maxMessage) {
        this.maxMessage = maxMessage;
    }
}
