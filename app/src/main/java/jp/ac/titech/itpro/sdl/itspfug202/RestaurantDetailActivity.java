package jp.ac.titech.itpro.sdl.itspfug202;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import jp.ac.titech.itpro.sdl.itspfug202.model.Restaurant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantDetailActivity extends AppCompatActivity implements Serializable {
    private Restaurant myRestaurant;
    private ApiService apiService;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("SearchResultDetail","onCreate");
        super.onCreate(savedInstanceState);
        //Toast.makeText(this,"poi");
        setContentView(R.layout.search_result_detail);
        TextView tx1 = findViewById(R.id.search_result_text1);
        TextView tx2 = findViewById(R.id.search_result_text2);

        // SearchResultActivityからの遷移
        myRestaurant = (Restaurant)getIntent().getSerializableExtra("restaurant");
        if(myRestaurant != null){
            tx1.setText(myRestaurant.getName());
            tx2.setText(myRestaurant.getAddress());
        }

        // MainActivityからランダムボタンでの遷移
        if(getIntent().getStringExtra("random") != null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_ADDRESS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService = retrofit.create(ApiService.class);
            Call<List<Restaurant>> randomRestaurantsCall = apiService.getRandomRestaurants("");
            Log.d("Demo", "call apiService randomRestaurants");

            randomRestaurantsCall.enqueue(new Callback<List<Restaurant>>() {
                @Override
                public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                    Log.d("SearchResultActivity","onResponse");
                    Restaurant restaurant = response.body().get(0);
                    TextView detailName = findViewById(R.id.search_result_text1);
                    TextView detailAddress = findViewById(R.id.search_result_text2);
                    detailName.setText(restaurant.getName());
                    detailAddress.setText(restaurant.getAddress());
                }

                @Override
                public void onFailure(Call<List<Restaurant>> call, Throwable t) {

                }
            });
        }
    }
}
