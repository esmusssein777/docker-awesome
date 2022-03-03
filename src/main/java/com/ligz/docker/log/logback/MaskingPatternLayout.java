package com.ligz.docker.log.logback;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.ligz.docker.log.masking.LogMasker;
import com.ligz.docker.log.masking.PatternLogMasker;
import com.ligz.docker.log.masking.loader.EnumPatternLoader;
import com.ligz.docker.log.masking.loader.PatternLoader;

public class MaskingPatternLayout extends PatternLayout {

    private final LogMasker logMasker;

    public MaskingPatternLayout() {
        super();
        PatternLoader patternLoader = new EnumPatternLoader();
        logMasker = new PatternLogMasker(patternLoader);
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        String message = super.doLayout(event);
        try {
            return logMasker.mask(message);
        } catch (Exception e) {
            return message;
        }
    }

}
