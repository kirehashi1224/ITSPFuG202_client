package jp.ac.titech.itpro.sdl.itspfug202;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.ac.titech.itpro.sdl.itspfug202.model.Tag;
import jp.ac.titech.itpro.sdl.itspfug202.model.TagSection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Map<TagSection.TagType, TagSection> tagSectionMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Tag>> priceTagCall = apiService.getPriceTag();
        Call<List<Tag>> genreTagCall = apiService.getGenreTag();
        Call<List<Tag>> distanceTagCall = apiService.getDistanceTag();

        priceTagCall.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                tagSectionMap.put(TagSection.TagType.PriceTag, new TagSection(response.body()));
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                sendNetworkErrorMessage();
            }
        });

        genreTagCall.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                tagSectionMap.put(TagSection.TagType.GenreTag, new TagSection(response.body()));
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                sendNetworkErrorMessage();
            }
        });

        distanceTagCall.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                tagSectionMap.put(TagSection.TagType.DistanceTag, new TagSection(response.body()));
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                sendNetworkErrorMessage();
            }
        });

        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                EditText searchText = findViewById(R.id.searchText);
                intent.putExtra("name", searchText.getText().toString());
                startActivity(intent);
            }
        });

        ImageButton randomButton = findViewById(R.id.randomButton);
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RestaurantDetailActivity.class);
                intent.putExtra("random", "true");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private  void sendNetworkErrorMessage(){
        Log.d("SearchResultActivity","onResponse_Failure");
        Toast.makeText(getApplicationContext() , "ネットワークに接続されていません" , Toast.LENGTH_SHORT).show();
    }

}
