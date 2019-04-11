package com.vcv.backend.configs;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "vin-decoder")
@Configuration("VinDecoderProperties")
public class VinDecoderConfig {
    private String url;
    private String apiKey;
    private String secretKey;

    public String getUrl() {
        return url;
    }
    public String getApiKey() {
        return apiKey;
    }
    public String getSecretKey() {
        return secretKey;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String info(String vin) {
        String hash = DigestUtils.shaHex("info-" + vin + "|" + apiKey + "|" + secretKey).substring(0, 10);
        return url + "/" + apiKey + "/" + hash + "/decode/info/" + vin + ".json";
    }

    public String decode(String vin) {
        String hash = DigestUtils.shaHex(vin + "|" + apiKey + "|" + secretKey).substring(0, 10);
        return url + "/" + apiKey + "/" + hash + "/decode/" + vin + ".json";
    }

    public String balance() {
        String hash = DigestUtils.shaHex("balance|" + apiKey + "|" + secretKey).substring(0, 10);
        return url + "/" + apiKey + "/" + hash + "balance.json";
    }
}
