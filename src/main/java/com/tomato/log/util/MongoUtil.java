package com.tomato.log.util;

import com.alibaba.fastjson.JSONObject;
import com.tomato.framework.log.support.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


/**
 * @author Hunhun
 */
public class MongoUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoUtil.class);

    public static Query composeQuery(Operation operation) {
        return new Query(getCriteria(operation));
    }

    public static Sort composeSort(List<OrderBy> orderByList) {
        if(CollectionUtils.isEmpty(orderByList)) {
            return new Sort(new Sort.Order(Sort.Direction.DESC, "localDate"), new Sort.Order(Sort.Direction.DESC, "localTime"));
        }
        List<Sort.Order> orderList = new ArrayList<>();
        for (OrderBy orderBy : orderByList) {
            Sort.Order order = new Sort.Order(Sort.Direction.fromString(orderBy.getDirection().toString()), orderBy.getField());
            orderList.add(order);
        }
        return new Sort(orderList);
    }

    /**
     * 递归解析二叉树
     */
    public static Criteria getCriteria(Operation operation) {
        Criteria criteria = null;
        Object key = operation.getKey();
        Object value = operation.getValue();
        if (key instanceof String) {
            String keyStr = key.toString();
            //叶子结点
            Criteria leafCriteria;
            Operator operator = operation.getOperator();
            if (key.equals("data.localDate") || key.equals("logDate")) {
                value = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date(Long.valueOf(value.toString()))));
            }
            if(key.equals("data.localTime") || key.equals("logTime")) {
                value = LocalTime.parse(new SimpleDateFormat("HH:mm:ss").format(new Date(Long.valueOf(value.toString()))));
            }
            switch (operator) {
                case GT:
                    leafCriteria = Criteria.where(keyStr).gt(value);
                    break;
                case GTE:
                    leafCriteria = Criteria.where(keyStr).gte(value);
                    break;
                case LT:
                    leafCriteria = Criteria.where(keyStr).lt(value);
                    break;
                case LTE:
                    leafCriteria = Criteria.where(keyStr).lte(value);
                    break;
                case NE:
                    leafCriteria = Criteria.where(keyStr).ne(value);
                    break;
                case LIKE:
                    leafCriteria = Criteria.where(keyStr).regex(value.toString());
                    break;
                case EQ:
                    leafCriteria = Criteria.where(keyStr).is(value);
                    break;
                default:
                    LOGGER.error("未识别的operator:{}", operator);
                    throw new RuntimeException("未识别的operator:" + operator);
            }
            return leafCriteria;
        }
        if (key instanceof JSONObject) {
            criteria = getCriteria(JSONObject.parseObject(((JSONObject) key).toJSONString(), Operation.class));
            switch (operation.getOperator()) {
                case AND:
                    criteria = new Criteria().andOperator(criteria, getCriteria(JSONObject.parseObject(((JSONObject) value).toJSONString(), Operation.class)));
                    break;
                case OR:
                    criteria = new Criteria().orOperator(criteria, getCriteria(JSONObject.parseObject(((JSONObject) value).toJSONString(), Operation.class)));
                    break;
                default:
                    break;
            }
        }
        return criteria;
    }
}
