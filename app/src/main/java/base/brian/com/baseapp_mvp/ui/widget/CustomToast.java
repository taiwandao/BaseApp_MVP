package base.brian.com.baseapp_mvp.ui.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import base.brian.com.baseapp_mvp.R;


/**
 * Created by Administrator on 2016/3/18 0018.
 */
public class CustomToast extends Toast {
    TextView tv_text;
    View view;

    public CustomToast(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_toast, null);
        tv_text = (TextView) view.findViewById(R.id.view_toast_textview);
        setView(view);
    }

   public void makeToast(CharSequence text, int duration) {
       tv_text.setText(text);
       if(getView().getWindowVisibility() != View.VISIBLE) {
           setGravity(Gravity.CENTER, 0, 0);
           setDuration(duration);
           show();
       }
   }
}
