package jp.ac.titech.itpro.sdl.itspfug202;

import java.util.List;

import jp.ac.titech.itpro.sdl.itspfug202.model.Restaurant;
import jp.ac.titech.itpro.sdl.itspfug202.model.Tag;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("restaurants")
    Call<List<Restaurant>> getRestaurants(@Query("name") String name);

    @GET("restaurants/?random_extract=true")
    Call<List<Restaurant>> getRandomRestaurants(@Query("name") String name);

    @GET("restaurants")
    Call<List<Restaurant>> getRestaurant(@Query("id") int restaurantId);

    @GET("price_tags")
    Call<List<Tag>> getPriceTag();

    @GET("genre_tags")
    Call<List<Tag>> getGenreTag();

    @GET("distance_tags")
    Call<List<Tag>> getDistanceTag();
}
