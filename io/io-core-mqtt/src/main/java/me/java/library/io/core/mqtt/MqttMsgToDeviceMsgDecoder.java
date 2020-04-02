package me.java.library.io.core.mqtt;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  DeviceMsgToMqttMsgEncoder
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
@ChannelHandler.Sharable
public class MqttMsgToDeviceMsgDecoder extends MessageToMessageDecoder<AbstractMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(MqttMsgToDeviceMsgDecoder.class);

    @Autowired
    ApplicationContext appCtx;

    @Autowired
    DasConfig dasConfig;

    @Autowired
    IMqttMsgResolver msgResolver;

    @Override
    protected void decode(ChannelHandlerContext ctx, AbstractMessage msg, List<Object> out) throws Exception {

        if (msg == null) {
            return;
        }
        appCtx.publishEvent(new MqttProtocolEvent(this, ctx.channel(), msg));

        List<IMsg> msgList = null;
        switch (msg.getMessageType()) {
            case AbstractMessage.CONNECT:
                msgList = decodeConnectMessage(ctx.channel(), (ConnectMessage) msg);
                break;
            case AbstractMessage.PUBLISH:
                msgList = decodePublishMessage(ctx.channel(), (PublishMessage) msg);
                break;
            default:
                break;
        }

        if (msgList != null && !msgList.isEmpty()) {
            out.addAll(msgList);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String clientId = NettyUtil.getClientId(ctx.channel());
        if (!Strings.isNullOrEmpty(clientId)) {
            appCtx.publishEvent(new MqttConnectionLostEvent(this, ctx.channel(), clientId));
        }
        LOG.debug("channl inactive for channel:{} and clientId: {}", ctx.channel(), clientId);
        super.channelInactive(ctx);
    }

    /**
     * =====================================================================================
     */


    private List<IMsg> decodeConnectMessage(Channel channel, ConnectMessage message) {

        String deviceId = message.getClientID();
        if (!DeviceGuid.checkValid(deviceId)) {
            LOG.warn("decodeConnectMessage failed for guid invalid: {}", deviceId);
            return null;
        }

        DeviceConnectionMsg msg = new DeviceConnectionMsg();
        msg.setSourceDeviceType(DeviceGuid.fromString(deviceId).getDeviceTypeId());
        msg.setSourceDeviceId(deviceId);
        msg.setDasNodeId(dasConfig.getDasNodeId());
        msg.setTerminalIp(NettyUtil.getChannelRemoteIP(channel));
        msg.setConnected(true);

        List<IMsg> msgList = Lists.newArrayList();
        msgList.add(msg);

        return msgList;
    }

    private List<IMsg> decodePublishMessage(Channel channel, PublishMessage message) {
        if (msgResolver == null) {
            return null;
        }

        String clientId = NettyUtil.getClientId(channel);
        if (!DeviceGuid.checkValid(clientId)) {
            LOG.warn("MqttMsgToDeviceMsgDecoder failed for clientId invalid: {}", clientId);
            return null;
        }

        String topic = message.getTopicName();
        if (Strings.isNullOrEmpty(topic)) {
            LOG.warn("MqttMsgToDeviceMsgDecoder failed for topic invalid: {}", topic);
            return null;
        }

        String tgtGuid = TopicUtil.getTargetGuid(topic);

        //必须要duplicate，否则会影响ProtocolProcessor的处理
        ByteBuffer payload = message.getPayload().duplicate();
        byte[] srcGuidBytes = new byte[DeviceGuid.GUID_LENGTH];
        payload.get(srcGuidBytes);
        String srcGuid = new String(srcGuidBytes, Charsets.UTF_8);
        int cmdCode = ByteUtils.toInt(payload.get());

        //去除 guid + cmdCode, 共18个字节
        int offset = DeviceGuid.GUID_LENGTH + 1;
        byte[] bytes = payload.array();
        payload = ByteBuffer.wrap(bytes, offset, bytes.length - offset).order(ByteOrder.LITTLE_ENDIAN)
                .asReadOnlyBuffer();
        MqttPacketWrap wrap = new MqttPacketWrap();
        wrap.setCmdCode(cmdCode);
        wrap.setDevice(DeviceGuid.fromString(srcGuid), DeviceGuid.fromString(tgtGuid));
        wrap.setPayload(payload);


        return Lists.newArrayList(msgResolver.wrapToMsg(wrap));
    }

}
