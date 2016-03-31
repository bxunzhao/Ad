package com.lajiaolc.admodule.chinazmob;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lajiaolc.admodule.R;
import com.zy.phone.SDKInit;
import com.zy.phone.net.Integral;

/**
 * @author Administrator
 */
public class ChinazMobActiyity extends Activity implements OnClickListener, Integral {
    // 获取广告列表
    private Button getAdlist;
    // 查看积分
    private Button check_integral;
    // 扣除积分
    private Button minus_integral;
    // 增加积分
    private Button add_integral;
    // 显示积分
    private TextView show_integral;
    // 添加积分
    private EditText add_textintegral;
    // 扣除积分
    private EditText minus_textintgral;
    // 秘钥
    private String AdpCode = "e9ff112bc855bf4c";
    //private String AdpCode = "4e368780d0c76f31";

    // 用户ID，用于记录开发者应用的唯一用户标识,没有为空
    private String Other = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chinazmob_activity);
        initView();
    }

    /**
     * 初始化控件和数据信息
     */
    private void initView() {

        getAdlist = (Button) findViewById(R.id.getAdlist);
        check_integral = (Button) findViewById(R.id.check_integral);
        show_integral = (TextView) findViewById(R.id.show_integral);
        minus_integral = (Button) findViewById(R.id.minus_integral);
        add_integral = (Button) findViewById(R.id.add_integral);
        add_textintegral = (EditText) findViewById(R.id.add_textintegral);
        minus_textintgral = (EditText) findViewById(R.id.minus_textintgral);
        // 设置单击事件
        getAdlist.setOnClickListener(this);
        check_integral.setOnClickListener(this);
        minus_integral.setOnClickListener(this);
        add_integral.setOnClickListener(this);
        /*
		 * 初始化信息AdpCode 是开发者的秘钥Other 服务器回调的时候一起返回.
		 */
        SDKInit.initAd(this, AdpCode, Other);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.getAdlist) {
            // 初始化列表
            SDKInit.initAdList(this);
        }
        // 查看积分
        if (v.getId() == R.id.check_integral) {
            SDKInit.checkIntegral(this);
        }
        // 扣除积分
        if (v.getId() == R.id.minus_integral) {
            SDKInit.minusIntegral(this, getIntgral(minus_textintgral));
        }
        // 增加积分
        if (v.getId() == R.id.add_integral) {
            SDKInit.addIntegral(this, getIntgral(add_textintegral));
        }
    }

    /**
     * 获取积分
     *
     * @param edit
     * @return
     */
    public String getIntgral(EditText edit) {
        return edit.getText().toString();
    }

    /********************************** 所有回调方法，主线程执行 **********************************/
    /**
     * 查看积分 retcode 0：成功，1：失败，2：积分不够扣除 返回积分
     */
    @Override
    public void retCheckIntegral(String retcode, String integral) {
        if (retcode.equals("0")) {
            show_integral.setText("您现在的积分为：" + integral);
        } else if (retcode.equals("1")) {
            show_integral.setText("查看积分失败");
        } else if (retcode.equals("2")) {
            show_integral.setText("积分不够扣除");
        }

    }

    /**
     * 扣除
     * 查看积分 retcode 0：成功，1：失败，2：积分不够扣除 返回积分
     */
    @Override
    public void retMinusIntegral(String retcode, String integral) {
        if (retcode.equals("0")) {
            show_integral.setText("您现在的积分为：" + integral);
        } else if (retcode.equals("1")) {
            show_integral.setText("查看积分失败");
        } else if (retcode.equals("2")) {
            show_integral.setText("积分不够扣除");
        }
    }

    /**
     * 增加
     * 查看积分 retcode 0：成功，1：失败，2：积分不够扣除 返回积分
     */
    @Override
    public void retAddIntegral(String retcode, String integral) {
        if (retcode.equals("0")) {
            show_integral.setText("您现在的积分为：" + integral);
        } else if (retcode.equals("1")) {
            show_integral.setText("查看积分失败");
        } else if (retcode.equals("2")) {
            show_integral.setText("积分不够扣除");
        }
    }

}
