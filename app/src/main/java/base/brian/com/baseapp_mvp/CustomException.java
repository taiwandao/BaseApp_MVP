package base.brian.com.baseapp_mvp;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
public class CustomException extends RuntimeException {
    public static final int NO_NET = -200;
    //重新登录
    public static final int RE_LOGIN = 900;

    public int code;
    public String message;

    public CustomException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CustomException(String message) {
        this.message = message;
    }
}
