package com.tomato.framework.log.model;

import java.util.Date;

/**
 * Created by Administrator on 2015/4/20.
 */
public class BizLog {

    private Integer innId;
    private Object type;
    private Date logTime;
    private String project;
    private Object data;

    public BizLog() {
    }

    public BizLog(Integer innId, Object type, String project,  Object data) {
        this.innId = innId;
        this.type = type;
        this.project = project;
        this.data = data;
    }

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "BizLog{" +
                "innId=" + innId +
                ", type=" + type +
                ", logTime=" + logTime +
                ", project='" + project + '\'' +
                ", data=" + data +
                '}';
    }
}
