package jp.ac.titech.itpro.sdl.itspfug202;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import jp.ac.titech.itpro.sdl.itspfug202.model.Restaurant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailInformationFragment extends Fragment {
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) return;
        context = activity.getApplicationContext();
    }

    public DetailInformationFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_detail_information, container, false);
        final TextView detailName = view.findViewById(R.id.detail_shop_name);
        final TextView detailAddress = view.findViewById(R.id.detail_shop_address);
        final ImageView detailShopImage = view.findViewById(R.id.detail_shop_image);
        final TextView mondayTime = view.findViewById(R.id.monday_time);
        final TextView tuesdayTime = view.findViewById(R.id.tuesday_time);
        final TextView wednesdayTime = view.findViewById(R.id.wednesday_time);
        final TextView thursdayTime = view.findViewById(R.id.thursday_time);
        final TextView fridayTime = view.findViewById(R.id.friday_time);
        final TextView saturdayTime = view.findViewById(R.id.saturday_time);
        final TextView sundayTime = view.findViewById(R.id.sunday_time);
        final Button mapButton = view.findViewById(R.id.map_button);
        final TextView detailIsOpen = view.findViewById(R.id.detail_isopen);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Restaurant>> restaurantCall;
        Bundle bundle = getArguments();
        if(bundle == null){
            throw new IllegalStateException("Bundleが空になっています");
        }else if(bundle.containsKey("random")){
            restaurantCall = apiService.getRandomRestaurants("");
        }else if(bundle.containsKey("restaurant")){
            Restaurant restaurant = (Restaurant) getArguments().getSerializable("restaurant");
            Log.d("RestaurantId",String.valueOf(restaurant.getid()));
            restaurantCall = apiService.getRestaurant(restaurant.getid());
        }else{
            throw new IllegalStateException("Bundleの値に設定された値が不適切です");
        }
        restaurantCall.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                Log.d("SearchResultActivity","onResponse");
                List<Restaurant> restaurantList = response.body();
                if(restaurantList.size() >= 1){
                    final Restaurant restaurant = restaurantList.get(0);
                    detailName.setText(restaurant.getName());
                    detailAddress.setText(restaurant.getAddress());
                    mondayTime.setText(restaurant.getTimeSpan(Restaurant.DayOfWeek.MONDAY));
                    tuesdayTime.setText(restaurant.getTimeSpan(Restaurant.DayOfWeek.TUESDAY));
                    wednesdayTime.setText(restaurant.getTimeSpan(Restaurant.DayOfWeek.WEDNESDAY));
                    thursdayTime.setText(restaurant.getTimeSpan(Restaurant.DayOfWeek.THURSDAY));
                    fridayTime.setText(restaurant.getTimeSpan(Restaurant.DayOfWeek.FRIDAY));
                    saturdayTime.setText(restaurant.getTimeSpan(Restaurant.DayOfWeek.SATURDAY));
                    sundayTime.setText(restaurant.getTimeSpan(Restaurant.DayOfWeek.SUNDAY));
                    // これね、文字列定数は string.xml で定義すべき
                    detailIsOpen.setText(restaurant.isOpen() ? "営業中" : "閉店中");

                    // Picasso.get().load(BuildConfig.ROOT_ADDRESS + "media/oppai.png").into(detailShopImage);
                    if(restaurant.getImage_path() != null && restaurant.getImage_path().length() > 0){
                        Picasso.get().load(BuildConfig.ROOT_ADDRESS + "media/" + restaurant.getImage_path()).placeholder(context.getResources().getDrawable(R.drawable.default_shop)).into(detailShopImage);
                    }

                    //map_buttonの動作
                    mapButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse("geo:0,0?q="+restaurant.getAddress());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    });
                }else{
                    Toast.makeText(getActivity(), "該当する店が存在しません", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.d("SearchResultActivity","onResponse_Failure");
                Toast.makeText(context , "ネットワークに接続されていません" , Toast.LENGTH_SHORT).show();
                try {
                    ((Activity)context).finish();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }
}
