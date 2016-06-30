package com.tomato.log.event;

import com.tomato.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author frd
 */
@Component
public class LogSaveListener implements ApplicationListener<LogSaveEvent> {

    @Autowired
    private LogService logService;

    @Override
    public void onApplicationEvent(LogSaveEvent logSaveEvent) {
        logService.save(logSaveEvent.getSource());
    }
}
