package com.tomato.log.controller;

import com.tomato.framework.log.support.Paging;
import com.tomato.log.util.HttpResp;

/**
 * @author Hunhun
 */
public class Result {

    private int status = HttpResp.SUCCESS;
    private String message;
    private Object content;
    private Paging paging;

    public Result(Object content, Paging paging) {
        this.content = content;
        this.paging = paging;
    }

    public Result(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }
}
