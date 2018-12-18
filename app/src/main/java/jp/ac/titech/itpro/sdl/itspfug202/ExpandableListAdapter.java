package jp.ac.titech.itpro.sdl.itspfug202;

import android.content.Context;
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
    private Map<TagSection.TagType, TagSection> tagSectionMap;
    private Context context;
    private LayoutInflater inflater;

    public ExpandableListAdapter(Context context,
                                 Map<TagSection.TagType, TagSection> tagSectionMap) {
        this.context = context;
        this.tagSectionMap = tagSectionMap;
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
        /*Log.d("ExpandableListAdapter","A");
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Log.d("ExpandableListAdapter","B");

        TextView textView = new TextView(context);
        textView.setLayoutParams(param);

        Log.d("ExpandableListAdapter","C");

        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        textView.setPadding(0, 0, 0, 0);
        textView.setText(TagSection.TagType.values()[groupPosition].getLabel());*/

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.expandable_item, null);
        }
        CheckBox checkBox = convertView.findViewById(R.id.expandable_check_box);
        checkBox.setText(getChild(groupPosition, childPosition).getTag());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
