package com.tomato.log.service;

import com.tomato.framework.log.support.Paging;

/**
 * @author Hunhun
 */
public interface LogService {

    void save(Object object);

    Paging queryLog(String queryParams);

    Object groupLog(String queryParams);
}
