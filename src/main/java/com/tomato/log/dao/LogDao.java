package com.tomato.log.dao;

import com.tomato.framework.log.support.GroupBy;
import com.tomato.framework.log.support.Operation;
import com.tomato.framework.log.support.OrderBy;
import com.tomato.framework.log.support.Paging;

import java.util.List;

/**
 *
 * @author Hunhun
 */
public interface LogDao {

    void save(Object obj);

    /**
     * 分页条件查询
     */
    Paging findBizLog(Paging paging, Operation operation, List<OrderBy> orderByList);

    Object findBizLog(Operation operation, GroupBy groupBy);
}
