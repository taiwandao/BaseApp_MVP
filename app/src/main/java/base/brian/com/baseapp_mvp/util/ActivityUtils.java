//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package base.brian.com.baseapp_mvp.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import java.util.List;

public class ActivityUtils {
    public ActivityUtils() {
    }

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
    }

    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5F);
    }

    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / fontScale + 0.5F);
    }

    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5F);
    }

    public static void hiddenWindowSoft(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if(view != null) {
            InputMethodManager inputmanger = (InputMethodManager)activity.getSystemService("input_method");
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public static int getScreenWidth(Activity mActivity) {
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenHeight(Activity mActivity) {
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static boolean isAppAlive(String packageName, Context context) {
        if(packageName != null && !"".equals(packageName)) {
            try {
                ApplicationInfo e = context.getPackageManager().getApplicationInfo(packageName, 8192);
                return true;
            } catch (NameNotFoundException var3) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isAvilible(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        List pinfo = packageManager.getInstalledPackages(0);

        for(int i = 0; i < pinfo.size(); ++i) {
            if(((PackageInfo)pinfo.get(i)).packageName.equalsIgnoreCase(packageName)) {
                return true;
            }
        }

        return false;
    }

    @TargetApi(11)
    public static void copy(String content, Context context) {
        ClipboardManager cmb = (ClipboardManager)context.getSystemService("clipboard");
        cmb.setText(content.trim());
    }

    public static <T> T checkNotNull(T reference) {
        if(reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }
}
