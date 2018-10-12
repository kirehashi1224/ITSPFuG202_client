package jp.ac.titech.itpro.sdl.itspfug202.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//import java.util.Date;

public class Restaurant implements Serializable{

    private int id;
    private String name;
    private String address;
    @SerializedName("timespans")
    private String timeSpanString;

    private TimeSpanList timeSpanList;
    //private String openTime;
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

    public String getTimeSpan(DayOfWeek dw) {
        if (timeSpanList == null) {
            String[] times = timeSpanString.split(",");
            this.timeSpanList = new TimeSpanList(times.length);
            for (int i = 0; i < times.length; i++) {
                String[] spans = times[i].split("/");
                for (int j = 0; j < spans.length; j++) {
                    String[] time = spans[j].split(";");
                    if (time.length != 2) continue;
                    String[] begin = time[0].split(":");
                    String[] end = time[1].split(":");
                    this.timeSpanList.setTimespanList(DayOfWeek.values()[i], new TimeSpan(new Time(Integer.parseInt(begin[0]), Integer.parseInt(begin[1])), new Time(Integer.parseInt(end[0]), Integer.parseInt(end[1]))));
                }
            }
        }
        List<TimeSpan> lis = timeSpanList.getTimespanList(dw);
        if (lis.size() == 0) return "定休日";
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
                '}';
}
    // TODO:isOpen()

    //public List<Date>
    public class TimeSpanList implements Serializable{
        private List<TimeSpan>[] ls;
        public TimeSpanList(int wc) {
            ls = new List[wc];
            for (int i = 0; i < wc; ++i) {
                ls[i] = new ArrayList<TimeSpan>();
            }
        }
        public List<TimeSpan> getTimespanList(DayOfWeek dw){
            switch (dw){
                case SUNDAY:
                    return ls[6];
                case MONDAY:
                    return ls[0];
                case TUESDAY:
                    return ls[1];
                case WEDNESDAY:
                    return ls[2];
                case THURSDAY:
                    return ls[3];
                case FRIDAY:
                    return ls[4];
                case SATURDAY:
                    return ls[5];
                default: return null;
            }
        }
        public void setTimespanList(DayOfWeek dw, TimeSpan sp){
            switch (dw){
                case SUNDAY:
                    ls[6].add(sp);
                    break;
                case MONDAY:
                    ls[0].add(sp);
                    break;
                case TUESDAY:
                    ls[1].add(sp);
                    break;
                case WEDNESDAY:
                    ls[2].add(sp);
                    break;
                case THURSDAY:
                    ls[3].add(sp);
                    break;
                case FRIDAY:
                    ls[4].add(sp);
                    break;
                case SATURDAY:
                    ls[5].add(sp);
                    break;
            }
        }
    }
    class Time implements Serializable{
        int hour;
        int minute;
        Time(int hour,int minute){
            this.hour = hour;
            this.minute = minute;
        }
        @Override
        public String toString() {
            return String.format("%d:%02d", hour, minute);
        }
    }
    public enum DayOfWeek implements Serializable{
        SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY
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
