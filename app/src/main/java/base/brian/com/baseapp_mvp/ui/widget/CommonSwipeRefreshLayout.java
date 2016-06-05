package base.brian.com.baseapp_mvp.ui.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import base.brian.com.baseapp_mvp.R;


/**
 * 集成下拉刷新和RecyclerView
 * Created by yefu on 2015/11/30.
 */
public class CommonSwipeRefreshLayout extends SwipeRefreshLayout implements FooterViewListener, View.OnClickListener {

    RecyclerView recyclerView;
    CommonFooterViewRecyclerAdapter adapter;
    OnLoadMoreListener onLoadMoreListener;
    boolean isAddFooter=true;
    public CommonSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public CommonSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void onLoadMoreSuccess() {
        adapter.onLoadMoreSuccess();
        adapter.setFooterClickListener(this);
    }

    @Override
    public void onLoadMoreFailed() {
        adapter.onLoadMoreFailed();
        adapter.setFooterClickListener(this);
    }

    @Override
    public void onLoadMoreComplete() {
        adapter.onLoadMoreComplete();
        adapter.setFooterClickListener(this);
    }

    @Override
    public void onLoadMoreRefreshing() {
        adapter.onLoadMoreRefreshing();
        adapter.setFooterClickListener(null);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View v) {
        loadMore();
    }

    public void loadMore() {
        onLoadMoreRefreshing();
        if (onLoadMoreListener != null) {
            onLoadMoreListener.onLoadMore();
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.onLoadMoreListener = listener;
    }

    public void setAdapter(CommonFooterViewRecyclerAdapter adapter) {
        this.adapter = adapter;
        recyclerView.setAdapter(adapter);
        onLoadMoreSuccess();
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void addItemDecoration(RecyclerView.ItemDecoration direction) {
        recyclerView.addItemDecoration(direction);
    }

    public void setAddFooter(boolean addFooter) {
        isAddFooter = addFooter;
    }

    public void setClipToPadding(int padding) {
        recyclerView.setClipToPadding(false);
        recyclerView.setPadding(0, padding, 0, 0);
    }
}

