package com.tomato.framework.log.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tomato.framework.log.support.GroupBy;
import com.tomato.framework.log.support.Operation;
import com.tomato.framework.log.support.OrderBy;
import com.tomato.framework.log.support.Paging;

import java.util.List;

/**
 * @author Hunhun
 */
public class HttpParamConvert {

    public static JSONObject convert(Paging paging, String logType, Operation operation, List<OrderBy> orderByList) {
        JSONObject json = new JSONObject();
        json.put("logType", logType);
        json.put("paging", paging);
        json.put("orderByList", orderByList);
        json.put("values", convert(operation));
        return json;
    }

    public static JSONObject convert(String logType, Operation operation, GroupBy groupBy) {
        JSONObject json = new JSONObject();
        json.put("logType", logType);
        json.put("groupBy", groupBy.getField());
        json.put("values", convert(operation));
        return json;
    }

    public static JSONObject convert(Operation operation) {
        return JSON.parseObject(JSON.toJSONString(operation));
    }
}
