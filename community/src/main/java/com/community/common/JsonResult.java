package com.community.common;

import java.io.Serializable;

public class JsonResult implements Serializable {

    private final static Integer SUCCESS=1;

    private final static Integer FAIL=0;

    private String msg;

    private Integer code;

    private Object data;

    public void setSuccesed(Object data,String msg){
        this.data=data;
        this.msg=msg;
        this.code=SUCCESS;
    }

    public void setFail(String msg){
        this.msg=msg;
        this.code=FAIL;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
