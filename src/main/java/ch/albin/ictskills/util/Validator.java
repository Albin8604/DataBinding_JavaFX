package ch.albin.ictskills.util;

import java.util.Collection;

public class Validator {
    public static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public static final String TEL_REGEX = "[\\\\+]?[(]?[0-9]{3}[)]?[-\\\\s\\\\.]?[0-9]{3}[-\\\\s\\\\.]?[0-9]{4,6}";
    public static final String ONLY_LETTER_REGEX = "[a-zA-Z]+";
    public static final String ONLY_NUMBER_REGEX = "[0-9]+";
    public static boolean isNullOrEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }
    public static boolean isInLength(String text, int min, int max){
        return text.length() >= min && text.length() <= max;
    }

    public static String getNumberRegexInRange(int min, int max){
        return "[0-9]{"+min+","+max+"}";
    }

    public static String getLetterRegexInRange(int min, int max){
        return "[a-zA-Z]{"+min+","+max+"}";
    }
    public static boolean matchesRegex(String text, String regex){
        return text.matches(regex);
    }
}
