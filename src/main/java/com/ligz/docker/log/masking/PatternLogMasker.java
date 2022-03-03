package com.ligz.docker.log.masking;

import com.ligz.docker.log.masking.loader.PatternLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class PatternLogMasker implements LogMasker {

    private final static List<RegexPattern> regexPatterns = new ArrayList<>();

    public PatternLogMasker(PatternLoader patternLoader) {
        regexPatterns.addAll(patternLoader.loadRegexPatterns());
    }

    @Override
    public String mask(String message) {
        if (isBlank(message)) {
            return message;
        }
        for (RegexPattern regexPattern : regexPatterns) {
            Pattern pattern = Pattern.compile(regexPattern.getRegex());
            Matcher matcher = pattern.matcher(message);
            Set<String> matches = extractMatches(matcher);
            if (!matches.isEmpty()) {
                message = replace(message, matches, regexPattern.getReplaceRegex(), regexPattern.getReplacement());
            }
        }
        return message;
    }

    private Set<String> extractMatches(Matcher matcher) {
        Set<String> matches = new HashSet<>();
        int count = matcher.groupCount();
        while (matcher.find()) {
            if (count == 0) {
                matches.add(matcher.group());
                continue;
            }
            for (int i = 1; i <= count; i++) {
                String match = matcher.group(i);
                if (null != match) {
                    matches.add(match);
                }
            }
        }
        return matches;
    }

    private String replace(String message, Set<String> matches, String replaceRegex, String replacement) {
        for (String match : matches) {
            String matchProcess = match.replaceAll(replaceRegex, replacement);
            message = message.replace(match, matchProcess);
        }
        return message;
    }
}
