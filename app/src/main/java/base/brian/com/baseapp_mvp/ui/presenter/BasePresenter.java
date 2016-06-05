package base.brian.com.baseapp_mvp.ui.presenter;


import org.greenrobot.eventbus.EventBus;

import base.brian.com.baseapp_mvp.event.BaseEvent;
import base.brian.com.baseapp_mvp.model.business.BaseService;
import base.brian.com.baseapp_mvp.model.business.DataListener;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public abstract class BasePresenter implements DataListener {
    public abstract BaseService getService();

    public BasePresenter() {
         getService();  //--------调用这个方法初始化service
    }

    public void onDestroy() {
        if(getService() != null) {
            getService().destroy();
        }
    }

    @Override
    public void onSuccess(BaseEvent event) {
        EventBus.getDefault().post(event);
    }

    @Override
    public void onFail(BaseEvent event) {
        EventBus.getDefault().post(event);
    }
}
