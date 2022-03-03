package com.ligz.docker.log.masking.loader;

import com.ligz.docker.log.masking.RegexPattern;

import java.util.List;

public interface PatternLoader {
    List<RegexPattern> loadRegexPatterns();
}
