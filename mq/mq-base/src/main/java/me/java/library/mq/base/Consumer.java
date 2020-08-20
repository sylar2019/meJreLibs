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
     * 获取内部原生的consumer
     *
     * @return 原生的consumer
     */
    Object getNativeConsumer();

    /**
     * 订阅
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
