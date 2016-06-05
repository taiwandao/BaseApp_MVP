package base.brian.com.baseapp_mvp.model.business;


import base.brian.com.baseapp_mvp.event.BaseEvent;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public interface DataListener {
    void onSuccess(BaseEvent event);
    void onFail(BaseEvent event);
}
