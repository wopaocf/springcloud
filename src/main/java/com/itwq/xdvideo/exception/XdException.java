package com.itwq.xdvideo.exception;

import lombok.Data;

@Data
public class XdException  extends  RuntimeException{
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 异常消息
     */
    private String msg;

    private XdException(){

        }

    private XdException(Integer code,String msg){
            super(msg);
            this.code=code;
            this.msg=msg;
    }


}
