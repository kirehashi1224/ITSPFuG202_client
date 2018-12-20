package jp.ac.titech.itpro.sdl.itspfug202;

import android.content.Context;
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
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import jp.ac.titech.itpro.sdl.itspfug202.model.Tag;
import jp.ac.titech.itpro.sdl.itspfug202.model.TagSection;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.randomButton).setEnabled(false);
        findViewById(R.id.searchButton).setEnabled(false);

        ApiService apiService = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(BuildConfig.API_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class);
        Observable.zip(
                apiService.getPriceTag(),
                apiService.getGenreTag(),
                apiService.getDistanceTag(),
                new Function3<List<Tag>, List<Tag>, List<Tag>, Map<TagSection.TagType, TagSection>>() {
                    @Override
                    public Map<TagSection.TagType, TagSection> apply(List<Tag> priceTagList
                            , List<Tag> genreTagList
                            , List<Tag> distanceTagList) {

                        Collections.sort(priceTagList, new Tag.TagComparator());
                        Collections.sort(genreTagList, new Tag.TagComparator());
                        Collections.sort(distanceTagList, new Tag.TagComparator());
                        Map<TagSection.TagType, TagSection> tagSectionMap = new HashMap<>();
                        tagSectionMap.put(TagSection.TagType.PriceTag, new TagSection(priceTagList));
                        tagSectionMap.put(TagSection.TagType.GenreTag, new TagSection(genreTagList));
                        tagSectionMap.put(TagSection.TagType.DistanceTag, new TagSection(distanceTagList));
                        return tagSectionMap;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Map<TagSection.TagType, TagSection>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Map<TagSection.TagType, TagSection> tagSectionMap) {
                        ExpandableListView expandableListView = findViewById(R.id.tag_list);
                        expandableListView.setAdapter(new ExpandableListAdapter(context, tagSectionMap));
                    }

                    @Override
                    public void onError(Throwable e) {
                        sendNetworkErrorMessage();
                    }

                    @Override
                    public void onComplete() {
                        findViewById(R.id.randomButton).setEnabled(true);
                        findViewById(R.id.searchButton).setEnabled(true);
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private  void sendNetworkErrorMessage(){
        Log.d("MainActivity","onResponse_Failure");
        Toast.makeText(getApplicationContext() , "ネットワークに接続されていません" , Toast.LENGTH_SHORT).show();
    }

}
