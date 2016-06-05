package base.brian.com.baseapp_mvp.util;

import android.content.Context;
import android.content.Intent;

import base.brian.com.baseapp_mvp.CustomException;
import base.brian.com.baseapp_mvp.R;
import base.brian.com.baseapp_mvp.event.BaseEvent;


public class UIHelper {


    public static String getErrorMsg(Context context, BaseEvent event) {
        String errorMsg = context.getString(R.string.error_network);
        Throwable throwable = event.throwable;
        if(throwable != null && throwable instanceof CustomException) {
            String message = ((CustomException) throwable).message;
            if(message != null) {
                errorMsg = message;
            }
        }
        return errorMsg;
    }
}
