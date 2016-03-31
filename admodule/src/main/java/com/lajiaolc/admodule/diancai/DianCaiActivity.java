package com.lajiaolc.admodule.diancai;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dc.wall.DianCai;
import com.dc.wall.IAddMoneyNotifier;
import com.dc.wall.IEarnMoneyNotifier;
import com.dc.wall.IQueryMoneyNotifier;
import com.dc.wall.IReduceMoneyNotifier;
import com.lajiaolc.admodule.R;


public class DianCaiActivity extends Activity implements OnClickListener{
	
	
	

	Button add_btn;
	Button reduce_btn;
	Button query_btn;
	Button offerWall_btn;
	Button recommendWall_btn;
	
	Context  context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diancai_main);
		context = this;		  
		TextView tv = (TextView) findViewById(R.id.version);
		tv.setText("DianCai SDK 版本号：v"+DianCai.versionName());
		
		query_btn          = (Button) findViewById(R.id.query_btn);
		add_btn            = (Button) findViewById(R.id.add_btn);
		reduce_btn         = (Button) findViewById(R.id.reduce_btn);
		offerWall_btn      = (Button) findViewById(R.id.withScore_btn);	
		recommendWall_btn  = (Button) findViewById(R.id.withoutScore_btn);

		query_btn.setOnClickListener(this);
		add_btn.setOnClickListener(this);
		reduce_btn.setOnClickListener(this);
		offerWall_btn.setOnClickListener(this);
		recommendWall_btn.setOnClickListener(this);

		
		DianCai.initApp(this, "168", "666666888888");

	

		
		//激活APP或签到成功后的，回调奖励的通知。
		DianCai.setEarnMoneyListener(new IEarnMoneyNotifier() {
			
			@Override
			public void earnSuccess(int arg0, int arg1, String arg2) {
				// TODO Auto-generated method stub
				//参数介绍
				//arg0: 等于0 表示首次激活App得到的奖励；若大于0 表示 第arg0次签到赚取的奖励；
				//arg1: 赚取的虚拟货币数量；
			    //arg2: 广告的包名称；
				
				if(arg0==0){//来自安装激活通知
					   
					Log.i("广告激活成功", "赚取了"+arg1+"虚拟货币;");
				   
				}else if(arg0>0){//来自签到的通知
				  
					Log.i("第"+arg0+"次签到成功", "赚取了"+arg1+"虚拟货币;");
					
				}
			}
			
			@Override
			public void earnFailed(int arg0, int arg1, String arg2) {
				// TODO Auto-generated method stub
				//参数介绍
				//arg0: 等于0 表示首次激活App得到的奖励；若大于0 表示 第arg0次签到赚取的奖励；
				//arg1: 赚取的虚拟货币数量；
			    //arg2: 失败的错误信息；
				
				Log.i("广告激活(签到)奖励失败",arg2);
				
			}
		});
		
	}

	

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
        if(v==add_btn){
			
			DianCai.addMoney(100, new  IAddMoneyNotifier() {
				
				@Override
				public void addSuccess(int totalMoney) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "增加成功"+" 当前的余额:"+totalMoney, Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void addFailed(int errorCode) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "增加失败", Toast.LENGTH_SHORT).show();
				}
			});
			
			
		}else if(v==reduce_btn){
			
			DianCai.reduceMoney(100, new  IReduceMoneyNotifier() {

				@Override
				public void reduceSuccess(int totalMoney) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "扣除成功"+" 当前的余额:"+totalMoney, Toast.LENGTH_SHORT).show();
				}

				@Override
				public void reduceFailed(int errorCode) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "扣除失败,错误代码"+errorCode, Toast.LENGTH_SHORT).show();
				}
				

			});
			
			
		}else if(v==query_btn){
			
			DianCai.queryMoney(new IQueryMoneyNotifier() {

				@Override
				public void querySuccess(int totalMoney, String currencyUnit) {
					// TODO Auto-generated method stub
					//参数说明
					//totalMoney 虚拟货币数量
					//虚拟货币单位 （如：金币 等）
					
					Toast.makeText(context, "查询成功，当前的余额："+totalMoney + currencyUnit, Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void queryFailed(int error) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "查询失败"+"错误代码:"+error, Toast.LENGTH_SHORT).show();
				}
			});
			
		}else if(v==offerWall_btn){
			
	            DianCai.showOfferWall();//积分墙

	    }else if(v==recommendWall_btn){
			
			    DianCai.showGoodApps();//精品推荐列表
	 
		}
			
	}
	
	

}
