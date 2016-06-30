package com.tomato.log.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tomato.framework.log.support.GroupBy;
import com.tomato.framework.log.support.Operation;
import com.tomato.framework.log.support.OrderBy;
import com.tomato.framework.log.support.Paging;
import com.tomato.log.bo.BizLogCountMapping;
import com.tomato.log.model.BizLog;
import com.tomato.log.util.MongoUtil;
import com.tomato.log.util.PageUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * @author Hunhun
 */
@Repository("mongoDao")
public class MongoDaoImpl implements LogDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void save(Object obj) {
        mongoTemplate.save(obj);
    }

    @Override
    public Paging findBizLog(Paging paging, Operation operation, List<OrderBy> orderByList) {
        Query query = MongoUtil.composeQuery(operation);
        Sort sort = MongoUtil.composeSort(orderByList);
        query.with(sort).skip(PageUtil.skip(paging)).limit(PageUtil.limit(paging));
        List<BizLog> bizLogs = mongoTemplate.find(query, BizLog.class);
        Long count = mongoTemplate.count(query, BizLog.class);
        paging.setTotalCount(count.intValue());
        paging.setResult(bizLogs);
        return paging;
    }


    @Override
    public Object findBizLog(Operation operation, GroupBy groupBy) {
        Criteria criteria = MongoUtil.getCriteria(operation);
        GroupByResults<BizLogCountMapping> result = mongoTemplate.group(criteria, "tomato_biz_log",
                org.springframework.data.mongodb.core.mapreduce.GroupBy.keyFunction("function(doc) { return { x: doc." + groupBy.getField() + " }; }").initialDocument("{ count: 0 }")
                        .reduceFunction("function(doc, prev) { prev.count += 1 }"), BizLogCountMapping.class);
        JSONObject json = new JSONObject();
        json.put("totalCount", result.getCount());
        json.put("keys", result.getKeys());
        JSONArray jsonArr = new JSONArray();
        Iterator<BizLogCountMapping> iterator = result.iterator();
        while (iterator.hasNext()) {
            BizLogCountMapping mapping = iterator.next();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put(mapping.getX(), mapping.getCount());
            jsonArr.add(jsonObj);
        }
        json.put("content", jsonArr);
        return json;
    }

    public void foo() {
        String mapFunction = "function(){emit(this.innId, 1);}";
        String reduceFunction = "function(key, values){return Array.sum(values)};";
        MapReduceResults<BizLogCountMapping> results = mongoTemplate.mapReduce(new Query(Criteria.where("innId").gt(1000)), "tomato_biz_log", mapFunction, reduceFunction, BizLogCountMapping.class);
//        MapReduceResults<BizLogCountMapping> results = mongoTemplate.mapReduce("tomato_biz_log", mapFunction, reduceFunction, BizLogCountMapping.class);
        System.out.println(results);
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");
        MongoDaoImpl mongoDao = (MongoDaoImpl) ctx.getBean("mongoDao");
        mongoDao.foo();
    }
}
