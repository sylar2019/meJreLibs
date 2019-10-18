package me.java.library.utils.event.disruptor;


/**
 * @author :  sylar
 * @FileName :  EventFactory
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
public class EventFactory implements com.lmax.disruptor.EventFactory<Event> {
    @Override
    public Event newInstance() {
        return new Event();
    }
}
