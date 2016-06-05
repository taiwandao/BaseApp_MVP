package base.brian.com.baseapp_mvp;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

import base.brian.com.baseapp_mvp.constant.AppConfig;

/**
 * Created by brian on 16/6/5.
 */
public class BrianApplication extends Application{

    /**
     * 应用内上下文都使用这个
     */
    public static BrianApplication app;
    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        //初始化xutils
        x.Ext.init(this);
        x.Ext.setDebug(AppConfig.DEBUG);
    }

    public static Context getInstance() {
        return app;
    }

}
