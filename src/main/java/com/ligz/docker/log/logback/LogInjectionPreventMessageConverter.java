package com.ligz.docker.log.logback;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.ligz.docker.log.masking.LogMasker;

public class LogInjectionPreventMessageConverter extends MessageConverter {

    @Override
    public String convert(ILoggingEvent event) {
        return replaceUnsafeLogCharacters(super.convert(event));
    }

    private String replaceUnsafeLogCharacters(String message) {
        return LogMasker.getInstance().process(message);
    }
}
