package base.brian.com.baseapp_mvp.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import java.util.List;

import base.brian.com.baseapp_mvp.ui.fragment.BaseFragment;

/**
 * 一个模块一个activity，多个fragment
 * Created by Administrator on 2016/5/30 0030.
 */
public class BaseFragmentActivity extends AppCompatActivity {
    FragmentManager fm;

    public int getContainerId() {
        return android.R.id.content;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();
        processRestoreInstanceState(savedInstanceState);
    }

    public void showFragment(Fragment to) {
        showFragment(to, 0);
    }

    public void showFragment(Fragment to, int requestCode) {
        String toName = to.getClass().getName();
        FragmentTransaction ft = fm.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(getContainerId(), to, toName);


        Fragment from = getTopFragment(fm);
        Bundle bundle = to.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            to.setArguments(bundle);
        }
        if (from != null) {
            ft.hide(from);
        } else {
            bundle.putBoolean(BaseFragment.IS_ROOT, true);
        }

        bundle.putInt(BaseFragment.ARG_REQUEST_CODE, requestCode);
        ft.addToBackStack(toName);
        ft.commit();
    }

    private BaseFragment getTopFragment(FragmentManager fragmentManager) {
        List<Fragment> fragmentList = fragmentManager.getFragments();
        if (fragmentList == null) return null;

        for (int i = fragmentList.size() - 1; i >= 0; i--) {
            Fragment fragment = fragmentList.get(i);
            if (fragment instanceof BaseFragment) {
                return (BaseFragment) fragment;
            }
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
//            fm.popBackStackImmediate();
            handleBack();
        } else {
            finish();
        }
    }

    /**  内存恢复fragment的显示状态  */
    private void processRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if (fragments != null && fragments.size() > 0) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                for (int i = fragments.size() - 1; i >= 0; i--) {
                    Fragment fragment = fragments.get(i);
                    if (fragment instanceof BaseFragment) {
                        BaseFragment baseFragment = (BaseFragment) fragment;
                        if (baseFragment.isSupportHidden()) {
                            ft.hide(baseFragment);
                        } else {
                            ft.show(baseFragment);
                        }
                    }
                }
                ft.commit();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void handleBack() {
        List<Fragment> fragmentList = fm.getFragments();
        int count = 0;
        int requestCode = 0, resultCode = 0;
        long lastAnimTime = 0;
        Bundle data = null;

        for (int i = fragmentList.size() - 1; i >= 0; i--) {
            Fragment fragment = fragmentList.get(i);
            if (fragment instanceof BaseFragment) {
                Bundle bundle = fragment.getArguments();
                final BaseFragment baseFragment = (BaseFragment) fragment;
                if (count == 0) {
                    requestCode = bundle.getInt(BaseFragment.ARG_REQUEST_CODE);
                    resultCode = bundle.getInt(BaseFragment.ARG_RESULT_CODE);
                    data = bundle.getBundle(BaseFragment.ARG_RESULT_BUNDLE);
                    lastAnimTime = baseFragment.getExitAnimDuration();
                    count++;
                } else {
                    if (requestCode != 0 && resultCode != 0) {
                        final int finalRequestCode = requestCode;
                        final int finalResultCode = resultCode;
                        final Bundle finalData = data;

                        long animTime = baseFragment.getPopEnterAnimDuration();

                        fm.popBackStackImmediate();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                baseFragment.onFragmentResult(finalRequestCode, finalResultCode, finalData);
                            }
                        }, Math.max(animTime, lastAnimTime));
                        return;
                    }
                    break;
                }
            }
        }
        fm.popBackStackImmediate();
    }
}
