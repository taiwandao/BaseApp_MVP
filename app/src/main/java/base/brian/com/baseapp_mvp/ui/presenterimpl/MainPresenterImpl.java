package base.brian.com.baseapp_mvp.ui.presenterimpl;

import org.xutils.common.util.LogUtil;

import base.brian.com.baseapp_mvp.event.BaseEvent;
import base.brian.com.baseapp_mvp.event.WeatherEvent;
import base.brian.com.baseapp_mvp.model.business.BaseService;
import base.brian.com.baseapp_mvp.model.business.DataListener;
import base.brian.com.baseapp_mvp.model.business.WeatherService;
import base.brian.com.baseapp_mvp.ui.presenter.MainPresenter;

/**
 * Created by brian on 16/6/5.
 */
public class MainPresenterImpl extends MainPresenter implements DataListener{
    WeatherService service;
    @Override
    public void loadWeather(String cityCode) {
        service.getWeathList(cityCode,this);
    }

    @Override
    public BaseService getService() {
        service=new WeatherService();
        return service;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSuccess(BaseEvent event) {
        LogUtil.i("onSuccess");
        if(event instanceof WeatherEvent){
            WeatherEvent weatherEvent= (WeatherEvent) event;

        }
        super.onSuccess(event);
    }

    @Override
    public void onFail(BaseEvent event) {
        LogUtil.i("onFail");
        super.onFail(event);
    }
}
