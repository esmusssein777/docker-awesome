package com.ligz.docker.log.masking;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class MaskingPatternLayout extends PatternLayout {

    @Override
    public String doLayout(ILoggingEvent event) {
        String message = super.doLayout(event);
        try {
            return LogMasker.getInstance().process(message);
        } catch (Exception e) {
            return message;
        }
    }

}
