package com.ligz.docker.log.masking;

import ch.qos.logback.classic.spi.ILoggingEvent;
import net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder;

import java.nio.charset.StandardCharsets;

public class MaskingLoggingEventCompositeJsonEncoder extends LoggingEventCompositeJsonEncoder {

    private final LogMasker logMasker;

    public MaskingLoggingEventCompositeJsonEncoder() {
        super();
        logMasker = new LogMasker();
    }

    @Override
    public byte[] encode(ILoggingEvent event) {
        byte[] message = super.encode(event);
        try {
            return logMasker.process(new String(message, StandardCharsets.UTF_8)).getBytes();
        } catch (Exception e) {
            return message;
        }
    }


}
