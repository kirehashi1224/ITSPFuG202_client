package jp.ac.titech.itpro.sdl.itspfug202.model;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Tag {
    private int id;
    @SerializedName("name")
    private String tag;
    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public static class TagComparator implements Comparator<Tag> {
        @Override
        public int compare(Tag tag1, Tag tag2) {
            return Integer.compare(tag1.getId(), tag2.getId());
        }
    }
}
