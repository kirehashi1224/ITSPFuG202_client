package jp.ac.titech.itpro.sdl.itspfug202;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import jp.ac.titech.itpro.sdl.itspfug202.model.Restaurant;
import jp.ac.titech.itpro.sdl.itspfug202.model.Tag;
import jp.ac.titech.itpro.sdl.itspfug202.model.TagSection;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static jp.ac.titech.itpro.sdl.itspfug202.ExpandableListAdapter.tagSectionMap;

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
                .client(new OkHttpClient())
                .baseUrl(BuildConfig.API_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        // チェックボックスの状態からクエリに付与するタグを取得
        StringBuilder priceQueryBuilder = new StringBuilder();
        StringBuilder genreQueryBuilder = new StringBuilder();
        StringBuilder distanceQueryBuilder = new StringBuilder();
        for(Tag t : tagSectionMap.get(TagSection.TagType.PriceTag).getTagList()){
            if(t.isChecked()){
                if(priceQueryBuilder.length() > 0){
                    priceQueryBuilder.append("&priceTags=");
                }
                priceQueryBuilder.append(t.getId());
            }
        }
        for(Tag t : tagSectionMap.get(TagSection.TagType.GenreTag).getTagList()){
            if(t.isChecked()){
                if(genreQueryBuilder.length() > 0){
                    genreQueryBuilder.append("&genreTags=");
                }
                genreQueryBuilder.append(t.getId());
            }
        }
        for(Tag t : tagSectionMap.get(TagSection.TagType.DistanceTag).getTagList()){
            if(t.isChecked()){
                if(distanceQueryBuilder.length() > 0){
                    distanceQueryBuilder.append("&distanceTags=");
                }
                distanceQueryBuilder.append(t.getId());
            }
        }
        String priceQuery = null, genreQuery = null, distanceQuery = null;
        if(priceQueryBuilder.length() > 0){
            priceQuery = priceQueryBuilder.toString();
        }
        if(genreQueryBuilder.length() > 0){
            genreQuery = genreQueryBuilder.toString();
        }
        if(distanceQueryBuilder.length() > 0){
            distanceQuery = distanceQueryBuilder.toString();
        }
        Call<List<Restaurant>> restaurantsCall = apiService.getRestaurants(intent.getStringExtra("name"),
                                                                            priceQuery,
                                                                            genreQuery,
                                                                            distanceQuery);
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
                Log.d("SearchResultActivity","onResponse_Failure");
                Toast.makeText(getApplicationContext() , "ネットワークに接続されていません" , Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
