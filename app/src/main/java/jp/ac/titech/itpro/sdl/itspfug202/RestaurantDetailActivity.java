package jp.ac.titech.itpro.sdl.itspfug202;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

import jp.ac.titech.itpro.sdl.itspfug202.model.Restaurant;

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
        // MainActivityからランダムボタンでの遷移
        if(getIntent().getStringExtra("random") != null){
            bundle.putString("random", "true");
        }

        // 詳細画面の表示
        DetailFragmentPagerAdapter adapter = new DetailFragmentPagerAdapter(getSupportFragmentManager(), bundle);
        ViewPager viewPager = findViewById(R.id.DetailviewPager);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.DetailtabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
