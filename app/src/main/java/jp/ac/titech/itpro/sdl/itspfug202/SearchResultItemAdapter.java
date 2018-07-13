package jp.ac.titech.itpro.sdl.itspfug202;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jp.ac.titech.itpro.sdl.itspfug202.model.Restaurant;

public class SearchResultItemAdapter extends RecyclerView.Adapter<SearchResultItemViewHolder>{

    private List<Restaurant> restaurantList;

    public SearchResultItemAdapter(List<Restaurant> restaurantList){
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public SearchResultItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("SearchResultItemAdapter","onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_item_layout, parent, false);
        return new SearchResultItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultItemViewHolder holder, int position) {
        Log.d("SearchResultItemAdapter","onBindViewHolder");
        holder.nameText.setText(restaurantList.get(position).getName());
        holder.addressText.setText(restaurantList.get(position).getAddress());
        final int pos = position;

        holder.base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SearchResultItemAdapter","onClick");
                Intent intent = new Intent(v.getContext(),RestaurantDetail.class);
                intent.putExtra("restaurant",restaurantList.get(pos));
                v.getContext().startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return restaurantList.size();
    }
}
