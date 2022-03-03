package com.ligz.docker.log.masking;

public interface LogMasker {
    String mask(String message);
}
