package base.brian.com.baseapp_mvp.network;


import base.brian.com.baseapp_mvp.event.WeatherEvent;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by brian on 16/6/5.
 */
public interface ApiService {

    @GET("data/sk/{cityCode}.html")
    Observable<WeatherEvent> getWeather(@Path("cityCode") String cityCode);
}
