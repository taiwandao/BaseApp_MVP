package base.brian.com.baseapp_mvp.network;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.util.LogUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import base.brian.com.baseapp_mvp.BrianApplication;
import base.brian.com.baseapp_mvp.CustomException;
import base.brian.com.baseapp_mvp.constant.AppConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by brian on 16/6/5.
 */
public class RetrofitManager {
    public final int TIME_OUT = 30;

    private ApiService mApiService;

    private static volatile OkHttpClient mOkHttpClient;
    //多个地址存储
    public static Map<String, RetrofitManager> managers = new HashMap<>();

    public static RetrofitManager getInstance(String url) {
        RetrofitManager instance = managers.get(url);
        if (instance == null) {
            instance = new RetrofitManager(url);
            managers.put(url, instance);
        }
        return instance;
    }


    public static RetrofitManager getInstance() {
        return getInstance(AppConfig.IP);
    }

    private RetrofitManager(String url) {
        initOkHttpClient();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
        mApiService=retrofit.create(ApiService.class);
    }


    private void initOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (mOkHttpClient == null) {
                    HttpLoggingInterceptor log = new HttpLoggingInterceptor();
                    if (AppConfig.DEBUG) {
                        log.setLevel(
                                HttpLoggingInterceptor.Level.BODY
                        );
                    } else {
                        log.setLevel(HttpLoggingInterceptor.Level.NONE);
                    }
                    mOkHttpClient = new OkHttpClient.Builder().addInterceptor(log)
                            .retryOnConnectionFailure(true)
                            .addInterceptor(netInterceptor)
                            .connectTimeout(TIME_OUT, TimeUnit.SECONDS).build();
                }
            }

        }
    }

    //统一的头部处理
    Interceptor netInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
//            String net_check = request.header("net_check");
//            if(net_check == null) {  //默认检查网络是否连接
//                if(!isNetworkAvailable(BrianApplication.app)) {
//                    throw new CustomException(CustomException.NO_NET, "网络不给力哦~请检查您的网络");
//                }
//            } else {
//                request = request.newBuilder().removeHeader("net_check").build();
//            }
            LogUtil.i("head>>>>"+request.header("Accept-Encoding"));
            Response response = chain.proceed(request);
            //大于定义的返回码
            if(response != null && response.code() >= 210) {
                CustomException exception = new CustomException(CustomException.RE_LOGIN, "服务器连接超时");
                EventBus.getDefault().post(exception);
//                if(net_check == null) {
//                    throw exception;
//                }
            }
            if(response!=null&&response.body()!=null) {
                LogUtil.i(">>>>>response " + response.body().string());
            }

            return response;
        }
    };

    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }

        return false;
    }

    public static ApiService getApiService() {
        return getInstance().mApiService;
    }
}
