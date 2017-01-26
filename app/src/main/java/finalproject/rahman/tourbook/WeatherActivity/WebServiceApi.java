package finalproject.rahman.tourbook.WeatherActivity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Bdjobs on 12/20/2016.
 */

public interface WebServiceApi {


    @GET("")
    Call<WebResponse> getAllSevenDays(@Url String url);


}
