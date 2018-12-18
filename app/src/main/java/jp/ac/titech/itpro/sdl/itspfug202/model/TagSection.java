package jp.ac.titech.itpro.sdl.itspfug202.model;

import java.util.ArrayList;
import java.util.List;

public class TagSection {
    public enum TagType{
        PriceTag,
        GenreTag,
        DistanceTag
    }

    private String mSectionText;
    private List<Tag> tagList;

    public TagSection(){
        mSectionText = "";
        tagList = new ArrayList<>();
    }

    public TagSection(List<Tag> tagList){
        mSectionText = "";
        this.tagList = tagList;
    }

    public String getSectionText() {
        return mSectionText;
    }

    public void setSectionText(String sectionText) {
        mSectionText = sectionText;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }
}
