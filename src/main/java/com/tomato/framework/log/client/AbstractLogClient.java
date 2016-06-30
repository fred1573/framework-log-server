package com.tomato.framework.log.client;

import com.alibaba.fastjson.JSON;
import com.tomato.mq.client.support.MQClientBuilder;
import com.tomato.mq.support.core.TextMessage;
import com.tomato.mq.support.message.MessageType;

/**
 *
 * Created by Administrator on 2015/4/28.
 */
public abstract class AbstractLogClient implements LogClient {

    @Override
    public void save(Object object) {
        MQClientBuilder.build().send(new TextMessage(JSON.toJSONString(object), MessageType.BIZ_LOG, "BIZ_LOG_CLIENT"));
    }

}
