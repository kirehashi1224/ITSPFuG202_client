package jp.ac.titech.itpro.sdl.itspfug202;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import java.io.Serializable;

import jp.ac.titech.itpro.sdl.itspfug202.model.Restaurant;

public class SearchResultDetail extends AppCompatActivity implements Serializable {
    private Restaurant myRestaurant;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        myRestaurant = (Restaurant)getIntent().getSerializableExtra("restaurant");
    }
}
