package todolist.today.today.global.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private LocalDateUtil() {
        throw new IllegalStateException("Utility Class");
    }

    public static String convert(LocalDate date) {
        return date.format(formatter);
    }

    public static LocalDate convert(String date) {
        return LocalDate.parse(date, formatter);
    }

}
