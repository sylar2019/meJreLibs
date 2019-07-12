package me.util.hct.exception;


/**
 * @author :  luhao
 * @FileName :  HttpProcessException
 * @CreateDate :  2016/8/25
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) www.XXXXX.com   All Rights Reserved
 * *******************************************************************************************
 */
public class HttpProcessException extends Exception {

    public HttpProcessException(Exception e) {
        super(e);
    }

    /**
     * @param msg 消息
     */
    public HttpProcessException(String msg) {
        super(msg);
    }

    /**
     * @param message 异常消息
     * @param e       异常
     */
    public HttpProcessException(String message, Exception e) {
        super(message, e);
    }

}
