package me.tanyp.controller.common;

import me.tanyp.json.JSONResultModel;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by tanyp on 2018/8/12
 */
@RestControllerAdvice
public class CommonExceptionController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JSONResultModel<Object> error(Exception e){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        resultModel.setData(e);
        return resultModel;
    }
}
