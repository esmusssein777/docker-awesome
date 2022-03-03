package com.ligz.docker.log.masking;

public class RegexPattern {
    private final String regex;
    private final String replaceRegex;
    private final String replacement;

    public RegexPattern(String regex, String replaceRegex, String replacement) {
        this.regex = regex;
        this.replaceRegex = replaceRegex;
        this.replacement = replacement;
    }

    public String getRegex() {
        return regex;
    }

    public String getReplaceRegex() {
        return replaceRegex;
    }

    public String getReplacement() {
        return replacement;
    }
}
