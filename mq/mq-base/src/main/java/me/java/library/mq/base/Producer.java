package me.java.library.mq.base;

/**
 * @author :  sylar
 * @FileName :  Producer
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
public interface Producer extends Client {
    /**
     * 发送消息前需要先启动
     */
    void start();

    /**
     * 不再需要发消息时可以停用，以节省资源
     */
    void stop();

    /**
     * 发送消息
     *
     * @param message 待发送的消息
     * @return
     */
    Object send(Message message);

}
