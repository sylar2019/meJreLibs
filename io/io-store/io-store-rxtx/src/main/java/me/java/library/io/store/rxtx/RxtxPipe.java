package me.java.library.io.store.rxtx;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.rxtx.RxtxChannel;
import io.netty.channel.rxtx.RxtxChannelConfig;
import io.netty.channel.rxtx.RxtxChannelOption;
import io.netty.channel.rxtx.RxtxDeviceAddress;
import me.java.library.io.core.pipe.AbstractPipe;
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
public class RxtxPipe extends AbstractPipe<RxtxParams, RxtxCodec> {

    public RxtxPipe(RxtxParams params, RxtxCodec codec) {
        super(params, codec);
    }

    @Override
    protected boolean onStart() throws Exception {
        RxtxUtils.preparePermisson(params.getCommPortId());

        masterLoop = new OioEventLoopGroup();
        RxtxDeviceAddress rxtxDeviceAddress = new RxtxDeviceAddress(params.getCommPortId());

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(masterLoop)
                .channel(RxtxChannel.class)
                .option(RxtxChannelOption.BAUD_RATE, params.getBaudRate())
                .option(RxtxChannelOption.DATA_BITS, RxtxChannelConfig.Databits.valueOf(params.getDataBits()))
                .option(RxtxChannelOption.STOP_BITS, RxtxChannelConfig.Stopbits.valueOf(params.getStopBits()))
                .option(RxtxChannelOption.PARITY_BIT, RxtxChannelConfig.Paritybit.valueOf(params.getParity()))
                .option(RxtxChannelOption.DTR, params.isDTR())
                .option(RxtxChannelOption.RTS, params.isRTS())
                .handler(channelInitializer);

        return bootstrap.connect(rxtxDeviceAddress).sync().isDone();
    }

}
