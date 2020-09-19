package me.java.library.mq.base;

/**
 * @author :  sylar
 * @FileName :  Consumer
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) xxx.com All Rights Reserved
 * *******************************************************************************************
 */
public interface Consumer extends Client {


    /**
     * 订阅
     * 【重要】：每个消费者只允许订阅一个topic，若需订阅多个topic，请使用多个消费者实例
     *
     * @param topic           主题
     * @param messageListener 消息监听器
     * @param tags            tags, 可选参数
     */
    void subscribe(String topic, MessageListener messageListener, String... tags);


    /**
     * 取消订阅
     */
    void unsubscribe();
}
