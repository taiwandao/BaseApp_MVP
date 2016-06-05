//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package base.brian.com.baseapp_mvp.util;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;

import base.brian.com.baseapp_mvp.ui.widget.CustomToast;

public class ErrorUtil {
    public static CustomToast toast;

    public ErrorUtil() {
    }

    public static void makeToast(Context context, int resId, int duration) {
        makeToast(context, context.getResources().getText(resId), duration);
    }

    public static void makeToast(Context context, int resId) {
        makeToast(context, context.getResources().getText(resId), 0);
    }

    public static void makeToast(Context context, CharSequence text) {
        makeToast(context, text, 0);
    }

    public static void makeToast(Context context, CharSequence text, int duration) {
        if(text != null) {
            if(toast == null) {
                toast = new CustomToast(context);
            }

            toast.makeToast(text, duration);
        }
    }

}
