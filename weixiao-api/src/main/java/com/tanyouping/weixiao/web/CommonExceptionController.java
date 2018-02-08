package com.tanyouping.weixiao.web;

import com.tanyouping.weixiao.json.JSONResultModel;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CommonExceptionController {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JSONResultModel<Object> exception(Exception e){
        JSONResultModel<Object> resultModel = new JSONResultModel<>();
        resultModel.setRet(false);
        resultModel.setMsg(e.getMessage());
        resultModel.setMsg(e.getMessage()==null?e.getClass().getName():e.getMessage());
        e.printStackTrace();
        return resultModel;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public JSONResultModel dataIntegrityViolationExceptionExceptionHandle(DataIntegrityViolationException e){
        JSONResultModel resultModel = new JSONResultModel();
        resultModel.setRet(false);
        resultModel.setMsg(e.getCause().getCause().getMessage());
        e.printStackTrace();
        return resultModel;
    }

}
