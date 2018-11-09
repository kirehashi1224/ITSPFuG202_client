package jp.ac.titech.itpro.sdl.itspfug202.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Restaurant implements Serializable{

    private int id;
    private String name;
    private String address;
    @SerializedName("image")
    private String image_path;
    @SerializedName("timespans")
    private String timeSpanString;

    private TimeSpanList timeSpanList;
    public int getid() {
        return id;
    }

    public TimeSpanList getTimeSpanList() {
        return timeSpanList;
    }
    public void setTimeSpanList(TimeSpanList timeSpanList) {
        this.timeSpanList = timeSpanList;
    }

    public String getTimeSpanString() {
        return timeSpanString;
    }

    public void setTimeSpanString(String timeSpanString) {
        this.timeSpanString = timeSpanString;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getTimeSpan(DayOfWeek dw) {
        if (timeSpanList == null) {
            if (timeSpanString == null || timeSpanString.length() < 1 || timeSpanString.indexOf('?') != -1) {
                return "準備中";
            }
            String[] times = timeSpanString.split("\\|", -1);
            this.timeSpanList = new TimeSpanList(times.length);
            for (int i = 0; i < times.length; i++) {
                String[] spans = times[i].split("/", -1);
                for (String span : spans) {
                    String[] time = span.split(";", -1);
                    if (time.length != 2) continue;
                    String[] begin = time[0].split(":", -1);
                    String[] end = time[1].split(":", -1);
                    try {
                        this.timeSpanList.setTimeSpanList(DayOfWeek.get(i),
                                new TimeSpan(new Time(Integer.parseInt(begin[0]), Integer.parseInt(begin[1])),
                                             new Time(Integer.parseInt(end[0]), Integer.parseInt(end[1]))));
                    } catch (NumberFormatException e) { }
                }
            }
        }
        List<TimeSpan> lis = timeSpanList.getTimeSpanList(dw);
        if (lis == null || lis.size() == 0) return "定休日";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lis.size(); ++i) {
            if (i > 0) sb.append(" / ");
            sb.append(lis.get(i));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", image_path='" + image_path + '\'' +
                '}';
    }

    public boolean isOpen() {
        final Calendar now = Calendar.getInstance();
        return isOpen(DayOfWeek.convertFromCalendar(now), new Time(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE)));
    }

    public boolean isOpen(DayOfWeek week, Time time) {
        return containTime(timeSpanList.getTimeSpanList(week), time)
                || containTime(timeSpanList.getTimeSpanList(week.prev()), new Time(time.hour + 24, time.minute));
    }

    private boolean containTime(List<TimeSpan> timeSpans, Time time) {
        if (timeSpans == null) return false;
        for (TimeSpan timespan: timeSpans)
            if (timespan.open.compareTo(time) <= 0 && timespan.close.compareTo(time) >= 0)
                return true;

        return false;
    }

    public class TimeSpanList implements Serializable{
        private List<TimeSpan>[] ls;
        public TimeSpanList(int wc) {
            ls = new List[wc];
            for (int i = 0; i < wc; ++i) {
                ls[i] = new ArrayList<>();
            }
        }
        public List<TimeSpan> getTimeSpanList(DayOfWeek dw){
            return (dw != null && dw.getOrd() < ls.length) ? ls[dw.getOrd()] : null;
        }
        public void setTimeSpanList(DayOfWeek dw, TimeSpan sp){
            if (dw != null && dw.getOrd() < ls.length) ls[dw.getOrd()].add(sp);
        }
    }
    public class Time implements Serializable{
        int hour;
        int minute;
        public Time(int hour, int minute){
            this.hour = hour;
            this.minute = minute;
        }
        @Override
        public String toString() {
            return String.format("%d:%02d", hour, minute);
        }

        public int compareTo(Time time) {
            if (Integer.compare(hour, time.hour) != 0)
                return Integer.compare(hour, time.hour);
            return Integer.compare(minute, time.minute);
        }
    }
    public enum DayOfWeek implements Serializable{
        MONDAY(0, "月"),
        TUESDAY(1, "火"),
        WEDNESDAY(2, "水"),
        THURSDAY(3, "木"),
        FRIDAY(4, "金"),
        SATURDAY(5, "土"),
        SUNDAY(6, "日");

        private final int ord;
        private final String label;
        DayOfWeek(int ord, String label) {
            this.ord = ord;
            this.label = label;
        }

        int getOrd() {
            return ord;
        }
        String getLabel() {
            return label;
        }

        static DayOfWeek convertFromCalendar(Calendar calendar) {
            switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY:    return SUNDAY;
                case Calendar.MONDAY:    return MONDAY;
                case Calendar.TUESDAY:   return TUESDAY;
                case Calendar.WEDNESDAY: return WEDNESDAY;
                case Calendar.THURSDAY:  return THURSDAY;
                case Calendar.FRIDAY:    return FRIDAY;
                case Calendar.SATURDAY:  return SATURDAY;
                default:                 return null;
            }
        }

        private static final DayOfWeek[] ALL = new Object(){
            DayOfWeek[] run() {
                DayOfWeek[] arr = new DayOfWeek[DayOfWeek.values().length];
                for (DayOfWeek dayOfWeek: DayOfWeek.values())
                    arr[dayOfWeek.ord] = dayOfWeek;
                return arr;
            }
        }.run();

        static DayOfWeek get(int ord) {
            return (ord >= 0 && ord < ALL.length) ? ALL[ord] : null;
        }
        DayOfWeek next() {
            return get((ord + 1) % ALL.length);
        }
        DayOfWeek prev() {
            return get((ord + ALL.length - 1) % ALL.length);
        }
    }
    public class TimeSpan implements Serializable{
        Time open;
        Time close;
        public TimeSpan(Time open, Time close){
            this.open = open;
            this.close = close;
        }
        @Override
        public String toString() {
            return open.toString() + " - " + close.toString();
        }
    }
}
