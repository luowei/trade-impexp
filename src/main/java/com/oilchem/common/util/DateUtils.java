package com.oilchem.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

/**
 * User: luowei
 * Date: 12-5-29
 * Time: 下午4:20
 */
public abstract class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static final SimpleDateFormat DATE_ONLY_NO_DELIM = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat DATE_TIME_STAMP_NO_DELIM = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    public static final SimpleDateFormat DATE_TIME_STAMP_SHORT_NO_DELIM = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat DATE_TIME_STAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final SimpleDateFormat DATE_TIME_STAMP_SHORT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_TIME_STAMP_SHORT_TIME2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static final SimpleDateFormat DATE_ONLY = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_ONLY_COMMON_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    public static final SimpleDateFormat DATE_COMMON_FORMAT_WITH_TIME = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
    public static final SimpleDateFormat DATE_COMMON_FORMAT_WITH_SHORT_TIME = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    public static final SimpleDateFormat DATE_XML_FORMAT_TZ = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    public static final SimpleDateFormat DATE_XML_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    public static final SimpleDateFormat DATE_XML_FORMAT_ISO_8601_FULL = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//ISO_8601_FULL
    public static final SimpleDateFormat DATE_XML_SHORT_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    public static final SimpleDateFormat HOUR24_TIME_ONLY = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat HOUR24_WITH_MS_TIME_ONLY = new SimpleDateFormat("HH:mm:ss.SSS");
    public static final SimpleDateFormat FULL_WRITTEN_DATE_NO_TIME = new SimpleDateFormat("MMM dd, yyyy");
    public static final SimpleDateFormat FULL_WRITTEN_DATE_WITH_TIME = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");

    public static final SimpleDateFormat YEARMONTH_ONLY = new SimpleDateFormat("yyyy-MM");

    public static final SimpleDateFormat RFC822_TIMESTAMP = new SimpleDateFormat();
//	    Sun, 29 Sep 2002 11:13:10 GMT

    private static final Logger LOGGER = Logger.getLogger("net.youngdev.util.DateUtils");

    private static final SimpleDateFormat[] FORMAT_ARY = new SimpleDateFormat[]{
            DateUtils.DATE_COMMON_FORMAT_WITH_TIME,
            DateUtils.DATE_TIME_STAMP,
            DateUtils.DATE_TIME_STAMP_SHORT_TIME,
            DateUtils.DATE_TIME_STAMP_SHORT_TIME2,
            DateUtils.DATE_XML_FORMAT_TZ,
            DateUtils.DATE_XML_FORMAT,
            DateUtils.DATE_XML_FORMAT_ISO_8601_FULL,
            DateUtils.DATE_XML_SHORT_FORMAT,
            DateUtils.DATE_ONLY_COMMON_FORMAT,
            DateUtils.DATE_ONLY,
            DateUtils.DATE_TIME_STAMP_NO_DELIM,
            DateUtils.DATE_TIME_STAMP_SHORT_NO_DELIM,
            DateUtils.DATE_ONLY_NO_DELIM,
            DateUtils.FULL_WRITTEN_DATE_WITH_TIME,
            DateUtils.FULL_WRITTEN_DATE_NO_TIME,
            DateUtils.YEARMONTH_ONLY
    };

    private static SimpleDateFormat dateFormat = new SimpleDateFormat();

    /**
     * 获取当前时间（yyyy-MM-dd HH:mm:ss）
     *
     * @return
     */
    public static String datetime() {
        dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    /**
     * 获取当前时间（日期对象 yyyy-MM-dd HH:mm:ss）
     *
     * @return
     */
    public static Date datetimeToDate() {
        return Timestamp.valueOf(datetime());
    }

    /**
     * 获取当前日期（yyyy-MM-dd）
     *
     * @return
     */
    public static String date() {
        dateFormat.applyPattern("yyyy-MM-dd");
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    /**
     * 获取当前时间（HH:mm:ss）
     *
     * @return
     */
    public static String time() {
        dateFormat.applyPattern("HH:mm:ss");
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    /**
     * 字符串转换成日期或时间
     * Successively tries to parse a string represented as a date by the dateString.  formats tried in the following order:
     *
     * @param dateString
     * @return
     */
    public static Date parseDate(String dateString) {
        Date date = null;
        for (int i = 0; i < FORMAT_ARY.length; i++) {
            try {
                date = FORMAT_ARY[i].parse(dateString);
                //   LOGGER.debug( "Using format "+i );
                break;
            } catch (Exception e) {
                // try the next one.
            }
        }
        if (date == null) {
            LOGGER.info("unable to parse " + dateString);
        }

        return date;
    }

    public static boolean isWeekend(Date date) {
        boolean ret = false;
        Calendar cal = DateUtils.buildCalendar(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            ret = true;
        }
        return ret;
    }

    /**
     * Nullsafe Calendar Builder.
     *
     * @param date
     * @return
     */
    public static Calendar buildCalendar(Date date) {
        Calendar cal = null;
        if (date != null) {
            cal = Calendar.getInstance();
            cal.setTime(date);
        }
        return cal;
    }

    public static Calendar buildCalendar(String dateString) {
        Calendar cal = null;
        Date date = null;
        try {
            date = DateUtils.parseDate(dateString);
        } catch (Exception e) {
            LOGGER.info("unable to parse dateString: " + dateString);
        }
        return cal;
    }

    /**
     * Counts the first day of each month between $begin(inclusive) and $end(exclusive)
     *
     * @param begin
     * @param end
     * @return
     */
    public static int countFirstOfMonthsBetween(Date begin, Date end) {
        int count = 0;
        Calendar beginCal = DateUtils.buildCalendar(DateUtils.stripTime(begin));
        Calendar endCal = DateUtils.buildCalendar(DateUtils.stripTime(end));
        LOGGER.info(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(beginCal.getTime()));
        LOGGER.info(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(endCal.getTime()));
        if (beginCal.get(Calendar.DAY_OF_MONTH) == 1) {
            ++count;
        }
        beginCal.set(Calendar.DAY_OF_MONTH, 1);
        // 	beginCal.add(Calendar.MONTH, 1);
        while (beginCal.before(endCal)) {
            LOGGER.info(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(beginCal.getTime()) + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(endCal.getTime()));
            beginCal.add(Calendar.MONTH, 1);
            if (beginCal.before(endCal)) {
                ++count;
            }

        }

        return count;
    }

    /**
     * Strips the time off the date Object
     *
     * @param date
     * @return
     */
    public static Date stripTime(Date date) {
        Date d = null;
        if (date != null) {
            Calendar calendar = DateUtils.buildCalendar(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);


            d = calendar.getTime();
        }
        return d;
    }

    /**
     * @param date
     * @return
     */
    public static Date getFirstOfClosestMonth(Date date) {
        Date d = null;
        if (date != null) {
            Calendar c = DateUtils.buildCalendar(DateUtils.stripTime(date));
            if (c.get(Calendar.DAY_OF_MONTH) != 1) {
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.add(Calendar.MONTH, 1);
            }
            d = c.getTime();
        }
        return d;
    }

    /**
     * Adds 1 to the month field in the calendar object of the provided date.
     *
     * @param current
     * @return
     */
    public static Date incrementMonth(Date current) {
        Date d = null;
        if (current != null) {
            Calendar c = DateUtils.buildCalendar(current);
            c.add(Calendar.MONTH, 1);
            d = c.getTime();
        }
        return d;
    }

    /**
     * Advances the current date to the end of the month or returns null
     *
     * @param current
     * @return
     */
    public static Date advanceToEndOfMonth(Date current) {
        Date d = null;
        if (current != null) {
            Calendar c = DateUtils.buildCalendar(current);
            c.add(Calendar.MONTH, 1);
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.add(Calendar.DAY_OF_YEAR, -1);
            d = c.getTime();
        }
        return d;
    }

    /**
     * Sets the date and time to 00:00:00 on the first day of the month
     *
     * @param date
     * @return
     */
    public static Date getFirstOfMonth(Date date) {
        Date d = null;
        if (date != null) {
            Calendar calendar = DateUtils.buildCalendar(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            d = calendar.getTime();
        }
        return d;
    }

    /**
     * Gets the first of the month at 00:00:00 of the current month
     *
     * @return
     */
    public static Date getFirstOfMonth() {
        return getFirstOfMonth(new Date());
    }

    public static Date add(Date date, int calendarField, int addAmount) {
        Calendar cal = buildCalendar(date);
        cal.add(calendarField, addAmount);
        return cal.getTime();
    }


}
