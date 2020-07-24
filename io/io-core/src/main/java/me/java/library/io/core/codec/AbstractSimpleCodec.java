package me.java.library.io.core.codec;

/**
 * File Name             :  AbstractSimpleCodec
 *
 * @author :  sylar
 * Create :  2019-10-14
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
public abstract class AbstractSimpleCodec extends AbstractCodecWithLogAndIdle {

    protected SimpleCmdResolver simpleCmdResolver;

    public AbstractSimpleCodec(SimpleCmdResolver simpleCmdResolver) {
        this.simpleCmdResolver = simpleCmdResolver;
    }
}
