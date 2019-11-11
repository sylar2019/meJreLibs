package me.java.library.utils.disruptor.event;

/**
 * File Name             :  AbstractEvent
 *
 * @author :  sylar
 * Create :  2019-10-01
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public abstract class BaseEvent<Source, Content> implements Event<Source, Content> {
    protected Source source;
    protected Content content;

    public BaseEvent() {
    }

    public BaseEvent(Content content) {
        this(null, content);
    }

    public BaseEvent(Source source, Content content) {
        this.source = source;
        this.content = content;
    }

    @Override
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

}
