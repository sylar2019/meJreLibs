package me.java.library.io.core.pipe.list;

import com.google.common.base.Preconditions;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.rxtx.RxtxChannel;
import io.netty.channel.rxtx.RxtxChannelConfig;
import io.netty.channel.rxtx.RxtxChannelOption;
import io.netty.channel.rxtx.RxtxDeviceAddress;
import me.java.library.io.core.bus.list.RxtxBus;
import me.java.library.io.core.codec.list.RxtxCodec;
import me.java.library.io.core.pipe.AbstractPipe;
import me.java.library.utils.base.OSInfoUtils;

import java.io.File;

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
@SuppressWarnings("ALL")
public class RxtxPipe extends AbstractPipe<RxtxBus, RxtxCodec> {

    public RxtxPipe(RxtxBus bus, RxtxCodec codec) {
        super(bus, codec);
    }

    @Override
    protected void onStart() {
        super.onStart();

        group = new OioEventLoopGroup();
        try {
            setRxtxPermisson(bus.getRxtxPath());
            RxtxDeviceAddress rxtxDeviceAddress = new RxtxDeviceAddress(bus.getRxtxPath());

            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(RxtxChannel.class)
                    .option(RxtxChannelOption.BAUD_RATE, bus.getRxtxBaud())
                    .option(RxtxChannelOption.DATA_BITS, RxtxChannelConfig.Databits.valueOf(bus.getRxtxDatabits()))
                    .option(RxtxChannelOption.STOP_BITS, RxtxChannelConfig.Stopbits.valueOf(bus.getRxtxStopbits()))
                    .option(RxtxChannelOption.PARITY_BIT, RxtxChannelConfig.Paritybit.valueOf(bus.getRxtxParitybit()))
                    .option(RxtxChannelOption.DTR, bus.getRxtxDTR())
                    .option(RxtxChannelOption.RTS, bus.getRxtxRTS())
                    .handler(getChannelInitializer());

            ChannelFuture future = b.connect(rxtxDeviceAddress).sync();
            isRunning = true;
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
            isRunning = false;
        }
    }


    static void setRxtxPermisson(String fileName) {
        if (OSInfoUtils.isLinux()
                || OSInfoUtils.isMacOS()
                || OSInfoUtils.isMacOSX()) {

            File device = new File(fileName);
            Preconditions.checkState(device.exists(), "serialport file invalid");

            /* Check access permission */
            if (!device.canRead() || !device.canWrite()) {
                try {
                    /* Missing read/write permission, trying to chmod the file */
                    Process su;
                    su = Runtime.getRuntime().exec("/system/bin/su");
                    String cmd = "chmod 666 " + device.getAbsolutePath() + "\n"
                            + "exit\n";
                    su.getOutputStream().write(cmd.getBytes());
                    if ((su.waitFor() != 0) || !device.canRead() || !device.canWrite()) {
                        throw new SecurityException();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new SecurityException();
                }
            }
        }
    }

}
