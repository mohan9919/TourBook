package NearBy;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by User on 12/20/2016.
 */

public interface NearbyPlaceServiceApi {
    @GET("")
    Call<NearbyPlaces>getAllResponse(@Url String urlString);
}

