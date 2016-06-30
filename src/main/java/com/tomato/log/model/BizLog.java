package com.tomato.log.model;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Administrator on 2015/4/20.
 */
@Document(collection = "tomato_biz_log")
public class BizLog {

    @Id
    private String id;
    private Object type;
    private Integer innId;
    private String project;
    private LocalDate localDate = LocalDate.now();
    private LocalTime localTime = LocalTime.now();
    private Object data;

    public BizLog() {
    }

    public BizLog(JSONObject json){
        this.type = json.get("type");
        this.innId = json.getInteger("innId");
        this.project = json.getString("project");
        this.data = json.get("data");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
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
}
