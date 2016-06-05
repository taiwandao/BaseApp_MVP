package base.brian.com.baseapp_mvp.ui.activity;

import android.os.Bundle;

import base.brian.com.baseapp_mvp.ui.fragment.MainFragment;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            showFragment(MainFragment.newInstance());
        }
    }
}
