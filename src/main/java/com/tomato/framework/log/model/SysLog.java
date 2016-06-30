/**
 *
 */
package com.tomato.framework.log.model;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * @author Hunhun
 *         <p>
 *         上午11:24:15
 */
public class SysLog {

    private String project;

    private String module;

    private String descr;

    private Date logTime;

    private String userId;

    private String classType;

    private String signature;

    private Object extension;

    public SysLog() {
    }

    public SysLog(String project, String module, String descr, String classType, String methodName,
                  Object[] args, String userId, String extension) {
        this.project = project;
        this.module = module;
        this.descr = descr;
        this.classType = classType;
        this.signature = resolveSignature(methodName, getTypes(args));
        this.userId = userId;
        this.extension = JSON.parseObject(extension);
    }

    public SysLog(String project, String module, String descr, String classType, String signature,
                  String userId, String extension) {
        this.project = project;
        this.module = module;
        this.descr = descr;
        this.classType = classType;
        this.signature = signature;
        this.userId = userId;
        this.extension = JSON.parseObject(extension);
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Object getExtension() {
        return extension;
    }

    public void setExtension(Object extension) {
        this.extension = extension;
    }

    private String resolveSignature(String methodName, Class[] types){
        String str = methodName + "(*)";
        if(types.length <= 0){
            return str.replace("*", StringUtils.EMPTY);
        }else{
            StringBuffer sb = new StringBuffer();
            for(Class clazz : types){
                sb.append(clazz.getCanonicalName())
                        .append(", ");
            }
            return str.replace("*", sb.subSequence(0, sb.lastIndexOf(",")));
        }
    }

    private Class[] getTypes(Object[] args){
        Class[] types = new Class[args.length];
        if(args != null && args.length > 0){
            for(int i=0; i<args.length; i++){
                types[i] = args[i].getClass();
            }
        }
        return types;
    }
}
