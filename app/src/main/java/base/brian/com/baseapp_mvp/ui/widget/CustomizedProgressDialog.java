package base.brian.com.baseapp_mvp.ui.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import base.brian.com.baseapp_mvp.R;


/**
 * 
*  各个界面可以公用的进度框
* @author brian.hou
* 2014-10-9 上午10:55:15
* @version V1.0
 */
public class CustomizedProgressDialog extends ProgressDialog {
	/** 提示文字 **/
	private TextView dialog_progress_text;

	/**
	 * 用户设置的文本信息
	 */
	private CharSequence mMessage;

	public CustomizedProgressDialog(Context context) {
		this(context, R.style.dialog_progress);
	}

	public CustomizedProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_progress);
		dialog_progress_text = (TextView) this.findViewById(R.id.dialog_progress_text);
		
		mMessage = this.getContext().getText(R.string.wait);

	}

	@Override
	public void onStart() {
		super.onStart();
		dialog_progress_text.setText(mMessage);
	}
	
	@Override
	public void setMessage(CharSequence message) {
		mMessage = message;
	}
}
