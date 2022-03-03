package com.ligz.docker.log.masking;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogMasker {

    private final static Map<PatternType, Pattern> patternsMap = new HashMap<>();

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
            patternsMap.put(patternType, pattern);
        }
    }

    public String process(String message) {
        if (StringUtils.isBlank(message)) {
            return message;
        }
        for (PatternType key : patternsMap.keySet()) {
            Pattern pattern = patternsMap.get(key);
            Matcher matcher = pattern.matcher(message);
            Set<String> matches = extractMatchesByType(matcher);
            if (!matches.isEmpty()) {
                message = maskByType(key, message, matches);
            }
        }
        return message;
    }

    private Set<String> extractMatchesByType(Matcher matcher) {
        return extractDefault(matcher);
    }

    private Set<String> extractDefault(Matcher matcher) {
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


    private String maskByType(PatternType key, String message, Set<String> matchs) {
        if (key == PatternType.ID_CARD || key == PatternType.PHONE_NUMBER || key == PatternType.BANK_CARD) {
            return replaceNumber(message, matchs);
        } else if (key == PatternType.EMAIL) {
            return replaceMail(message, matchs);
        } else if (key == PatternType.EMAIL_FOR_HTML_ENCODE) {
            return replaceHtmlMail(message, matchs);
        } else {
            return message;
        }
    }


    private String replaceNumber(String message, Set<String> matches) {

        for (String match : matches) {
            String matchProcess = maskNumber(match);
            message = message.replace(match, matchProcess);
        }
        return message;
    }

    private String replaceMail(String message, Set<String> matches) {
        for (String match : matches) {
            String matchProcess = maskMail(match);
            message = message.replace(match, matchProcess);
        }
        return message;
    }


    private String maskNumber(String numberStr) {
        return numberStr.replaceAll("(?<=\\d{3})(\\d)(?=\\d{4})", "*");
    }

    private String maskMail(String mailString) {
        return mailString.replaceAll("(^\\w{3})[^@]*(@.*$)", "$1****$2");
    }

    private String replaceHtmlMail(String message, Set<String> matches) {
        for (String match : matches) {
            String matchProcess = maskHtmlMail(match);
            message = message.replace(match, matchProcess);
        }
        return message;
    }

    private String maskHtmlMail(String mailString) {
        return mailString.replaceAll("(^\\w{3})[^&]*(&.*$)", "$1****$2");
    }


    private enum PatternType {
        PHONE_NUMBER("手机号", "(1[3-9]\\d{9})[^\\d]|(^1[3-9]\\d{9}$)"),
        BANK_CARD("银行卡", "[^\\d](\\d{16})[^\\d]|[^\\d](\\d{19})[^\\d]|(^\\d{16}$)|(^\\d{19}$)"),
        EMAIL("邮箱", "([A-Za-z_0-9]{1,64}@[A-Za-z1-9_-]+.[A-Za-z]{2,10})"),
        EMAIL_FOR_HTML_ENCODE("邮箱", "([A-Za-z_0-9]{1,64}&#x40;[A-Za-z1-9_-]+.[A-Za-z]{2,10})"),
        ID_CARD("身份证", "[^\\d](\\d{15})[^\\d]|[^\\d](\\d{18})[^\\d]|[^\\d](\\d{17}X)|(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}X$)");

        public String description;
        public String regex;

        PatternType(String description, String regex) {
            this.description = description;
            this.regex = regex;
        }

    }

}
