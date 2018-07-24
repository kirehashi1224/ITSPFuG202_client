package jp.ac.titech.itpro.sdl.itspfug202;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import jp.ac.titech.itpro.sdl.itspfug202.model.Restaurant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantDetailActivity extends AppCompatActivity implements Serializable {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("SearchResultDetail","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_detail);

        Bundle bundle = new Bundle();
        // SearchResultActivityからの遷移
        Restaurant myRestaurant = (Restaurant)getIntent().getSerializableExtra("restaurant");
        if(myRestaurant != null){
            bundle.putSerializable("restaurant", myRestaurant);
        }

        // 詳細画面の表示
        DetailFragmentPagerAdapter adapter = new DetailFragmentPagerAdapter(getSupportFragmentManager(), bundle);
        ViewPager viewPager = findViewById(R.id.DetailviewPager);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.DetailtabLayout);
        tabLayout.setupWithViewPager(viewPager);

        // MainActivityからランダムボタンでの遷移
        if(getIntent().getStringExtra("random") != null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_ADDRESS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<List<Restaurant>> randomRestaurantsCall = apiService.getRandomRestaurants("");
            Log.d("Demo", "call apiService randomRestaurants");

            randomRestaurantsCall.enqueue(new Callback<List<Restaurant>>() {
                @Override
                public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                    Log.d("SearchResultActivity","onResponse");
                    List<Restaurant> restaurantList = response.body();
                    if(restaurantList.size() >= 1){
                        Restaurant restaurant = restaurantList.get(0);
                        TextView detailName = findViewById(R.id.detail_shop_name);
                        TextView detailAddress = findViewById(R.id.detail_shop_address);
                        TextView mondayTime = findViewById(R.id.monday_time);
                        TextView tuesdayTime = findViewById(R.id.tuesday_time);
                        TextView wednesdayTime = findViewById(R.id.wednesday_time);
                        TextView thursdayTime = findViewById(R.id.thursday_time);
                        TextView fridayTime = findViewById(R.id.friday_time);
                        TextView saturdayTime = findViewById(R.id.saturday_time);
                        TextView sundayTime = findViewById(R.id.sunday_time);
                        detailName.setText(restaurant.getName());
                        detailAddress.setText(restaurant.getAddress());
                        mondayTime.setText(restaurant.getTimeSpan(Restaurant.DayOfWeek.MONDAY));
                        tuesdayTime.setText(restaurant.getTimeSpan(Restaurant.DayOfWeek.TUESDAY));
                        wednesdayTime.setText(restaurant.getTimeSpan(Restaurant.DayOfWeek.WEDNESDAY));
                        thursdayTime.setText(restaurant.getTimeSpan(Restaurant.DayOfWeek.THURSDAY));
                        fridayTime.setText(restaurant.getTimeSpan(Restaurant.DayOfWeek.FRIDAY));
                        saturdayTime.setText(restaurant.getTimeSpan(Restaurant.DayOfWeek.SATURDAY));
                        sundayTime.setText(restaurant.getTimeSpan(Restaurant.DayOfWeek.SUNDAY));

                        /*
                        StringBuilder sb = new StringBuilder();
                        for (Restaurant.DayOfWeek dw : Restaurant.DayOfWeek.values()) {
                            sb.append(dw.toString());
                            sb.append(": ");
                            sb.append(restaurant.getTimeSpan(dw));
                            sb.append("\n");
                        }
                        detailAddress.setText(sb.toString());
                        */
                    }else{
                        Toast.makeText(RestaurantDetailActivity.this, "該当する店が存在しません", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RestaurantDetailActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<List<Restaurant>> call, Throwable t) {

                }
            });
        }


    }
}
