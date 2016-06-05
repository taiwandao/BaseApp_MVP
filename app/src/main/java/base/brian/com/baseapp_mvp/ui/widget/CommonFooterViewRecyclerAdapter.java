package base.brian.com.baseapp_mvp.ui.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import base.brian.com.baseapp_mvp.R;


/**
 * footer布局adapter
 * Created by cannocan on 2015/11/30.
 */
public class CommonFooterViewRecyclerAdapter extends HeaderViewRecyclerAdapter implements FooterViewListener {

    private TextView tvLoad;
    private ProgressBar progressBarLoad;
    private View loadMoreView;

    public CommonFooterViewRecyclerAdapter(RecyclerView.Adapter adapter, ViewGroup parent) {
        super(adapter);
        loadMoreView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_foorerview, parent, false);
        tvLoad = (TextView) loadMoreView.findViewById(R.id.load_text);
        progressBarLoad = (ProgressBar) loadMoreView.findViewById(R.id.load_progress_bar);
    }

    @Override
    public void onLoadMoreSuccess() {
        addLoadMoreFooterView();
        progressBarLoad.setVisibility(View.GONE);
        tvLoad.setText("查看更多");
    }

    @Override
    public void onLoadMoreFailed() {
        progressBarLoad.setVisibility(View.GONE);
        tvLoad.setText("加载失败点击重试");
    }

    @Override
    public void onLoadMoreComplete() {
//        progressBarLoad.setVisibility(View.GONE);
//        tvLoad.setText("暂无更多");
        removeFooterView();
    }

    @Override
    public void onLoadMoreRefreshing() {
        progressBarLoad.setVisibility(View.VISIBLE);
        tvLoad.setText("正在加载中...");
    }

    public void setFooterClickListener(View.OnClickListener listener) {
        loadMoreView.setOnClickListener(listener);
    }

    public void addLoadMoreFooterView() {
        addFooterView(loadMoreView);
    }

    public boolean isFooter(int position) {
        int lastPosition = getItemCount() - 1;
        return getFooterView() != null && position == lastPosition;
    }

    public boolean isHeader(int position) {
        return getHeaderCount() > 0 && position == 0;
    }
}
