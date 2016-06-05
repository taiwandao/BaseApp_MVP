package base.brian.com.baseapp_mvp.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    int divHeight = 1;
    Paint mPaint;
    int padding_l_r;


    //左右间距
    public DividerItemDecoration(int colorRes, Context context, int dimenRes, int paddingRes) {
        this.divHeight = context.getResources().getDimensionPixelSize(dimenRes);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(context.getResources().getColor(colorRes));
        mPaint.setStyle(Paint.Style.FILL);
        if(paddingRes != 0) {
            padding_l_r = context.getResources().getDimensionPixelSize(paddingRes);
        }
    }

    public void setDivHeight(int divHeight) {
        this.divHeight = divHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, divHeight);
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//       drawHorizontal(c, parent);
        drawVertical(c,parent);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    /**
     * 绘制纵向 item 分割线
     */
    private void drawVertical(Canvas canvas,RecyclerView parent){
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount() ;
        for(int i = 0 ; i < childSize ; i ++){
            final View child = parent.getChildAt( i ) ;
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin ;
            final int bottom = top + divHeight;
            canvas.drawRect(left + padding_l_r,top,right - padding_l_r,bottom,mPaint);
        }
    }
}
