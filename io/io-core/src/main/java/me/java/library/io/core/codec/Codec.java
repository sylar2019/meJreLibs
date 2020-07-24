package me.java.library.io.core.codec;

import io.netty.channel.Channel;
import me.java.library.common.Identifiable;

/**
 * File Name             :  Codec
 *
 * @author :  sylar
 * Create :  2019-10-05
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
public interface Codec extends Identifiable<String> {

    void initPipeLine(Channel channel) throws Exception;

    <V> V getHandlerAttr(String handlerKey, String attrKey, V defaultValue);
}
