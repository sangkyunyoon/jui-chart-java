import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yuni on 2014-12-18.
 */
public class Test {
    public static void main(String args[]) {
        long current = System.currentTimeMillis();

        printDate(getToday(current));
        printDate(getToday(current, 3, 2));
        printDate(getWeek(current, 1, 7));
        printDate(getMonth(current - 1000*60*60*24*100));

    }

    protected static long[] getMonth(long current) {
        long[] list = new long[2];

        System.out.println(current);

        // 한달전 날짜
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(current);

        System.out.println(c.get(Calendar.MONTH));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date(current)));


        c.add(Calendar.MONTH, -1);

        System.out.println(c.get(Calendar.MONTH));

        Calendar start = (Calendar) c.clone();
        System.out.println(start.get(Calendar.MONTH));
        start.set(Calendar.DATE, 1);
        start.clear(Calendar.HOUR);
        start.clear(Calendar.MINUTE);
        start.clear(Calendar.SECOND);
        start.clear(Calendar.MILLISECOND);

        Calendar end = (Calendar) start.clone();
        end.add(Calendar.MONTH, 1);
        end.add(Calendar.DATE, -1);
        end.set(Calendar.HOUR, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.set(Calendar.MILLISECOND, 999);

        list[0] = start.getTimeInMillis();
        list[1] = end.getTimeInMillis();

        return list;
    }

    protected static void printDate(long[] list) {
        System.out.println("start : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(list[0])));
        System.out.println("end : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(list[1])));
    }


    protected static long[] getToday(long current) {

        long[] list = new long[2];

        // 어제 하루 종일
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(current);
        c.add(Calendar.DATE, -1);

        Calendar start = (Calendar) c.clone();
        start.clear(Calendar.HOUR);
        start.clear(Calendar.MINUTE);
        start.clear(Calendar.SECOND);
        start.clear(Calendar.MILLISECOND);

        Calendar end = (Calendar) c.clone();
        end.set(Calendar.HOUR, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.set(Calendar.MILLISECOND, 999);

        list[0] = start.getTimeInMillis();
        list[1] = end.getTimeInMillis();

        return list;
    }

    protected static long[] getToday(long current, int s, int e) {

        long[] list = new long[2];

        // 어제 하루 종일
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(current);
        c.add(Calendar.DATE, -1);

        Calendar start = (Calendar) c.clone();
        start.set(Calendar.HOUR, s);
        start.clear(Calendar.MINUTE);
        start.clear(Calendar.SECOND);
        start.clear(Calendar.MILLISECOND);

        Calendar end = (Calendar) c.clone();
        end.set(Calendar.HOUR, e);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.set(Calendar.MILLISECOND, 999);

        list[0] = start.getTimeInMillis();
        list[1] = end.getTimeInMillis();

        return list;
    }


    /**
     * 1 monday
     * 2.tuesday
     * 3 wednesday
     * 4 thirsday
     * 5 friday
     * 6 saturday
     * 7 sunday 기준으로 작성
     *
     * 주 단위는 운영시간을 계산하지 않음
     *
     * @param current
     * @param s
     * @param e
     * @return
     */
    protected static long[] getWeek(long current, int s, int e) {
        long[] list = new long[2];

        // 현재 요일을 검사
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(current);
        c.clear(Calendar.HOUR);
        c.clear(Calendar.MINUTE);
        c.clear(Calendar.SECOND);
        c.clear(Calendar.MILLISECOND);
        int day = c.get(Calendar.DAY_OF_WEEK);

        System.out.println("day : " + day);

        // -7 일을 뺌
        c.add(Calendar.DATE, -7);

        Calendar start = (Calendar) c.clone();
        Calendar end = (Calendar) c.clone();

        // 현재 요일에서 s 의 차이를 구함
        start.add(Calendar.DATE, (s+1) - day);
        end.add(Calendar.DATE, (e+1) - day);
        end.set(Calendar.HOUR, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.set(Calendar.MILLISECOND, 999);

        list[0] = start.getTimeInMillis();
        list[1] = end.getTimeInMillis();

        return list;
    }
}
