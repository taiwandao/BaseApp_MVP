package base.brian.com.baseapp_mvp.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import base.brian.com.baseapp_mvp.R;
import base.brian.com.baseapp_mvp.constant.AppConfig;
import base.brian.com.baseapp_mvp.event.BaseEvent;
import base.brian.com.baseapp_mvp.ui.activity.BaseFragmentActivity;
import base.brian.com.baseapp_mvp.ui.presenter.BasePresenter;
import base.brian.com.baseapp_mvp.ui.widget.CustomizedProgressDialog;
import base.brian.com.baseapp_mvp.ui.widget.PageStateLayout;
import base.brian.com.baseapp_mvp.util.ErrorUtil;
import base.brian.com.baseapp_mvp.util.UIHelper;

/**
 * Created by Administrator on 2016/5/30 0030.
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements Toolbar.OnMenuItemClickListener {
    public abstract int getLayoutId();
    public abstract String getTitleString();
    public abstract void initData();
    public abstract P initPresenter();

    public static final String IS_ROOT = "mIsRoot";
    public static final String FRAGMENTATION_STATE_SAVE_IS_HIDDEN = "is_hidden";
    public static final String ARG_REQUEST_CODE = "request_code";
    public static final String ARG_RESULT_CODE = "result_code";
    public static final String ARG_RESULT_BUNDLE = "bundle";

    private boolean mIsRoot;  //栈底
    private boolean mIsHidden = true;   // 用于记录Fragment show/hide 状态
    private Animation mNoAnim, mEnterAnim, mExitAnim, mPopEnterAnim, mPopExitAnim;

    boolean hasBack = true; //返回按钮
    boolean register = false;  //是否注册EventBus

    public View rootView;
//    @ViewInject(R.id.mLayout)
    public PageStateLayout mLayout;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.tv_center_title)
    private TextView tv_center_title;

    public P presenter;

    ProgressDialog progressDialog;

    View.OnClickListener againClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            initData();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);  //设置可以缓存
        setHasOptionsMenu(true);  //设置有菜单
        Bundle bundle = getArguments();
        if (bundle != null) {
            mIsRoot = bundle.getBoolean(IS_ROOT, false);
        }
        if(savedInstanceState != null) {
            mIsHidden = savedInstanceState.getBoolean(FRAGMENTATION_STATE_SAVE_IS_HIDDEN);
        }

        hasBack = hasBack();
        register = hasRegister();
        initAnim();

        if (!EventBus.getDefault().isRegistered(this) && register) {
            EventBus.getDefault().register(this);
        }

        presenter = initPresenter();
    }

    private void initAnim() {
        mNoAnim = AnimationUtils.loadAnimation(getContext(), R.anim.no_anim);
        mEnterAnim = AnimationUtils.loadAnimation(getContext(), R.anim.h_fragment_enter);
        mExitAnim = AnimationUtils.loadAnimation(getContext(), R.anim.h_fragment_exit);
        mPopEnterAnim = AnimationUtils.loadAnimation(getContext(), R.anim.h_fragment_pop_enter);
        mPopExitAnim = AnimationUtils.loadAnimation(getContext(), R.anim.h_fragment_pop_exit);

        mEnterAnim.setAnimationListener(new CustomAnimListener(true));
        mPopEnterAnim.setAnimationListener(new CustomAnimListener(false));
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (transit == FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {
            if (enter) {
                if (mIsRoot) {
                    return mNoAnim;
                }
                return mEnterAnim;
            } else {
                return mPopExitAnim;
            }
        } else if (transit == FragmentTransaction.TRANSIT_FRAGMENT_CLOSE) {
            if (enter) {
                return mPopEnterAnim;
            } else {
                return mExitAnim;
            }
        }
        return null;
    }

    public long getExitAnimDuration() {
        return mExitAnim.getDuration();
    }
    public long getPopEnterAnimDuration() {
        return mPopEnterAnim.getDuration();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView != null) return rootView;
        if(getLayoutId() <= 0) throw new IllegalArgumentException("布局id不正确");
        rootView = inflater.inflate(getLayoutId(), container, false);
        x.view().inject(this,rootView);
        mLayout = (PageStateLayout) rootView.findViewById(R.id.mLayout);
        if(mLayout != null) {
            mLayout.setErrorClickListener(againClick);
        }
        initToolBar();
        onBindView();
        if(mIsRoot) {
            onEnterAnimationEnd();
        }
        return rootView;
    }

    protected void initToolBar() {
//        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
//        tv_center_title = (TextView) rootView.findViewById(R.id.tv_center_title);

        initMenu();

        if(tv_center_title != null) {
            String title = getTitleString();
            if(title != null) {
                tv_center_title.setText(title);
            }
        }
    }

    public void initMenu() {
        if(toolbar != null) {
            toolbar.getMenu().clear();
            toolbar.setNavigationIcon(R.drawable.btn_back_default);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    back();
                }
            });

            if(getMenuId() != AppConfig.NO_MENU) {
                toolbar.inflateMenu(getMenuId());
            }
            toolbar.setOnMenuItemClickListener(this);
        }
    }

    public boolean isSupportHidden() {
        return mIsHidden;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        initFragmentBackground(view);  //动画背景透明的问题

    }

    protected void initFragmentBackground(View view) {
        if (view != null && view.getBackground() == null) {
            int background = getWindowBackground();
            view.setBackgroundResource(background);
        }
    }

    protected int getWindowBackground() {
        TypedArray a = getActivity().getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowBackground});
        int background = a.getResourceId(0, 0);
        a.recycle();
        return background;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FRAGMENTATION_STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            hideKeyboard();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void hideKeyboard() {
        Activity activity = getActivity();
        if(activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                back();
                break;
//            case R.id.action_commit:
//                commit();
//                break;
        }
        return false;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        if(presenter != null) {
            presenter.onDestroy();
        }
    }

    public void onError(BaseEvent event, boolean paging) {
        closeProgressDialog();

        String errorMsg = UIHelper.getErrorMsg(getContext(), event);
        if(mLayout == null) {
            ErrorUtil.makeToast(getActivity(), errorMsg);
        } else {
            if(mLayout.isLoaded()) {  //当前页面已经加载成功过
                if(paging) {
                    pageError(errorMsg);
                } else {
                    ErrorUtil.makeToast(getActivity(), errorMsg);
                }
            } else {  //初始化就加载失败了
                mLayout.onError();
            }
        }
    }

    private class CustomAnimListener implements Animation.AnimationListener {
        boolean isEnterAnim;

        public CustomAnimListener(boolean isEnterAnim) {
            this.isEnterAnim = isEnterAnim;
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (isEnterAnim) {
                onEnterAnimationEnd();
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }

    /**  进入动画结束，复杂布局可以在动画结束后初始化布局 防止卡顿  */
    private void onEnterAnimationEnd() {
        initData();
    }

    /**  初始化toolbar,返回是否有返回按钮  */
    public boolean hasBack() {
        return true;
    };

    //绑定view
    public void onBindView() {}

    //toolbar提交按钮点击
    public void commit() {}

    //返回是否有menu菜单
    public int getMenuId() {
        return AppConfig.NO_MENU;
    }

    public boolean hasRegister() {
        return false;
    };

    //有分页的网络错误
    public void pageError(String errorMsg) {}


    public void showProgressDialog() {
        showProgressDialog(true);
    }

    public void showProgressDialog(boolean cancelable) {
        if (progressDialog == null) {
            progressDialog = new CustomizedProgressDialog(getActivity());
            progressDialog.setCancelable(cancelable);
        }
        progressDialog.show();
    }

    public void showProgressDialog(boolean cancelable, String txt) {
        if (progressDialog == null) {
            progressDialog = new CustomizedProgressDialog(getActivity());
            progressDialog.setCancelable(cancelable);
            progressDialog.setMessage(txt);
        }
        progressDialog.show();
    }

    public void closeProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    public void setFragmentResult(int resultCode, Bundle bundle) {
        Bundle args = getArguments();
        if (args == null) {
            args = new Bundle();
        }
        args.putInt(ARG_RESULT_CODE, resultCode);
        args.putBundle(ARG_RESULT_BUNDLE, bundle);
    }

    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {}

    public void toFragment(Fragment to) {
        toFragment(to, 0);
    }

    public void toFragment(Fragment to, int requestCode) {
        Activity activity = getActivity();
        if(activity != null && activity instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity)activity).showFragment(to, requestCode);
        }
    }

    public void back() {
        getActivity().onBackPressed();
    }
}
