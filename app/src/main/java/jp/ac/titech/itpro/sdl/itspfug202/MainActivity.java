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

import java.util.List;

import jp.ac.titech.itpro.sdl.itspfug202.model.DistanceTag;
import jp.ac.titech.itpro.sdl.itspfug202.model.GenreTag;
import jp.ac.titech.itpro.sdl.itspfug202.model.PriceTag;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

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
        Call<List<PriceTag>> priceTagCall = apiService.getPriceTag();
        Call<List<GenreTag>> genreTagCall = apiService.getGenreTag();
        Call<List<DistanceTag>> distanceTagCall = apiService.getDistanceTag();

        priceTagCall.enqueue(new Callback<List<PriceTag>>() {
            @Override
            public void onResponse(Call<List<PriceTag>> call, Response<List<PriceTag>> response) {
                List<PriceTag> priceTagList = response.body();
            }

            @Override
            public void onFailure(Call<List<PriceTag>> call, Throwable t) {
                sendNetworkErrorMessage();
            }
        });

        genreTagCall.enqueue(new Callback<List<GenreTag>>() {
            @Override
            public void onResponse(Call<List<GenreTag>> call, Response<List<GenreTag>> response) {
                List<GenreTag> genreTagList = response.body();
            }

            @Override
            public void onFailure(Call<List<GenreTag>> call, Throwable t) {
                sendNetworkErrorMessage();
            }
        });

        distanceTagCall.enqueue(new Callback<List<DistanceTag>>() {
            @Override
            public void onResponse(Call<List<DistanceTag>> call, Response<List<DistanceTag>> response) {
                List<DistanceTag> distanceTagList = response.body();
            }

            @Override
            public void onFailure(Call<List<DistanceTag>> call, Throwable t) {
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
