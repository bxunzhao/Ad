package com.lajiaolc.admodule.miidi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.midi.wall.sdk.AdDesc;
import net.midi.wall.sdk.AdDescItem;
import net.midi.wall.sdk.AdWall;
import net.midi.wall.sdk.IAdWallRequestAdSourceSigninNotifier;

import java.util.List;

public class AdListActivity extends Activity implements IAdWallRequestAdSourceSigninNotifier/* IAdWallRequestAdSourceNotifier*/{
	
	List<AdDesc> addesclist = null;
	List<AdDesc> signinList = null;
	
	boolean is_signin =  false;
	private View signinView;
	private View listView;
	private ProgressDialog mpd = null;
	
	private void showDialog(){
		mpd = ProgressDialog.show(this, "", "加载中。。。。");
		mpd.setCanceledOnTouchOutside(true);
//		mpd.show();
	}
	
	private void dismiss(){
		if(mpd !=null && mpd.isShowing()){
			mpd.dismiss();
		}
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		showDialog();
		// 异步方式
//		AdWall.requestAdSourceList(this);
		AdWall.requestAdSourceSigninList(AdListActivity.this);
		
	}

	/**
     * 新建适配后的ListView
     * @return ListView实例
     */
    private View createListView(){
    	ListView listView = new ListView(this);
    	listView.setCacheColorHint(Color.TRANSPARENT);
    	
    	if(addesclist != null && addesclist.size() > 0){
	    	listView.setAdapter(new CustomAdapter(this, addesclist));
	    	listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// 点击广告
					AdWall.requestAdEffect(addesclist.get(position).getAdId());
				}
			});
	    	return listView;
    	}else{
    		TextView text = new TextView(this);
    		text.setText("亲！你还没有深度任务哦！！！！");
    		text.setTextColor(Color.RED);
    		text.setTextSize(16.0f);
    		return text;
    	}
    	
    }
    
    
    private View createSigninListView() {
		ListView listView = new ListView(this);
    	
    	if(signinList != null && signinList.size() > 0){
	    	listView.setAdapter(new CustomAdapter(this, signinList));
	    	listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// 点击广告
					AdWall.requestAdEffect(signinList.get(position).getAdId());
				}
			});
	    	return listView;
    	}else{
    		TextView text = new TextView(this);
    		text.setText("亲！你还没有深度任务哦！！！！");
    		text.setTextColor(Color.RED);
    		text.setTextSize(16.0f);
    		return text;
    	}
}
    
    
    /**
     * 用于数据源广告列表的Adapter
     */
    private class CustomAdapter extends BaseAdapter{
    	Context context;
    	List<AdDesc> list;
    	public CustomAdapter(Context context, List<AdDesc> list){
    		this.context = context;
    		this.list = list;
    	}
    	
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			RelativeLayout r_layout;
        	ImageView app_icon;
        	TextView app_name;
        	RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        	
        	AdDesc adInfo = list.get(position);
    		r_layout = new RelativeLayout(context);
    		app_icon = new ImageView(context);
    		app_icon.setId(1);
    		app_name = new TextView(context);
    		
    		app_icon.setLayoutParams(new LayoutParams(75,75));
    		app_icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
    		
    		rlp.addRule(RelativeLayout.RIGHT_OF, app_icon.getId());
    		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
            app_icon.setImageDrawable(new BitmapDrawable(adInfo.icon));  
            app_icon.setPadding(5, 5, 5, 5);
            
            if(is_signin){
            	app_name.setText(adInfo.title + "    " +"        当前任务积分："+adInfo.points+"积分" + "             status=" + adInfo.status + "             statusMemo=" + adInfo.statusMemo);
            }else{
            	app_name.setText(adInfo.title + "    " + adInfo.appAction/* +"        总积分:"+adInfo.allTaskPoint+"积分"*/);
            }
            
            
            app_name.setTextSize(18);
            app_name.setTextColor(Color.MAGENTA);
            app_name.setPadding(10, 0, 0, 0);
            
            TextView content = new TextView(context);
            
			if(is_signin){
				content.setText(adInfo.earnStep);
			}else{
				StringBuffer buffer = new StringBuffer();
				if (adInfo.isTask) {
					if (adInfo.items != null) {
						if (adInfo.items.size() > 0) {	
							for (Object item : adInfo.items) {
								AdDescItem desc = (AdDescItem) item;
								buffer.append(desc.adDescDay + "  " + desc.adDescDescr + ",可获得积分"+desc.adDescScore +"\n");
							}
						}
					}
	            }
				content.setText(adInfo.text + "\n"+buffer.toString());
			}
            content.setPadding(10, 0, 0, 0);
            
            LinearLayout layout  = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            
            layout.addView(app_name);
            layout.addView(content);
            
            r_layout.addView(app_icon);
            r_layout.addView(layout, rlp);
            
        	convertView = r_layout;
        	convertView.setTag(r_layout);
            return r_layout;
		}
    }

	@Override
	public void onFailedGetAdSource(String errMsg) {
		// TODO Auto-generated method stub
		dismiss();
		Toast toast = Toast.makeText(getApplicationContext(),
			     "未获取到广告源数据", Toast.LENGTH_LONG);
	    toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	@Override
	public void onGetAdSource(List adSourceList,List adSigninList) {
		// TODO Auto-generated method stub
		dismiss();
		addesclist = adSourceList;
		signinList = adSigninList;
		if(addesclist != null && addesclist.size() > 0)
		{
		

				try {
//					LinearLayout layout=new LinearLayout(AdListActivity.this); 
//				    layout.setOrientation(LinearLayout.VERTICAL); 
//				    
//				      
//				    //
//				    View listView = createListView();   
//				   
//				    layout.addView(listView); 
//				//      
//				    setContentView(layout);
					
					LinearLayout layout=new LinearLayout(this); 
				    layout.setOrientation(LinearLayout.VERTICAL); 
				    layout.setBackgroundColor(Color.WHITE);
				    LinearLayout button_layout = new LinearLayout(this);
				    button_layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
				    button_layout.setOrientation(LinearLayout.HORIZONTAL);
				    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				    param.weight = 1;
				    param.height = 80;
				    Button left = new Button(this);
				    left.setText("任务列表");
				    
				    
				    Button right = new Button(this);
				    right.setText("深度任务");
				    
				    button_layout.addView(left, param);
				    button_layout.addView(right, param);

				    listView = createListView(); 
				    
				    signinView = createSigninListView();
				    left.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							is_signin = false;
							listView.setVisibility(View.VISIBLE);
							signinView.setVisibility(View.GONE);
						}
					});
				    
				    right.setOnClickListener(new OnClickListener() {
						
						

						@Override
						public void onClick(View arg0) {
							is_signin = true;
							listView.setVisibility(View.GONE);
							signinView.setVisibility(View.VISIBLE);
						}
					});
//				    // 
				    layout.addView(button_layout);
				    layout.addView(listView); 
				    layout.addView(signinView);
				    setContentView(layout);
					
				} catch (Exception e) {
					e.printStackTrace();
				}

		}
		
		else{
			
				Toast toast = Toast.makeText(getApplicationContext(),
					     "未获取到广告源数据", Toast.LENGTH_LONG);
			    toast.setGravity(Gravity.CENTER, 0, 0);
			    toast.show();
			
		}
	}
    
  //------------------ IAdWallRequestAdSourceNotifier ---------------------------//
  	
}
