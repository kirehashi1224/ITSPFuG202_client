package jp.ac.titech.itpro.sdl.itspfug202;

import java.util.List;

import io.reactivex.Observable;
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
    Observable<List<Tag>> getPriceTag();

    @GET("genre_tags")
    Observable<List<Tag>> getGenreTag();

    @GET("distance_tags")
    Observable<List<Tag>> getDistanceTag();
}
