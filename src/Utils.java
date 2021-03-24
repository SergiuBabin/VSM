import java.time.LocalDateTime;

public class Utils {
    public static boolean validateDate(LocalDateTime currentDate,
                                       LocalDateTime endDate) {
        if (endDate.getYear() > currentDate.getYear()) {
            return true;
        } else if (endDate.getYear() < currentDate.getYear()) {
            return false;
        }

        if (endDate.getMonthValue() > currentDate.getMonthValue()) {
            return true;
        } else if (endDate.getMonthValue() < currentDate.getMonthValue()) {
            return false;
        }

        if (endDate.getDayOfMonth() > currentDate.getDayOfMonth()) {
            return true;
        } else if (endDate.getDayOfMonth() < currentDate.getDayOfMonth()) {
            return false;
        }

        if (endDate.getHour() > currentDate.getHour()) {
            return true;
        } else if (endDate.getHour() < currentDate.getHour()) {
            return false;
        }

        if (endDate.getMinute() > currentDate.getMinute()) {
            return true;
        } else if (endDate.getMinute() < currentDate.getMinute()) {
            return false;
        }

        return true;
    }

}
