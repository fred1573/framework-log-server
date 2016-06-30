package com.tomato.framework.log.client;

import com.tomato.framework.log.support.GroupBy;
import com.tomato.framework.log.support.Operation;
import com.tomato.framework.log.support.OrderBy;
import com.tomato.framework.log.support.Paging;

import java.util.List;

/**
 * @author Hunhun
 */
public class SysLogClient extends AbstractLogClient{


    @Override
    public Paging find(Paging paging, Operation operation, List<OrderBy> orderByList) {
        return null;
    }

    @Override
    public Object find(Operation operation, GroupBy groupBy) {
        return null;
    }
}
