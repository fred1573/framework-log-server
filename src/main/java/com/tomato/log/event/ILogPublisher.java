package com.tomato.log.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author frd
 */
public interface ILogPublisher {

    void publishEvent(ApplicationEvent event);
}
