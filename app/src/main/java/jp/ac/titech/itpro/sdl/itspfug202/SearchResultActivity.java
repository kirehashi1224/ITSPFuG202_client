package jp.ac.titech.itpro.sdl.itspfug202;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.ac.titech.itpro.sdl.itspfug202.model.Restaurant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchResultActivity extends AppCompatActivity {
    ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        Call<List<Restaurant>> restaurantsCall = apiService.getRestaurants();
        Log.d("Demo", "call apiService");

        final RecyclerView recyclerView = findViewById(R.id.result_recycler);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llManager);

        restaurantsCall.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                List<Restaurant> restaurants = response.body();
                /*StringBuilder restaurantsDemo = new StringBuilder();
                for (Restaurant r: restaurants) {
                    restaurantsDemo.append(r.toString());

                }
                Log.d("Demo", restaurantsDemo.toString());
                TextView demo = findViewById(R.id.demo);
                demo.setText(restaurantsDemo.toString());
                */
                RecyclerView.Adapter adapter = new SearchResultItemAdapter(restaurants);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {

            }
        });
    }
}
