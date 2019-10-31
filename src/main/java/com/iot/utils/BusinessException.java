package com.iot.utils;

/**
 * 业务异常类，处理自定义消息代码和自定义消息
 *
 * @author Peng Wang
 * @since 1.0.0-SNAPSHOT
 */
public class BusinessException extends Exception {

    /** 自定义错误代码 */
    private final int errCode;

    /**
     * 构建自定义错误代码和自定义错误消息
     * @param errCode
     * @param message
     */
    public BusinessException(int errCode, String message) {
        super(message);
        this.errCode = errCode;
    }


    /**
     *
     * @param errCode
     * @param message 异常原有Message
     * @param cause  异常原有cause
     */
    public BusinessException(int errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }

    @Override
    public String toString() {
        String s = getClass().getName();
        String message = getLocalizedMessage();
        return (message != null) ? (s + ": " + message + " " + errCode) : s;
    }


    public int getErrCode() {
        return errCode;
    }


}
