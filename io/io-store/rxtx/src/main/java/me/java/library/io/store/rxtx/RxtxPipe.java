package me.java.library.io.store.rxtx;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.rxtx.RxtxChannel;
import io.netty.channel.rxtx.RxtxChannelConfig;
import io.netty.channel.rxtx.RxtxChannelOption;
import io.netty.channel.rxtx.RxtxDeviceAddress;
import me.java.library.io.core.pipe.AbstractPipe;
import me.java.library.utils.rxtx.RxtxParam;
import me.java.library.utils.rxtx.RxtxUtils;

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
@SuppressWarnings({"deprecation"})
public class RxtxPipe extends AbstractPipe<RxtxBus, RxtxCodec> {
    public RxtxPipe(RxtxBus bus, RxtxCodec codec) {
        super(bus, codec);
    }

    @Override
    protected ChannelFuture onStartByNetty() throws Exception {

        RxtxParam param = bus.getRxtxParam();
        RxtxUtils.preparePermisson(param.getCommPortId());

        masterLoop = new OioEventLoopGroup();
        RxtxDeviceAddress rxtxDeviceAddress = new RxtxDeviceAddress(param.getCommPortId());

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(masterLoop)
                .channel(RxtxChannel.class)
                .option(RxtxChannelOption.BAUD_RATE, param.getBaudRate())
                .option(RxtxChannelOption.DATA_BITS, RxtxChannelConfig.Databits.valueOf(param.getDataBits()))
                .option(RxtxChannelOption.STOP_BITS, RxtxChannelConfig.Stopbits.valueOf(param.getStopBits()))
                .option(RxtxChannelOption.PARITY_BIT, RxtxChannelConfig.Paritybit.valueOf(param.getParity()))
                .option(RxtxChannelOption.DTR, param.isDTR())
                .option(RxtxChannelOption.RTS, param.isRTS())
                .handler(channelInitializer);

        return bootstrap.connect(rxtxDeviceAddress).sync();
    }
}
