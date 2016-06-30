package com.tomato.framework.log.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tomato.framework.log.support.GroupBy;
import com.tomato.framework.log.support.Operation;
import com.tomato.framework.log.support.OrderBy;
import com.tomato.framework.log.support.Paging;
import com.tomato.framework.log.util.ApiURL;
import com.tomato.framework.log.util.HttpParamConvert;
import com.tomato.framework.log.util.HttpUtil;
import com.tomato.framework.log.util.SystemConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hunhun
 */
public class BizLogClient extends AbstractLogClient {

    public static final String BIZ_LOG = "BIZ_LOG";

    @Override
    public Paging find(Paging paging, Operation operation, List<OrderBy> orderByList) {
        JSONObject json = HttpParamConvert.convert(paging, BIZ_LOG, operation, orderByList);
        Map<String, Object> params = new HashMap<>();
        params.put("queryParams", json);
        String url = new HttpUtil().buildUrl(SystemConfig.PROPERTIES.get(SystemConfig.LOG_SERVER), ApiURL.LOG_QUERY, params);
        String response = new HttpUtil().get(url, false);
        JSONObject jsonObject = JSON.parseObject(response);
        paging = jsonObject.getObject("paging", Paging.class);
        return paging;
    }

    @Override
    public Object find(Operation operation, GroupBy groupBy) {
        JSONObject json = HttpParamConvert.convert(BIZ_LOG, operation, groupBy);
        Map<String, Object> params = new HashMap<>();
        params.put("queryParams", json);
        String url = new HttpUtil().buildUrl(SystemConfig.PROPERTIES.get(SystemConfig.LOG_SERVER), ApiURL.LOG_GROUP, params);
        String response = new HttpUtil().get(url, false);
        return JSON.parseObject(response);
    }
}
