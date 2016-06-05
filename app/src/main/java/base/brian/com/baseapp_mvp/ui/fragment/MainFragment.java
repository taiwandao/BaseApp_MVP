package base.brian.com.baseapp_mvp.ui.fragment;

import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import base.brian.com.baseapp_mvp.R;
import base.brian.com.baseapp_mvp.constant.AppConfig;
import base.brian.com.baseapp_mvp.event.WeatherEvent;
import base.brian.com.baseapp_mvp.ui.presenter.MainPresenter;
import base.brian.com.baseapp_mvp.ui.presenterimpl.MainPresenterImpl;

/**
 * Created by Administrator on 2016/5/30 0030.
 */
public class MainFragment extends BaseFragment<MainPresenter> {
    public static BaseFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @ViewInject(R.id.tv_weather)
    TextView tv_weather;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public String getTitleString() {
        return getString(R.string.home);
    }

    @Override
    public void onBindView() {
    }

    @Override
    public void initData() {
    }

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenterImpl();
    }

    @Event({R.id.btn_weather})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_weather:
                presenter.loadWeather("101200101");
                break;
        }
    }


    @Subscribe
    public void onEvent(WeatherEvent event) {
        LogUtil.i("event...."+event);
        switch (event.eventType) {
            case AppConfig.LOAD_SUCCESS:
                LogUtil.i("LOAD_SUCCESS");
                if(event.weatherinfo!=null) {
                    tv_weather.setText(event.weatherinfo.toString());
                }else{
                    tv_weather.setText("无数据");
                }
                break;
            case AppConfig.LOAD_FAIL:
                LogUtil.i("LOAD_FAIL");
                tv_weather.setText("加载失败");
                break;
        }
    }

    @Override
    public boolean hasRegister() {
        return true;
    }
}
