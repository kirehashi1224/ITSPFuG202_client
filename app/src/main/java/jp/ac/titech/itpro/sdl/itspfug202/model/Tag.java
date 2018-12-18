package jp.ac.titech.itpro.sdl.itspfug202.model;

import com.google.gson.annotations.SerializedName;

public class Tag {
    private int id;
    @SerializedName("name")
    private String tag;

    public int getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }
}
