package com.imooc.exception;

import com.imooc.common.utils.IMOOCJSONResult;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 自定义异常处理的类
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public IMOOCJSONResult HandlerMaxUploadFileException(MaxUploadSizeExceededException ex){
        return IMOOCJSONResult.errorMsg("文件过大!");
    }

}
