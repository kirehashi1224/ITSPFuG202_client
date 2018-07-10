package jp.ac.titech.itpro.sdl.itspfug202;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import jp.ac.titech.itpro.sdl.itspfug202.model.Restaurant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchResultActivity extends AppCompatActivity {
    ApiService apiService;
    SearchResultItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("SearchResultActivity","onCreate");
        setContentView(R.layout.activity_search_result);
        Intent intent = getIntent();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        Call<List<Restaurant>> restaurantsCall = apiService.getRestaurants(intent.getStringExtra("name"));
        Log.d("Demo", "call apiService");

        final RecyclerView recyclerView = findViewById(R.id.result_recycler);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llManager);

        restaurantsCall.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                Log.d("SearchResultActivity","onResponse");
                List<Restaurant> restaurants = response.body();
                //RecyclerView.Adapter adapter = new SearchResultItemAdapter(restaurants);
                adapter = new SearchResultItemAdapter(restaurants);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {

            }
        });
    }
}
