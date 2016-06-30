package com.tomato.log.consumer;

import com.alibaba.fastjson.JSON;
import com.tomato.log.event.LogSaveEvent;
import com.tomato.log.model.BizLog;
import com.tomato.log.model.SysLog;
import com.tomato.log.event.LogPublisher;
import com.tomato.mq.client.event.listener.MsgEventListener;
import com.tomato.mq.client.event.model.MsgEvent;
import com.tomato.mq.client.event.publisher.MsgEventPublisher;
import com.tomato.mq.support.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 系统日志监听
 * Created by Administrator on 2015/4/20.
 */
@Component
public class LogConsumer implements MsgEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogConsumer.class);

    @Autowired
    private LogPublisher logPublisher;

    public LogConsumer() {
        MsgEventPublisher.getInstance().addListener(this, "LOG_CONSUMER_TEST", MessageType.SYS_LOG, MessageType.BIZ_LOG);
    }

    @Override
    public void onEvent(MsgEvent msgEvent) {
        LOGGER.debug("监听到日志消息：{}", msgEvent.getSource().toString());
        Object log;
        MessageType messageType = msgEvent.getMessageType();
        if (messageType.equals(MessageType.BIZ_LOG)) {
            log = JSON.parseObject(msgEvent.getSource().toString(), BizLog.class);
        } else if (messageType.equals(MessageType.SYS_LOG)) {
            log = JSON.parseObject(msgEvent.getSource().toString(), SysLog.class);
        } else {
            throw new RuntimeException("消息类型异常，msgType:" + messageType);
        }
//        logService.save(log);
        logPublisher.publishEvent(new LogSaveEvent(new LogSave(log)));
    }


}
