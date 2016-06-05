package base.brian.com.baseapp_mvp.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import base.brian.com.baseapp_mvp.R;
import base.brian.com.baseapp_mvp.util.ActivityUtils;


/**
 * Created by Administrator on 2016/4/27 0027.
 */
public class PageStateLayout extends FrameLayout {
    public enum PageState {
        LOADING, EMPTY, ERROR, SUCCEED, REQEUSTING
    }

    private OnClickListener errorClickListener;

    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;
    private View mSucceedView;

    public PageStateLayout(Context context) {
        super(context);
    }

    public PageStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setErrorClickListener(OnClickListener errorClickListener) {
        this.errorClickListener = errorClickListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        if(getChildCount() > 0) {
            mSucceedView = getChildAt(0);
        }
    }

    public void onLoading() {
        show(PageState.LOADING);
    }

    public void onError() {
        show(PageState.ERROR);
    }


    public void onEmpty() {
        show(PageState.EMPTY);
    }


    public void onSucceed() {
        show(PageState.SUCCEED);
    }

    public void show(PageState state) {
        if (null == mLoadingView) {
            mLoadingView = getLoadingView();
            addView(mLoadingView);
        }

        if (null == mErrorView) {
            mErrorView = getErrorView();
            addView(mErrorView);
        }

        if (null == mEmptyView) {
            mEmptyView = getEmptyView();
            addView(mEmptyView);
        }

        mLoadingView.setVisibility(state == PageState.LOADING || state == PageState.REQEUSTING ? VISIBLE : GONE);
        mErrorView.setVisibility(state == PageState.ERROR ? VISIBLE : GONE);
        mEmptyView.setVisibility(state == PageState.EMPTY ? VISIBLE : GONE);

        if (null != mSucceedView) {
            mSucceedView.setVisibility(state == PageState.SUCCEED || state == PageState.REQEUSTING ? VISIBLE : GONE);
        }
    }

    private View getEmptyView() {
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_empty, null, false);
        ImageView iv_empty = (ImageView) emptyView.findViewById(R.id.iv_empty);

        TextView tv_empty = (TextView) emptyView.findViewById(R.id.tv_empty);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        emptyView.setLayoutParams(params);
        return emptyView;
    }

    private View getErrorView() {
        View errorView = LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_error, null, false);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        errorView.setLayoutParams(params);

        if(errorClickListener != null) {
            errorView.findViewById(R.id.btn_refresh).setOnClickListener(errorClickListener);
        }
        return errorView;
    }

    private View getLoadingView() {
        View loadingView = LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_loading, null, false);
        LayoutParams params = new LayoutParams(ActivityUtils.dip2px(getContext(), 80), ActivityUtils.dip2px(getContext(), 80));
        params.gravity = Gravity.CENTER;
        loadingView.setLayoutParams(params);
        return loadingView;
    }

    public boolean isLoaded() {
        return  mSucceedView != null && mSucceedView.isShown();
    }

    public void load(@NonNull ViewGroup parent, @NonNull View succeedView) {
        if (null != mSucceedView) {
            removeView(mSucceedView);
        }
        mSucceedView = succeedView;
        addView(mSucceedView, 0);
        parent.addView(this);
    }

    public void load(@NonNull View succeedView) {
        if (null != mSucceedView) {
            removeView(mSucceedView);
        }
        mSucceedView = succeedView;
        addView(mSucceedView, 0);
    }
}
