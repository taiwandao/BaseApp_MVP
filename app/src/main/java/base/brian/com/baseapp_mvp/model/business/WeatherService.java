package base.brian.com.baseapp_mvp.model.business;

import base.brian.com.baseapp_mvp.event.BaseEvent;
import base.brian.com.baseapp_mvp.event.WeatherEvent;
import base.brian.com.baseapp_mvp.util.ObservableUtil;
import rx.Observable;

/**
 * Created by brian on 16/6/5.
 */
public class WeatherService extends BaseService{

    public void getWeathList(String cityCode,DataListener listener) {
        Observable<WeatherEvent> observable = apiService.getWeather(cityCode);
        observable = ObservableUtil.transformation(observable);
        compositeSubscription.add(
                observable.subscribe(ObservableUtil.getSuccessAction(listener), ObservableUtil.getErrAction(new WeatherEvent(), listener))
        );
    }

}
