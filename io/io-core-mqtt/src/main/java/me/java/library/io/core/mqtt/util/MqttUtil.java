package me.java.library.io.core.mqtt.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttQoS;
import me.java.library.io.core.mqtt.MqttPacketWrap;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author :  sylar
 * @FileName :  MqttUtil
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @@CopyRight : COPYRIGHT(c) iot.zs-inc.com All Rights Reserved
 * *******************************************************************************************
 */
public class MqttUtil {

    public static MqttPublishMessage createPublishMessage(MqttPacketWrap mqttPacketWrap) {
        return createPublishMessage(mqttPacketWrap, MqttQoS.AT_MOST_ONCE);
    }

    public static MqttPublishMessage createPublishMessage(MqttPacketWrap mqttPacketWrap, MqttQoS qosType) {
        String sourceGuid = mqttPacketWrap.getSourceGuid().getGuid();
        String targetGuid = mqttPacketWrap.getTargetGuid().getGuid();

        Preconditions.checkNotNull(sourceGuid, "sourceGuid is null");
        Preconditions.checkState(sourceGuid.length() == DeviceGuid.GUID_LENGTH, "sourceGuid length error");

        byte[] bytes = mqttPacketWrap.getPayload().array();

        int count = DeviceGuid.GUID_LENGTH + 1 + bytes.length;
        ByteBuffer payload = ByteBuffer.allocate(count).order(ByteOrder.LITTLE_ENDIAN);
        payload.put(sourceGuid.getBytes());
        payload.put((byte) mqttPacketWrap.getCmdCode());
        payload.put(bytes);
        payload.flip();

        String topicName = Strings.isNullOrEmpty(targetGuid)
                ? TopicUtil.getBroadcastTopic(sourceGuid)
                : TopicUtil.getUnicastTopic(targetGuid);

        MqttPublishMessage publishMessage = new MqttPublishMessage();
        publishMessage.setTopicName(topicName);
        publishMessage.setPayload(payload);
        publishMessage.setQos(qosType);
        return publishMessage;
    }

}
