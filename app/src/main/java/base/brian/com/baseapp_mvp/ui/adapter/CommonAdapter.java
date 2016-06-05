package base.brian.com.baseapp_mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import static base.brian.com.baseapp_mvp.ui.adapter.BaseAdapterHelper.get;


/**
 * Created by Administrator on 2016/3/24 0024.
 */
public abstract class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.RecyclerViewHolder> {
    public abstract int getLayoutId();
    public abstract void onBindView(BaseAdapterHelper helper, int position);

    public interface OnItemClickListener{
        void onItemClick(RecyclerView.ViewHolder viewHolder, View view, int position);
    }
    public interface OnItemLongClickListener{
        void onItemLongClick(RecyclerView.ViewHolder viewHolder, View view, int position);
    }

    public OnItemClickListener onItemClickListener;
    public OnItemLongClickListener onItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BaseAdapterHelper helper = get(parent.getContext(), null, parent, getLayoutId(), -1);
        return new RecyclerViewHolder(helper.getView(),helper);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        BaseAdapterHelper helper = holder.adapterHelper;
        onBindView(helper, position);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        BaseAdapterHelper adapterHelper;

        public RecyclerViewHolder(View itemView, BaseAdapterHelper adapterHelper) {
            super(itemView);
            this.adapterHelper = adapterHelper;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if(null != onItemClickListener){
                        onItemClickListener.onItemClick(RecyclerViewHolder.this,v,getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override public boolean onLongClick(View v) {
                    if(null != onItemLongClickListener){
                        onItemLongClickListener.onItemLongClick(RecyclerViewHolder.this,v,getAdapterPosition());
                        return true;
                    }
                    return false;
                }
            });
        }
    }
}
