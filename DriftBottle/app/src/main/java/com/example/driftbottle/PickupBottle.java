package com.example.driftbottle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

public class PickupBottle extends Activity {
	private  AnimationDrawable  ad;
	private  ImageView  pick_spray1,pick_spray2,voice_msg,close;
	private  RelativeLayout pick_up_layout;
	int hour,minute,sec;

    private ImageButton imgbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pick_up_bottle);

        imgbtn = (ImageButton)findViewById(R.id.bottle_picked_voice_msg);
        imgbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTask task=new MyTask();
                task.execute(DriftBottleActivity.getBottleURL+"city=dublin");
                Log.i("URL",DriftBottleActivity.getBottleURL+"city=dublin");
            }
        });

		pick_spray1=(ImageView) findViewById(R.id.pick_spray1);
		pick_spray2=(ImageView) findViewById(R.id.pick_spray2);
		voice_msg=(ImageView) findViewById(R.id.bottle_picked_voice_msg);
		close=(ImageView) findViewById(R.id.bottle_picked_close);
		pick_up_layout=(RelativeLayout) findViewById(R.id.pick_up_layout);
		

				Calendar c=Calendar.getInstance();
				c.setTimeInMillis(System.currentTimeMillis());
				hour=c.get(Calendar.HOUR_OF_DAY);
				minute=c.get(Calendar.MINUTE);
				sec=c.get(Calendar.SECOND);
				if(hour>=18 || hour<=6){
					pick_up_layout.setBackgroundResource(R.drawable.bottle_night_bg);
				}
				else{
					pick_up_layout.setBackgroundResource(R.drawable.bottle_day_bg);
				}
		close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				pick_spray1.setVisibility(View.VISIBLE);
				ad.setOneShot(true);
				ad.start();
			}
		}, 1000);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				pick_spray1.setVisibility(View.GONE);
				pick_spray2.setVisibility(View.VISIBLE);
				ad.setOneShot(true);
				ad.start();
			}
		}, 2000);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				pick_spray1.setVisibility(View.GONE);
				pick_spray2.setVisibility(View.GONE);
				voice_msg.setVisibility(View.VISIBLE);
				doStartAnimation(R.anim.pick_up_scale);
				close.setVisibility(View.VISIBLE);
			}
		}, 3000);
		
		
	}
	
	private void doStartAnimation(int animId){
    	Animation animation=AnimationUtils.loadAnimation(this,animId);
    	voice_msg.startAnimation(animation);
    }
	

			@Override
	public void onWindowFocusChanged(boolean hasFocus) {
				// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		ad=(AnimationDrawable) getResources().getDrawable(R.anim.pick_up_spray);
		if(pick_spray1!=null&&pick_spray2!=null){
			pick_spray1.setBackgroundDrawable(ad);
			pick_spray2.setBackgroundDrawable(ad);
		}
				
	}
    private class MyTask extends AsyncTask {
        private String msg="Drop Failed";
        private String content="";
        private boolean isBottle=false;
        private boolean isSuccess=false;
        private boolean containBottle=false;
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet((String) objects[0]);
                HttpResponse response = client.execute(request);
                if(response.getStatusLine().getStatusCode()==200){
                    HttpEntity entity=response.getEntity();
                    String json= EntityUtils.toString(entity, "UTF-8");
                    JSONObject jsonObject=new JSONObject(json);
                    if(jsonObject.get("type").equals("send")) {
                        isBottle=false;
                        if (jsonObject.get("status").equals("success")) {
                            isSuccess=true;
                            msg="Drop Success!";
                        }
                    }else{
                        isBottle=true;
                        if(jsonObject.get("status").equals("success")) {
                            JSONObject bottle = jsonObject.getJSONObject("bottle");
                            isSuccess=true;

                            if (bottle != null) {
                                containBottle = true;
                                content = bottle.getString("message");
                            }
                        }else{
                            msg="No Bottle";
                        }
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {

            super.onPostExecute(o);
            if(!isBottle){
                if(isSuccess){
                    Intent in4=new Intent(PickupBottle.this,ShowMessage.class);

                    startActivity(in4);

                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PickupBottle.this);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();


                        }
                    }).setTitle("Prompt").setMessage(msg).create().show();
                }
            }
            else {
                if (!containBottle) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PickupBottle.this);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setTitle("Warning").setMessage(msg).create().show();
                }else{
                    Intent intent=new Intent(PickupBottle.this,ShowMessage.class);
                    intent.putExtra("content", content);
                    startActivity(intent);
                }
            }
        }
    }
	

}
