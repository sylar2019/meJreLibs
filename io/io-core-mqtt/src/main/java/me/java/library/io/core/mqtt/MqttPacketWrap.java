package me.java.library.io.core.mqtt;

import com.zsinc.iot.common.pojo.DeviceGuid;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @author :  sylar
 * @FileName :  MqttPacketWrap
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
public class MqttPacketWrap implements Serializable {
    private int cmdCode;
    private String sourceDeviceType;
    private String sourceDeviceId;
    private String targetDeviceType;
    private String targetDeviceId;
    private ByteBuffer payload;

    public DeviceGuid getSourceGuid() {
        return DeviceGuid.fromString(sourceDeviceType + sourceDeviceId);
    }

    public DeviceGuid getTargetGuid() {
        return DeviceGuid.fromString(targetDeviceType + targetDeviceId);
    }

    public void setDevice(DeviceGuid srcGuid, DeviceGuid tgtGuid) {
        setSourceDevice(srcGuid);
        setTargetDevice(tgtGuid);
    }

    public void setSourceDevice(DeviceGuid deviceGuid) {
        if (deviceGuid != null) {
            setSourceDevice(deviceGuid.getDeviceTypeId(), deviceGuid.getDeviceNumber());
        }
    }

    public void setTargetDevice(DeviceGuid deviceGuid) {
        if (deviceGuid != null) {
            setTargetDevice(deviceGuid.getDeviceTypeId(), deviceGuid.getDeviceNumber());
        }
    }

    public void setSourceDevice(String sourceDeviceType, String sourceDeviceId) {
        setSourceDeviceType(sourceDeviceType);
        setSourceDeviceId(sourceDeviceId);
    }

    public void setTargetDevice(String targetDeviceType, String targetDeviceId) {
        setTargetDeviceType(targetDeviceType);
        setTargetDeviceId(targetDeviceId);
    }


    public int getCmdCode() {
        return cmdCode;
    }

    public void setCmdCode(int cmdCode) {
        this.cmdCode = cmdCode;
    }

    public String getSourceDeviceType() {
        return sourceDeviceType;
    }

    public void setSourceDeviceType(String sourceDeviceType) {
        this.sourceDeviceType = sourceDeviceType;
    }

    public String getSourceDeviceId() {
        return sourceDeviceId;
    }

    public void setSourceDeviceId(String sourceDeviceId) {
        this.sourceDeviceId = sourceDeviceId;
    }

    public String getTargetDeviceType() {
        return targetDeviceType;
    }

    public void setTargetDeviceType(String targetDeviceType) {
        this.targetDeviceType = targetDeviceType;
    }

    public String getTargetDeviceId() {
        return targetDeviceId;
    }

    public void setTargetDeviceId(String targetDeviceId) {
        this.targetDeviceId = targetDeviceId;
    }

    public ByteBuffer getPayload() {
        return payload;
    }

    public void setPayload(ByteBuffer payload) {
        this.payload = payload;
    }
}
