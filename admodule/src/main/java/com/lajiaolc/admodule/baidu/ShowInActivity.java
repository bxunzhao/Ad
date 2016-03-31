package com.lajiaolc.admodule.baidu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.baidu.mobads.appoffers.OffersView;

public class ShowInActivity extends Activity {

    boolean isShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("test", "ShowInActivity.onCreate");
        final RelativeLayout rlMain = new RelativeLayout(this);
        setContentView(rlMain);
        Button btn = new Button(this);
        btn.setId(100);
        btn.setText("show wall in activity");
        rlMain.addView(btn);

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!isShow) {
                    isShow = true;
                    boolean showTitleBar = false;//如果为true，则显示页面顶端的bar；为false则不显示。
                    OffersView ov = new OffersView(ShowInActivity.this, showTitleBar);
                    RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(-1, -1);
                    rllp.addRule(RelativeLayout.BELOW, 100);
                    rlMain.addView(ov, rllp);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "ShowInActivity.onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test", "ShowInActivity.onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("test", "ShowInActivity.onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("test", "ShowInActivity.onResume");
    }

}
