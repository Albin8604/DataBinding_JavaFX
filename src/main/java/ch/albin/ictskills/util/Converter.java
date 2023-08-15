package ch.albin.ictskills.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static ch.albin.ictskills.util.Date.Formatter.*;

public class Converter {
    public static LocalDateTime dateToLocalDateTime(Date dateToConvert){
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
    public static LocalDate dateToLocalDate(Date dateToConvert){
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date LocalDateTimetoDate(LocalDateTime localDateTimeToConvert){
        return Date.from(localDateTimeToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate convertTextToLocalDate(String text) {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
    public static LocalTime convertTextToLocalTime(String text) {
        return LocalTime.parse(text, DateTimeFormatter.ofPattern(TIME_FORMAT));
    }
    public static LocalDateTime convertTextToLocalDateTime(String text) {
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }
    public static String convertLocalDateToText(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
    public static String convertLocalTimeToText(LocalTime localTime) {
        return localTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }
    public static String convertLocalDateToText(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    public static boolean convertByteToBoolean(byte b) {
        return b == 1;
    }

    public static byte convertBooleanToByte(boolean b) {
        return (byte) (b ? 1 : 0);
    }
}
