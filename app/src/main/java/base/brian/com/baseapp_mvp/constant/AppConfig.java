package base.brian.com.baseapp_mvp.constant;

import android.os.Environment;

/**
 * Created by brian on 2016/6/5
 */
public interface AppConfig {
    boolean DEBUG = true;

    String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();
    //
    String APP_SDCARD_BASE_PATH = SDCARD_PATH + "/brian/";
    String APP_IMAGE_FILE = APP_SDCARD_BASE_PATH + "image/";
    String APP_IMAGE_FILE_CACHE = APP_IMAGE_FILE + "cache/";
    String APP_DOWNLOAD = APP_SDCARD_BASE_PATH + "download/";

    String IP="http://www.weather.com.cn/";

    int PAGE_SIZE = 10;  //一页10条数据

    //菜单栏
    int NO_MENU = -1;

    int CODE_OK = 1;
    int LOAD_SUCCESS = 1000;
    int LOAD_FAIL = 1001;
    int REFRESH = 1002;
    int LOADMORE = 1003;

}
