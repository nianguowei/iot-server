package com.iot.utils;

import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.Map;

/**
 * Controller的默认异常处理
 *
 * @author Zhong Han
 * @since 1.0.0-SNAPSHOT
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    private static final String ERROR = "error";
    private static final String CAUSE = "cause";
    private static final String SUPPORTED = "supported";
    private static final String errCode = "errCode";

    @ExceptionHandler({MissingServletRequestParameterException.class,
            UnsatisfiedServletRequestParameterException.class,
            HttpRequestMethodNotSupportedException.class,
            ServletRequestBindingException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public
    @ResponseBody
    Map<String, Object> handleRequestException(Exception ex) {
        Map<String, Object> map = Maps.newHashMap();
        map.put(ERROR, "Request Error");
        map.put(CAUSE, ex.getMessage());
        return map;
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public
    @ResponseBody
    Map<String, Object> handleUnsupportedMediaTypeException(HttpMediaTypeNotSupportedException ex) throws IOException {
        Map<String, Object> map = Maps.newHashMap();
        map.put(ERROR, "Unsupported Media Type");
        map.put(CAUSE, ex.getLocalizedMessage());
        map.put(SUPPORTED, ex.getSupportedMediaTypes());
        return map;
    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public
//    @ResponseBody
//    Map<String, Object> handleUncaughtException(Exception ex) throws IOException {
//        Map<String, Object> map = Maps.newHashMap();
//        map.put(ERROR, "Unknown Error");
//        if (ex.getCause() != null) {
//            map.put(CAUSE, ex.getCause().getMessage());
//        } else if(ex.getMessage() != null){
//            map.put(CAUSE, ex.getMessage());
//        } else {
//            map.put(CAUSE, ex.getClass().getName());
//        }
//        return map;
//    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public
    @ResponseBody
    Map<String, Object> handleBussinessException(BusinessException ex) throws IOException {
        Map<String, Object> map = Maps.newHashMap();
        // 异常原有Message
        map.put(ERROR, ex.getMessage());
        // 自定义错误代码
        map.put(errCode, ex.getErrCode());
        // 异常原有cause
        if (ex.getCause() != null) {
            map.put(CAUSE, ex.getCause().getMessage());
        } else {
            map.put(CAUSE, ex.getMessage());
        }
        return map;
    }
}
