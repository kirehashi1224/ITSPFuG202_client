package jp.ac.titech.itpro.sdl.itspfug202.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
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
                        this.timeSpanList.setTimeSpanList(DayOfWeek.values()[i], new TimeSpan(new Time(Integer.parseInt(begin[0]), Integer.parseInt(begin[1])), new Time(Integer.parseInt(end[0]), Integer.parseInt(end[1]))));
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
    // TODO: isOpen()

    public class TimeSpanList implements Serializable{
        private List<TimeSpan>[] ls;
        public TimeSpanList(int wc) {
            ls = new List[wc];
            for (int i = 0; i < wc; ++i) {
                ls[i] = new ArrayList<>();
            }
        }
        public List<TimeSpan> getTimeSpanList(DayOfWeek dw){
            return (dw.getOrd() < ls.length) ? ls[dw.getOrd()] : null;
        }
        public void setTimeSpanList(DayOfWeek dw, TimeSpan sp){
            if (dw.getOrd() < ls.length) ls[dw.getOrd()].add(sp);
        }
    }
    class Time implements Serializable{
        int hour;
        int minute;
        Time(int hour, int minute){
            this.hour = hour;
            this.minute = minute;
        }
        @Override
        public String toString() {
            return String.format("%d:%02d", hour, minute);
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

        private int ord;
        private String label;
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
    }
    class TimeSpan implements Serializable{
        Time open;
        Time close;
        TimeSpan(Time open, Time close){
            this.open = open;
            this.close = close;
        }
        @Override
        public String toString() {
            return open.toString() + " - " + close.toString();
        }
    }
}
