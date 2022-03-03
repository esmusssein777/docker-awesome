package com.ligz.docker.log.masking;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogMasker {

    private final static Map<Pattern, Pair<String, String>> patternsMap = new HashMap<>();

    private LogMasker() {
        loadPatterns();
    }

    private static class SingletonLogMasker {
        private static final LogMasker instance = new LogMasker();
    }

    public static LogMasker getInstance() {
        return SingletonLogMasker.instance;
    }

    private void loadPatterns() {
        for (PatternType patternType : PatternType.values()) {
            Pattern pattern = Pattern.compile(patternType.regex);
            patternsMap.put(pattern, Pair.of(patternType.replaceRegex, patternType.replacement));
        }
    }

    public String process(String message) {
        if (StringUtils.isBlank(message)) {
            return message;
        }
        for (Pattern pattern : patternsMap.keySet()) {
            Pair<String, String> replacePair = patternsMap.get(pattern);
            Matcher matcher = pattern.matcher(message);
            Set<String> matches = extractMatches(matcher);
            if (!matches.isEmpty()) {
                message = mask(message, matches, replacePair.getLeft(), replacePair.getRight());
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


    private String mask(String message, Set<String> matches, String replaceRegex, String replacement) {
        for (String match : matches) {
            String matchProcess = match.replaceAll(replaceRegex, replacement);
            message = message.replace(match, matchProcess);
        }
        return message;
    }

    private enum PatternType {
        PHONE_NUMBER("(1[3-9]\\d{9})[^\\d]|(^1[3-9]\\d{9}$)", "(?<=\\d{3})(\\d)(?=\\d{4})", "*"),
        BANK_CARD("[^\\d](\\d{16})[^\\d]|[^\\d](\\d{19})[^\\d]|(^\\d{16}$)|(^\\d{19}$)", "(?<=\\d{3})(\\d)(?=\\d{4})", "*"),
        EMAIL("([A-Za-z_0-9]{1,64}@[A-Za-z1-9_-]+.[A-Za-z]{2,10})", "(^\\w{3})[^@]*(@.*$)", "$1****$2"),
        ID_CARD("[^\\d](\\d{15})[^\\d]|[^\\d](\\d{18})[^\\d]|[^\\d](\\d{17}X)|(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}X$)", "(?<=\\d{3})(\\d)(?=\\d{4})", "*");

        public final String regex;
        public final String replaceRegex;
        public final String replacement;

        PatternType(String regex, String replaceRegex, String replacement) {
            this.regex = regex;
            this.replaceRegex = replaceRegex;
            this.replacement = replacement;
        }

    }

}
