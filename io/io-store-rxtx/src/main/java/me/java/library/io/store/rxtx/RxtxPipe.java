package me.java.library.io.store.rxtx;

import com.google.common.base.Strings;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.rxtx.RxtxChannel;
import io.netty.channel.rxtx.RxtxChannelConfig;
import io.netty.channel.rxtx.RxtxChannelOption;
import io.netty.channel.rxtx.RxtxDeviceAddress;
import me.java.library.io.common.pipe.AbstractPipe;
import me.java.library.utils.base.OSInfoUtils;
import me.java.library.utils.base.ShellUtils;

/**
 * File Name             :  RxtxPipe
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
@SuppressWarnings("deprecation")
public class RxtxPipe extends AbstractPipe<RxtxBus, RxtxCodec> {

    public RxtxPipe(RxtxBus bus, RxtxCodec codec) {
        super(bus, codec);
    }

    @Override
    protected ChannelFuture onStart() throws Exception {
        preparePermisson();

        group = new OioEventLoopGroup();
        RxtxDeviceAddress rxtxDeviceAddress = new RxtxDeviceAddress(bus.getRxtxPath());

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(RxtxChannel.class)
                .option(RxtxChannelOption.BAUD_RATE, bus.getRxtxBaud())
                .option(RxtxChannelOption.DATA_BITS, RxtxChannelConfig.Databits.valueOf(bus.getRxtxDatabits()))
                .option(RxtxChannelOption.STOP_BITS, RxtxChannelConfig.Stopbits.valueOf(bus.getRxtxStopbits()))
                .option(RxtxChannelOption.PARITY_BIT, RxtxChannelConfig.Paritybit.valueOf(bus.getRxtxParitybit()))
                .option(RxtxChannelOption.DTR, bus.getRxtxDTR())
                .option(RxtxChannelOption.RTS, bus.getRxtxRTS())
                .handler(getChannelInitializer());

        return bootstrap.connect(rxtxDeviceAddress).sync();
    }

    protected void preparePermisson() {

        System.setProperty("gnu.io.rxtx.SerialPorts", bus.getRxtxPath());
        String cmd;
        ShellUtils.CommandResult cmdRes;

        if (OSInfoUtils.isLinux() || OSInfoUtils.isMacOS() || OSInfoUtils.isMacOSX()) {
            cmd = String.format("chmod 666 %s", bus.getRxtxPath());
            cmdRes = ShellUtils.execCommand(cmd, true, true);
            if (!Strings.isNullOrEmpty(cmdRes.errorMsg)) {
                throw new SecurityException("没有串口读写权限");
            }
        }

        if (OSInfoUtils.isLinux()) {
            cmd = String.format("chmod 777 %s", "/data/local/tmp");
            cmdRes = ShellUtils.execCommand(cmd, true, true);
            if (!Strings.isNullOrEmpty(cmdRes.errorMsg)) {
                throw new SecurityException("没有android root权限");
            }
        }
    }

}
