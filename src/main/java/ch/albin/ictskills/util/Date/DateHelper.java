package ch.albin.ictskills.util.Date;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateHelper {
    public static boolean isDateBetweenDatesBasedOnMonth(LocalDate date, LocalDate from, LocalDate to) {
        return from.getMonthValue() + from.getYear() <= date.getMonthValue() + from.getYear() && to.getMonthValue() + from.getYear() >= date.getMonthValue() + from.getYear();
    }

    public static boolean isTimeBetweenTimes(LocalTime date, LocalTime from, LocalTime to) {
        return (from.isBefore(date) || from.equals(date)) && (to.isAfter(date) || to.equals(date));
    }
}
