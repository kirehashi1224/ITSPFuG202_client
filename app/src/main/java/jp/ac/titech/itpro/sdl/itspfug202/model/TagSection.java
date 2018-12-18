package jp.ac.titech.itpro.sdl.itspfug202.model;

import java.util.ArrayList;
import java.util.List;

public class TagSection {
    public enum TagType{
        PriceTag("価格帯"),
        GenreTag("ジャンル"),
        DistanceTag("東工大からの所要時間");
        private String label;
        TagType(String label){
            this.label = label;
        }
        public String getLabel(){
            return this.label;
        }
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
