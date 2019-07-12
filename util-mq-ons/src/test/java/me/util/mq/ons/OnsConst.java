package me.util.mq.ons;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsConst {

    public static List<OnsConst> onsConsts = Lists.newArrayList();

    static {

        onsConsts.add(new OnsConst("envcloud",
                "公网测试",
                "LTAIVgkRm7xSeYHr",
                "QfgsIgVVitRSqddlmzin4eHChFoXTj",
                "TOP_VORTEX_TEST",
                "PID_VORTEX_TEST",
                "CID_VORTEX_TEST"));

        onsConsts.add(new OnsConst("15057182808",
                "公网测试",
                "XXXXXXXXXXXXXXXXXXXXX",
                "ujq6XTqpRNCnVvEJpvseovMTdeFxKO",
                "DRH_TEST",
                "PID_DRH_TEST",
                "CID_DRH_TEST"));


    }

    String account;
    String region;
    String accessKey;
    String secretKey;
    String topic;
    String producerId;
    String consumerId;

    public OnsConst(String account, String region) {
        this.account = account;
        this.region = region;
    }

    public OnsConst(String account, String region, String accessKey, String secretKey, String topic, String
            producerId, String consumerId) {
        this.account = account;
        this.region = region;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.topic = topic;
        this.producerId = producerId;
        this.consumerId = consumerId;
    }

    public static OnsConst getFirst() {
        return onsConsts.get(0);
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }
}
