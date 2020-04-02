package me.java.library.io.core.mqtt.util;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;

import java.util.List;

/**
 * @author :  sylar
 * @FileName :  TopicUtil
 * @CreateDate :  2017/11/08
 * @Description :  1)	单播，unicast，用于节点之间的消息交互
 * /u/{TargetType}/{TargetID}{Message Payload}
 * <p>
 * 2)	广播，broadcast，主要用于状态的上报
 * /b/{SourceType}/{SourceID}{Message Payload}
 * <p>
 * 注释：
 * TargetType：目标设备类型，TargetID：目标设备编码
 * SourceType：源设备类型，SourceID：源设备编码
 * Message Payload：指的是下文提到的消息数据
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @@CopyRight : COPYRIGHT(c) iot.zs-inc.com All Rights Reserved
 * *******************************************************************************************
 */
public class TopicUtil {
    public final static String TOPIC_SEPARATOR = "/";
    public final static String TOPIC_UNICAST = "u";
    public final static String TOPIC_BROADCAST = "b";

    public static String getSourceGuid(String topic) {
        int topicSplitLength = 3;
        List<String> list = split(topic);
        if (list.size() == topicSplitLength && Objects.equal(list.get(0), TOPIC_BROADCAST)) {
            return list.get(1) + list.get(2);
        } else {
            return null;
        }
    }

    public static String getTargetGuid(String topic) {
        int topicSplitLength = 3;
        List<String> list = split(topic);
        if (list.size() == topicSplitLength && Objects.equal(list.get(0), TOPIC_UNICAST)) {
            return list.get(1) + list.get(2);
        } else {
            return null;
        }
    }

    public static boolean isUnicast(String topic) {
        List<String> list = split(topic);
        if (list.size() > 1) {
            return Objects.equal(list.get(0), TOPIC_UNICAST);
        } else {
            return false;
        }
    }

    public static boolean isBroadcast(String topic) {
        List<String> list = split(topic);
        if (list.size() > 1) {
            return Objects.equal(list.get(0), TOPIC_BROADCAST);
        } else {
            return false;
        }
    }

    public static List<String> split(String topic) {
        return Splitter.on(TOPIC_SEPARATOR).trimResults().splitToList(topic);
    }

    public static String getUnicastTopic(String guid) {
        DeviceGuid dg = DeviceGuid.fromString(guid);
        String deviceType = dg.getDeviceTypeId();
        String deviceNum = dg.getDeviceNumber();
        return TOPIC_SEPARATOR + Joiner.on(TOPIC_SEPARATOR).skipNulls().join(TOPIC_UNICAST, deviceType, deviceNum);
    }

    public static String getBroadcastTopic(String guid) {
        DeviceGuid dg = DeviceGuid.fromString(guid);
        String deviceType = dg.getDeviceTypeId();
        String deviceNum = dg.getDeviceNumber();
        return TOPIC_SEPARATOR + Joiner.on(TOPIC_SEPARATOR).join(TOPIC_BROADCAST, deviceType, deviceNum);
    }
}
