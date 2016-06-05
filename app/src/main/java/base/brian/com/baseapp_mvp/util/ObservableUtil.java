package base.brian.com.baseapp_mvp.util;


import org.xutils.common.util.LogUtil;

import base.brian.com.baseapp_mvp.CustomException;
import base.brian.com.baseapp_mvp.constant.AppConfig;
import base.brian.com.baseapp_mvp.event.BaseEvent;
import base.brian.com.baseapp_mvp.model.business.DataListener;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
public class ObservableUtil {

    public static Observable transformation(Observable<? extends BaseEvent> observable) {
        return observable
                .map(new Func1<BaseEvent, BaseEvent>() {
                    @Override
                    public BaseEvent call(BaseEvent event) {
//                        if (event.rtnCode != AppConfig.CODE_OK) {
//                            throw new CustomException(event.rtnMsg);
//                        }
                        LogUtil.i(">>>>call");
                        return event;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }


    /**
     * 一个页面有几个请求，则需要使用自定义的eventType
     */
    public static Action1<BaseEvent> getSuccessAction(final int eventType, final DataListener listener) {
        return new Action1<BaseEvent>() {
            @Override
            public void call(BaseEvent baseEvent) {
                baseEvent.eventType = eventType;
                if(listener == null) return;
                listener.onSuccess(baseEvent);
            }
        };
    }

    /**
     * 默认返回的成功action
     */
    public static Action1<BaseEvent> getSuccessAction(final DataListener listener) {
        return new Action1<BaseEvent>() {
            @Override
            public void call(BaseEvent baseEvent) {
                LogUtil.i("getSuccessAction>>>>");
                baseEvent.eventType = AppConfig.LOAD_SUCCESS;
                if(listener == null) return;
                listener.onSuccess(baseEvent);
            }
        };
    }


    /**
     * 根据tab来发送成功事件
     */
    public static Action1<BaseEvent> getSuccessActionTab(final int tabPosition, final DataListener listener) {
        return new Action1<BaseEvent>() {
            @Override
            public void call(BaseEvent baseEvent) {
                baseEvent.eventType = AppConfig.LOAD_SUCCESS;
                baseEvent.tabPosition = tabPosition;
                if(listener == null) return;
                listener.onSuccess(baseEvent);
            }
        };
    }


    public static Action1<Throwable> getErrAction(final BaseEvent event, final DataListener listener) {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (!event.hasType) {
                    event.eventType = AppConfig.LOAD_FAIL;
                }
                event.throwable = throwable;
                if(listener == null) return;
                listener.onFail(event);
                LogUtil.e(throwable + "");
            }
        };
    }

    public static Action1<Throwable> getErrAction(final BaseEvent event, final int tabPosition, final DataListener listener) {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (!event.hasType) {
                    event.eventType = AppConfig.LOAD_FAIL;
                }
                event.throwable = throwable;
                event.tabPosition = tabPosition;
                if(listener == null) return;
               listener.onFail(event);
                LogUtil.e(throwable + "");
            }
        };
    }
}
