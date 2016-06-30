package com.tomato.framework.log.client;

import com.tomato.framework.log.support.GroupBy;
import com.tomato.framework.log.support.Operation;
import com.tomato.framework.log.support.OrderBy;
import com.tomato.framework.log.support.Paging;
import com.tomato.mq.client.exception.SendMessageException;

import java.util.List;

/**
 * @author Hunhun
 */
public interface LogClient {

    void save(Object object) throws SendMessageException;

    Paging find(Paging paging, Operation operation, List<OrderBy> orderByList);

    Object find(Operation operation, GroupBy groupBy);

}
