import java.util.Calendar;
import java.util.Date;

/**
 * Utility methods for java.util.Date objects.
 * 
 * @author Farzana R.
 * 
 * @version 2019-11-26
 */
public class DateUtils {
    
    private static final long MILLIS_PER_DAY = 86400000;
    
    /**
     * Adds a number of days to a date.
     * 
     * @param date the date (unchanged)
     * @param days number of days to add
     * @return the new date
     */
    public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }
    
    /**
     * Compute the number of days from the "from" date to the "to" date.
     * The time fields (hour, minute, second, millisecond) are set to zero
     * before the calculation is made, so that only the year, month, and day
     * are used to calculate the difference.
     * 
     * @param from the date starting the interval
     * @param to the date ending the interval
     * @return number of whole days in the interval
     */
    public static int interval(Date from, Date to) {
        
        Calendar fromCal = Calendar.getInstance();
        fromCal.setTime(from);
        fromCal.set(Calendar.HOUR, 0);
        fromCal.set(Calendar.MINUTE, 0);
        fromCal.set(Calendar.SECOND, 0);
        fromCal.set(Calendar.MILLISECOND, 0);
        
        Calendar toCal = Calendar.getInstance();
        toCal.setTime(to);
        toCal.set(Calendar.HOUR, 0);
        toCal.set(Calendar.MINUTE, 0);
        toCal.set(Calendar.SECOND, 0);
        toCal.set(Calendar.MILLISECOND, 0);
        
        long diff = toCal.getTimeInMillis() - fromCal.getTimeInMillis();
        return (int) (diff / MILLIS_PER_DAY);
    }
    
}
