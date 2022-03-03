package com.ligz.docker.log.masking.loader;

import com.ligz.docker.log.masking.RegexPattern;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumPatternLoader implements PatternLoader {
    private static final String NUMBER_REPLACE_REGEX = "(?<=\\d{3})(\\d)(?=\\d{4})";
    private static final String NUMBER_REPLACEMENT = "*";

    private static final String EMAIL_REPLACE_REGEX = "(^\\w{3})[^@]*(@.*$)";
    private static final String EMAIL_REPLACEMENT = "$1****$2";

    private enum Pattern {
        PHONE_NUMBER("(1[3-9]\\d{9})[^\\d]|(^1[3-9]\\d{9}$)", NUMBER_REPLACE_REGEX, NUMBER_REPLACEMENT),
        BANK_CARD("[^\\d](\\d{16})[^\\d]|[^\\d](\\d{19})[^\\d]|(^\\d{16}$)|(^\\d{19}$)", NUMBER_REPLACE_REGEX, NUMBER_REPLACEMENT),
        ID_CARD("[^\\d](\\d{15})[^\\d]|[^\\d](\\d{18})[^\\d]|[^\\d](\\d{17}X)|(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}X$)", NUMBER_REPLACE_REGEX, NUMBER_REPLACEMENT),
        EMAIL("([A-Za-z_0-9]{1,64}@[A-Za-z1-9_-]+.[A-Za-z]{2,10})", EMAIL_REPLACE_REGEX, EMAIL_REPLACEMENT);

        public final String regex;
        public final String replaceRegex;
        public final String replacement;

        Pattern(String regex, String replaceRegex, String replacement) {
            this.regex = regex;
            this.replaceRegex = replaceRegex;
            this.replacement = replacement;
        }
    }

    @Override
    public List<RegexPattern> loadRegexPatterns() {
        return Arrays.stream(Pattern.values()).map(pattern -> new RegexPattern(pattern.regex, pattern.replaceRegex, pattern.replacement))
                .collect(Collectors.toList());
    }
}
