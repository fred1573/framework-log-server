package com.tomato.log.event;

import com.tomato.log.consumer.LogSave;
import org.springframework.context.ApplicationEvent;

/**
 * @author frd
 */
public class LogSaveEvent extends ApplicationEvent {
    public LogSaveEvent(LogSave source) {
        super(source);
    }
}
