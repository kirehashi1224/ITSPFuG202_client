package jp.ac.titech.itpro.sdl.itspfug202;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Map;

import jp.ac.titech.itpro.sdl.itspfug202.model.Tag;
import jp.ac.titech.itpro.sdl.itspfug202.model.TagSection;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    static Map<TagSection.TagType, TagSection> tagSectionMap;
    private Context context;
    private LayoutInflater inflater;

    public ExpandableListAdapter(Context context,
                                 Map<TagSection.TagType, TagSection> tagSectionMap) {
        this.context = context;
        ExpandableListAdapter.tagSectionMap = tagSectionMap;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return tagSectionMap.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getGroup(groupPosition).getTagList().size();
    }

    @Override
    public TagSection getGroup(int groupPosition) {
        return tagSectionMap.get(TagSection.TagType.values()[groupPosition]);
    }

    @Override
    public Tag getChild(int groupPosition, int childPosition) {
        return getGroup(groupPosition).getTagList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getGroup(groupPosition).getTagList().get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.expandable_section, null);
        }
        TextView text = convertView.findViewById(R.id.expandable_section_text);
        text.setText(TagSection.TagType.values()[groupPosition].getLabel());

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.expandable_item, null);
        }
        final CheckBox checkBox = convertView.findViewById(R.id.expandable_check_box);
        checkBox.setText(getChild(groupPosition, childPosition).getTag());
        checkBox.setChecked(getChild(groupPosition, childPosition).isChecked());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // チェック状態変更し、状態をtagSectionMapに保存
                if(getChild(groupPosition, childPosition).isChecked()){
                    checkBox.setSelected(false);
                    getChild(groupPosition, childPosition).setChecked(false);
                    Log.d("Tag_onCheckedChenged", getChild(groupPosition, childPosition).getTag()+" : false");
                }else{
                    checkBox.setSelected(true);
                    getChild(groupPosition, childPosition).setChecked(true);
                    Log.d("Tag_onCheckedChenged", getChild(groupPosition, childPosition).getTag()+" : true");
                }
            }
        });
        /*checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // チェック状態を保存
                getChild(groupPosition, childPosition).setChecked(isChecked);
                Log.d("Tag_onCheckedChenged", getChild(groupPosition, childPosition).getTag()+" : "+isChecked);
            }
        });*/

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
