package base.brian.com.baseapp_mvp.model.business;


import base.brian.com.baseapp_mvp.network.ApiService;
import base.brian.com.baseapp_mvp.network.RetrofitManager;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public class BaseService {
    public CompositeSubscription compositeSubscription;
    public ApiService apiService;

    public BaseService() {
        compositeSubscription = new CompositeSubscription();
        apiService = RetrofitManager.getApiService();
    }

    public void destroy() {
        compositeSubscription.unsubscribe();
    }
}
