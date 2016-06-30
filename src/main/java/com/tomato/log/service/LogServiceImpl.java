package com.tomato.log.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tomato.framework.log.support.GroupBy;
import com.tomato.framework.log.support.Operation;
import com.tomato.framework.log.support.OrderBy;
import com.tomato.framework.log.support.Paging;
import com.tomato.log.consumer.LogSave;
import com.tomato.log.dao.LogDao;
import com.tomato.log.model.BizLog;
import com.tomato.log.util.LocalDateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hunhun
 */
@Service("logService")
public class LogServiceImpl implements LogService {

    public static final String BIZ_LOG = "BIZ_LOG";

    @Autowired
    private LogDao logDao;

    @Override
    public void save(Object object) {
        LogSave logSave = (LogSave) object;
        Object log = logSave.getLog();
        if(log instanceof BizLog) {
            BizLog bizLog = (BizLog) log;
            JSONObject jsonData = JSON.parseObject(JSON.toJSONString(bizLog.getData()));
            if(jsonData != null) {
                Date operateTime = jsonData.getDate("operateTime");
                if(operateTime != null) {
                    LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(operateTime));
                    LocalTime localTime = LocalTime.parse(new SimpleDateFormat("HH:mm:ss").format(operateTime));
                    jsonData.put("operateTime", LocalDateTimeUtils.getLocalDateTime(operateTime));
                    jsonData.put("localDate", localDate);
                    jsonData.put("localTime", localTime);
                    bizLog.setData(jsonData);
                }
            }
            logDao.save(bizLog);
            return;
        }
        logDao.save(log);
    }

    @Override
    public Paging queryLog(String queryParams) {
        JSONObject queryJson = JSON.parseObject(queryParams);
        String logType = queryJson.getString("logType");
        Paging paging = queryJson.getObject("paging", Paging.class);
        String orderByStr = queryJson.getString("orderByList");
        JSONArray orderByArr = JSONArray.parseArray(orderByStr);
        List<OrderBy> orderByList = new ArrayList<>();
        if(orderByArr != null) {
            for (Object o : orderByArr) {
                JSONObject orderBy = (JSONObject) o;
                OrderBy ob = OrderBy.where(orderBy.getString("field"));
                if(orderBy.getString("direction").equals(OrderBy.Direction.ASC.toString())) {
                    ob = ob.asc();
                }
                orderByList.add(ob);
            }
        }
        Operation operation = queryJson.getObject("values", Operation.class);
        if(logType.equals(BIZ_LOG)){
            paging = logDao.findBizLog(paging, operation, orderByList);
        }else {
            //TODO
        }
        return paging;
    }

    @Override
    public Object groupLog(String queryParams) {
        JSONObject queryJson = JSON.parseObject(queryParams);
        String logType = queryJson.getString("logType");
        String groupBy = queryJson.getString("groupBy");
        Operation operation = queryJson.getObject("values", Operation.class);
        if(logType.equals(BIZ_LOG)){
            return logDao.findBizLog(operation, new GroupBy(groupBy));
        }else {
            //TODO
            return null;
        }
    }

}
