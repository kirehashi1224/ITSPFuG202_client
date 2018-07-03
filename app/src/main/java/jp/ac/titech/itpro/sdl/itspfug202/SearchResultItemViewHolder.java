package jp.ac.titech.itpro.sdl.itspfug202;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class SearchResultItemViewHolder extends RecyclerView.ViewHolder{
    View base;
    TextView nameText;
    TextView addressText;

    public SearchResultItemViewHolder(View v) {
        super(v);
        this.base = v;
        this.nameText = v.findViewById(R.id.restaurant_name);
        this.addressText = v.findViewById(R.id.restaurant_address);
    }
}
