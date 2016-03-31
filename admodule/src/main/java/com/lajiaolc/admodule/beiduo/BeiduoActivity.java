package com.lajiaolc.admodule.beiduo;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bb.dd.BeiduoPlatform;
import com.bb.dd.listener.IActiveListener;
import com.bb.dd.listener.IAddListener;
import com.bb.dd.listener.IGetListener;
import com.bb.dd.listener.IReduceListener;
import com.lajiaolc.admodule.R;

public class BeiduoActivity extends Activity implements OnClickListener {


    Button show_btn0;
    Button show_btn1;
    Button add_btn;
    Button minus_btn;
    Button query_btn;


    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        context = this;

        show_btn0 = (Button) findViewById(R.id.show_btn);
        query_btn = (Button) findViewById(R.id.query_btn);
        add_btn = (Button) findViewById(R.id.add_btn);
        minus_btn = (Button) findViewById(R.id.minus_btn);
        show_btn1 = (Button) findViewById(R.id.fb_btn);

        show_btn0.setOnClickListener(this);
        show_btn1.setOnClickListener(this);
        query_btn.setOnClickListener(this);
        add_btn.setOnClickListener(this);
        minus_btn.setOnClickListener(this);


        BeiduoPlatform.setAppId(this, "10003", "");
        BeiduoPlatform.setUserId("6666");

        TextView tv = (TextView) findViewById(R.id.version);
        tv.setText("  版本号：v" + BeiduoPlatform.versionName());


        //首次安装激活成功后的回调
        BeiduoPlatform.setActiveAppListener(new IActiveListener() {

            @Override
            public void activeSuccess(int arg0, int arg1, String arg2) {
                // TODO Auto-generated method stub
                if (arg0 == 0) {//安装激活通知

                    Log.i("首次安装成功", "赠送了" + arg1 + "金币" + "; 广告包名称为：" + arg2);

                } else if (arg0 > 0) {//签到的通知

                    Log.i("第" + arg0 + "次签到成功", "赠送了" + arg1 + "金币" + "; 广告包名称为：" + arg2);

                }
            }

            @Override
            public void activeFailed(int arg0, int arg1, String arg2) {
                // TODO Auto-generated method stub
                Log.i("激活失败", "失败信息：" + arg2);
            }
        });


    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == add_btn) {

            BeiduoPlatform.addMoney(100, new IAddListener() {

                @Override
                public void addSuccess(int totalMoney) {
                    // TODO Auto-generated method stub
                    Toast.makeText(context, "增加成功" + " 当前的余额:" + totalMoney, 0).show();
                }

                @Override
                public void addFailed(int errorCode) {
                    // TODO Auto-generated method stub
                    Toast.makeText(context, "增加失败", 0).show();
                }
            });


        } else if (v == minus_btn) {

            BeiduoPlatform.reduceMoney(100, new IReduceListener() {

                @Override
                public void reduceSuccess(int totalMoney) {
                    // TODO Auto-generated method stub
                    Toast.makeText(context, "减少成功" + " 当前的余额:" + totalMoney, 0).show();
                }

                @Override
                public void reduceFailed(int errorCode) {
                    // TODO Auto-generated method stub
                    Toast.makeText(context, "减少失败,错误代码" + errorCode, 0).show();
                }


            });


        } else if (v == query_btn) {

            BeiduoPlatform.getMoney(new IGetListener() {

                @Override
                public void getSuccess(int totalMoney, String unitName) {
                    // TODO Auto-generated method stub
                    Toast.makeText(context, "获取成功，当前的" + unitName + "为:" + totalMoney, 0).show();
                }

                @Override
                public void getFailed(int error) {
                    // TODO Auto-generated method stub
                    Toast.makeText(context, "获取失败" + "错误码:" + error, 0).show();
                }
            });

        } else if (v == show_btn0) {

            BeiduoPlatform.showOfferWall(this);//有积分的积分墙

        } else if (v == show_btn1) {

            BeiduoPlatform.showMoreApps(this);//无积分的推荐墙

        }

    }


}
