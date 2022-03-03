package com.ligz.docker.log.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.ligz.docker.log.masking.LogMasker;
import com.ligz.docker.log.masking.PatternLogMasker;
import com.ligz.docker.log.masking.loader.EnumPatternLoader;
import com.ligz.docker.log.masking.loader.PatternLoader;
import net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder;

import java.nio.charset.StandardCharsets;

public class MaskingLoggingEventCompositeJsonEncoder extends LoggingEventCompositeJsonEncoder {

    private final LogMasker logMasker;

    public MaskingLoggingEventCompositeJsonEncoder() {
        super();
        PatternLoader patternLoader = new EnumPatternLoader();
        logMasker = new PatternLogMasker(patternLoader);
    }

    @Override
    public byte[] encode(ILoggingEvent event) {
        byte[] message = super.encode(event);
        try {
            return logMasker.mask(new String(message, StandardCharsets.UTF_8)).getBytes();
        } catch (Exception e) {
            return message;
        }
    }

}
