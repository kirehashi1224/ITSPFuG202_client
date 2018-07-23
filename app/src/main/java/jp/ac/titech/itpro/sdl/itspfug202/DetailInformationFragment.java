package jp.ac.titech.itpro.sdl.itspfug202;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jp.ac.titech.itpro.sdl.itspfug202.model.Restaurant;

public class DetailInformationFragment extends Fragment {

    public DetailInformationFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_detail_information, container, false);
        TextView detailName = view.findViewById(R.id.detail_shop_name);
        TextView detailAddress = view.findViewById(R.id.detail_shop_address);
        Restaurant restaurant = (Restaurant) getArguments().getSerializable("restaurant");
        if(restaurant != null){
            detailName.setText(restaurant.getName());
            detailAddress.setText(restaurant.getAddress());
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }
}
