package jp.ac.titech.itpro.sdl.itspfug202;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;

import jp.ac.titech.itpro.sdl.itspfug202.model.Restaurant;

public class RestaurantDetail extends AppCompatActivity implements Serializable {
    private Restaurant myRestaurant;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("SearchResultDetail","onCreate");
        super.onCreate(savedInstanceState);
        //Toast.makeText(this,"poi");
        setContentView(R.layout.search_result_detail);
        TextView tx1 = findViewById(R.id.search_result_text1);
        TextView tx2 = findViewById(R.id.search_result_text2);

        myRestaurant = (Restaurant)getIntent().getSerializableExtra("restaurant");

        tx1.setText(myRestaurant.getName());
        tx2.setText(myRestaurant.getAddress());

    }
}
