package com.lajiaolc.admodule.yibiaopan;

import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lajiaolc.admodule.R;
import com.lajiaolc.admodule.yibiaopan.utils.UI;
import com.ncc.sdk.offerwall.NccOfferWallAPI;
import com.ncc.sdk.offerwall.NccOfferWallListener;
import com.ncc.sdk.offerwall.entity.Point;


public class YBPMainActivity extends Activity implements OnClickListener {

    static final String TAG = YBPMainActivity.class.getSimpleName();

    TextView mResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ybp);
        findViewById(R.id.open).setOnClickListener(this);
        findViewById(R.id.close).setOnClickListener(this);
        findViewById(R.id.query).setOnClickListener(this);
        findViewById(R.id.use).setOnClickListener(this);
        mResultView = UI.<TextView>findViewById(this, R.id.result);
        NccOfferWallAPI.setPlatformId("13872c5a3cfafb1e639b619c3a25c7ab");
        NccOfferWallAPI.init(this);
        NccOfferWallAPI.setOnCloseListener(new NccOfferWallListener<Void>() {
            @Override
            public void onSucceed(Void result) {

                mResultView.setText("应用墙关闭了！");
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
            }
        });
        NccOfferWallAPI
                .setOnActivatedListener(new NccOfferWallListener<Point>() {
                    @Override
                    public void onSucceed(Point result) {
                        Toast.makeText(
                                YBPMainActivity.this,
                                "应用激活了：balance=" + result.balance + " total="
                                        + result.total + " used=" + result.used,
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(int errorCode, String errorMsg) {
                    }
                });
    }

    @Override
    protected void onDestroy() {
        NccOfferWallAPI.destroy(this);
        super.onDestroy();
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public void onClick(View view) {
//		switch (view.getId()) {
        if (view.getId() == R.id.open) {
            NccOfferWallAPI.open(this, new NccOfferWallListener<Void>() {
                @Override
                public void onSucceed(Void result) {
                    mResultView.setText("打开应用墙成功！");
                }

                @Override
                public void onError(int errorCode, String errorMsg) {
                    mResultView.setText("打开应用墙失败 --> " + errorCode + " --> "
                            + errorMsg);
                }
            });
        }
//			break;
//		case R.id.close:
//			NccOfferWallAPI.close(this);
//			break;
//		case R.id.query:
//			NccOfferWallAPI.getPoint(new NccOfferWallListener<Point>() {
//
//				@Override
//				public void onSucceed(Point result) {
//					mResultView.setText("获取成功了！\n 余额：" + result.balance
//							+ "\n 总共获得积分：" + result.total + "\n 总共使用积分："
//							+ result.used);
//				}
//
//				@Override
//				public void onError(int errorCode, String errorMsg) {
//					mResultView.setText("获取积分错误啦！ --> " + errorCode + " --> "
//							+ errorMsg);
//				}
//			});
//			break;
//		case R.id.use:
//			NccOfferWallAPI.usePoint(10, "我就是用了，咋滴！",
//					new NccOfferWallListener<Point>() {
//						@Override
//						public void onSucceed(Point result) {
//							mResultView.setText("获取成功了！\n 余额：" + result.balance
//									+ "\n 总共获得积分：" + result.total
//									+ "\n 总共使用积分：" + result.used);
//						}
//
//						@Override
//						public void onError(int errorCode, String errorMsg) {
//							mResultView.setText("使用积分错误啦！ --> " + errorCode
//									+ " --> " + errorMsg);
//						}
//					});
//			break;
//		default:
//			break;
//		}
        }

    }
