package com.example.driftbottle;

import java.io.IOException;
import java.util.Calendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Base64;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.facebook.AppEventsLogger;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;

public class DriftBottleActivity extends Activity {
	
    private UiLifecycleHelper uihelper;
    private static final String TAG = "ACCOUNT";

    void showMsg(String string)
    {
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }


    private Session.StatusCallback callback =new Session.StatusCallback()
    {

        @Override
        public void call(Session session, SessionState state, Exception exception)
        {

            onSessionStateChange(session,state,exception);
        }
    };


    void onSessionStateChange(Session session, SessionState state, Exception exception)
    {

        if (state.isOpened())
        {
            Log.i("facebook", "Logged in...");
            Request.newMeRequest(session, new Request.GraphUserCallback() {

                @Override
                public void onCompleted(GraphUser user, Response response) {

                    if (user != null) {
                        showMsg(user.getName());
                        showMsg(user.getProperty("email") + "");
                        showMsg(user.getProperty("gender") + "");
                        showMsg(user.getId() + "");

                    } else {
                        showMsg("its null");
                        showMsg(response.getError().getErrorMessage());
                    }
                }
            }).executeAsync();

        }
        else if (state.isClosed())
        {
            Log.i("facebook", "Logged out...");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uihelper.onSaveInstanceState(outState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        uihelper.onDestroy();
    }

	
    public static String IPADDRESS="10.6.44.251";
    public static String sendBottleURL="http://"+IPADDRESS+":8080/GuideFinder/saveBottle.action?";
    public static String getBottleURL="http://"+IPADDRESS+":8080/GuideFinder/getBottle.action?";
	
	private  PopupWindow  popupWindow,pop1;
	private  View view;
	private  LayoutInflater inflater;
	RelativeLayout layout,bottle_main_layout,bottle_bottom_layout,bottle_night_bottom_layout;
	private  ImageView  bottle_night_moon,bottle_night_floodlight,bottle_night_floodlight_1,bottle_night_floodlight_2;
	private  EditText  chuck_edit;
	private  Button  chuck_keyboard,chuck_speak,bottle_back;
	private  ImageView  chuck_toast;
	private  ImageView  bottle_img,bottle1,bottle2,bottle3,bottle_title;
	private  TranslateAnimation  ani0,ani1,ani2;
	ImageView chuck_empty2,chuck_empty1,chuck_spray;
	ImageView  bottle_night_bottom1,bottle_night_bottom2,bottle_night_bottom3;
	int m=2;
	
	int hour,minute,sec;
	
	AnimationDrawable ad,ad1,ad2;
	
    @Override
    protected void onResume() {
        super.onResume();

        uihelper.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);

    }

    @Override
    protected void onPause() {
        super.onPause();


        uihelper.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
        SysApplication.addActivity(this, TAG);
        uihelper =new UiLifecycleHelper(this,callback);
        uihelper.onCreate(savedInstanceState);

        ArrayList<String> permission =new ArrayList<String>();
        permission.add("email");
        permission.add("public_profile");
        permission.add("user_friends");

        LoginButton btn=(LoginButton)findViewById(R.id.authButton);
        btn.setPublishPermissions(permission);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.testing",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();}

		bottle_main_layout=(RelativeLayout) findViewById(R.id.bottle_main_layout);
		bottle_bottom_layout=(RelativeLayout) findViewById(R.id.bottle_bottom_layout);
		bottle_night_bottom_layout=(RelativeLayout) findViewById(R.id.bottle_night_bottom_layout);
		 bottle_night_moon=(ImageView) findViewById(R.id.bottle_night_moon);
		 bottle_night_floodlight=(ImageView) findViewById(R.id.bottle_night_floodlight);
		 bottle_night_floodlight_1=(ImageView) findViewById(R.id.bottle_night_floodlight_1);
		 bottle_night_floodlight_2=(ImageView) findViewById(R.id.bottle_night_floodlight_2);
		 bottle_night_bottom1=(ImageView) findViewById(R.id.bottle_night_bottom1);
		 bottle_night_bottom2=(ImageView) findViewById(R.id.bottle_night_bottom2);
		 bottle_night_bottom3=(ImageView) findViewById(R.id.bottle_night_bottom3);
		 bottle_night_bottom1.setOnClickListener(listener);
		 bottle_night_bottom2.setOnClickListener(listener);
		 bottle_night_bottom3.setOnClickListener(listener);
		
		bottle_img=(ImageView) findViewById(R.id.bottle_img);
		bottle1=(ImageView) findViewById(R.id.bottle_bottom1);
		bottle1.setOnClickListener(listener);
		bottle2=(ImageView) findViewById(R.id.bottle_bottom2);
		bottle2.setOnClickListener(listener);
		bottle3=(ImageView) findViewById(R.id.bottle_bottom3);
		bottle3.setOnClickListener(listener);
		bottle_title=(ImageView) findViewById(R.id.bottle_title);
		layout=(RelativeLayout) findViewById(R.id.bottle_layout_title);
		bottle_back=(Button) findViewById(R.id.bottle_back);
		bottle_back.setOnClickListener(listener);
		bottle_title.setOnClickListener(listener);
		ani0 = new TranslateAnimation( 
		Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.3f, 
		Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -0.3f); 
		ani1= new TranslateAnimation( 
		Animation.RELATIVE_TO_PARENT, 0.3f, Animation.RELATIVE_TO_PARENT, 0.6f, 
		Animation.RELATIVE_TO_SELF, -0.3f, Animation.RELATIVE_TO_SELF, 0.2f);
		ani2= new TranslateAnimation( 
				Animation.RELATIVE_TO_PARENT, 0.6f, Animation.RELATIVE_TO_PARENT, 0.0f, 
				Animation.RELATIVE_TO_SELF, 0.2f, Animation.RELATIVE_TO_SELF, 0.0f); 
		ani0.setInterpolator(new AccelerateDecelerateInterpolator()); 
		ani0.setDuration(15000); 
		ani0.setFillEnabled(true); 
		ani0.setFillAfter(true); 
		ani0.setAnimationListener(animationListener); 
		ani1.setInterpolator(new AccelerateDecelerateInterpolator()); 
		ani1.setDuration(15000); 
		ani1.setFillEnabled(true); 
		ani1.setFillAfter(true); 
		ani1.setAnimationListener(animationListener);
		ani2.setInterpolator(new AccelerateDecelerateInterpolator()); 
		ani2.setDuration(15000); 
		ani2.setFillEnabled(true); 
		ani2.setFillAfter(true); 
		ani2.setAnimationListener(animationListener);
		
		bottle_img.startAnimation(ani0);
		
		

		Calendar c=Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		hour=c.get(Calendar.HOUR_OF_DAY);
		minute=c.get(Calendar.MINUTE);
		sec=c.get(Calendar.SECOND);
		if(hour>=18 || hour<=6){
			bottle_main_layout.setBackgroundResource(R.drawable.bottle_night_bg);
			bottle_img.setVisibility(View.GONE);
			bottle_bottom_layout.setVisibility(View.GONE);
			bottle_night_moon.setVisibility(View.VISIBLE);
			bottle_night_floodlight.setVisibility(View.VISIBLE);
			bottle_night_bottom_layout.setVisibility(View.VISIBLE);

			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					ad.setOneShot(false);
					ad.start();
				}
			}, 1000);
			

		}
		else{
			bottle_main_layout.setBackgroundResource(R.drawable.bottle_day_bg);
			bottle_img.setVisibility(View.VISIBLE);
			bottle_bottom_layout.setVisibility(View.VISIBLE);
			bottle_night_moon.setVisibility(View.GONE);
			bottle_night_floodlight.setVisibility(View.GONE);
			bottle_night_bottom_layout.setVisibility(View.GONE);
		}
			
	}
	
	private  OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bottle_bottom1:
				initChuckPop();
				popupWindow.showAtLocation(findViewById(R.id.bottle_bottom1), Gravity.BOTTOM, 0, 0);
				break;
			case R.id.bottle_bottom2:
				Intent in=new Intent(DriftBottleActivity.this,PickupBottle.class);
				startActivity(in);			
				break;
			case R.id.bottle_bottom3:
				Intent in3=new Intent(DriftBottleActivity.this,MyBottle.class);
				startActivity(in3);	
				break;
			case R.id.bottle_night_bottom1:
				initChuckPop();
				popupWindow.showAtLocation(findViewById(R.id.bottle_bottom1), Gravity.BOTTOM, 0, 0);
				break;
			case R.id.bottle_night_bottom2:
				Intent in1=new Intent(DriftBottleActivity.this,PickupBottle.class);
				startActivity(in1);
				break;
			case R.id.bottle_night_bottom3:
				Intent in2=new Intent(DriftBottleActivity.this,MyBottle.class);
				startActivity(in2);	
				break;
			case R.id.bottle_back:
				finish();
//				Driftbottle_setting driftbottle_setting=
				break;
			case R.id.bottle_title:
				Intent i=new Intent(DriftBottleActivity.this,Driftbottle_setting.class);
				startActivity(i);
				break;
			case R.id.chuck_keyboard:
				if(v.isClickable()){
					chuck_edit.setVisibility(View.VISIBLE);
					chuck_toast.setVisibility(View.GONE);
					chuck_speak.setText("Drop Bottle");
					chuck_keyboard.setBackgroundResource(R.drawable.chatting_setmode_voice_btn_normal);
					
				}
				if(m%2==0){
					chuck_keyboard.setBackgroundResource(R.drawable.chat_voice);
					InputMethodManager inputMethodManager=(InputMethodManager) chuck_edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.showSoftInput(chuck_edit, InputMethodManager.RESULT_SHOWN);
					inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
				}else if(m%2!=0){
					chuck_keyboard.setBackgroundResource(R.drawable.chatting_setmode_keyboard_btn_normal);
					chuck_edit.setVisibility(View.GONE);
					chuck_speak.setText("hold to speak");
					chuck_toast.setVisibility(View.VISIBLE);
				}
				m++;
				break;
			case R.id.chuck_speak:

//				Intent in4=new Intent(DriftBottleActivity.this,chuck_animation.class);
//				startActivity(in4);
//				popupWindow.dismiss();
                MyTask task=new MyTask();
                task.execute(sendBottleURL+"city=dublin&"+"message="+chuck_edit.getText().toString());
                Log.i("URL",sendBottleURL+"city=dublin&"+"message="+chuck_edit.getText().toString());
                break;
			}
			
		}
	};
	
	private void doStartAnimation(int animId){
    	Animation animation=AnimationUtils.loadAnimation(this,animId);
    	chuck_empty1.startAnimation(animation);
    	chuck_empty2.startAnimation(animation);
    }
	
	final AnimationListener animationListener =new AnimationListener(){

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			if(animation==ani0){
				bottle_img.startAnimation(ani1);
			}
			if(animation==ani1){
				bottle_img.startAnimation(ani2);
			}
			if(animation==ani2){
				bottle_img.startAnimation(ani0);
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}};
		
		
		
		
		@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stubs
		super.onWindowFocusChanged(hasFocus);
		
		if(bottle_night_floodlight!=null){
			ad=(AnimationDrawable) getResources().getDrawable(R.anim.bottle_night_floodlight);
			bottle_night_floodlight.setBackgroundDrawable(ad);
		}
		
		else if(bottle_night_floodlight_1!=null){
			ad1=(AnimationDrawable) getResources().getDrawable(R.anim.bottle_night_floodlight1);
			bottle_night_floodlight_1.setBackgroundDrawable(ad);
		}
		else if(bottle_night_floodlight_2!=null){
			ad2=(AnimationDrawable) getResources().getDrawable(R.anim.bottle_night_floodlight2);
			bottle_night_floodlight_2.setBackgroundDrawable(ad);
		}
	}


		public  void  initChuckPop(){
			inflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			view=inflater.inflate(R.layout.chuck_pop, null);
			popupWindow=new PopupWindow(view, WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
			ColorDrawable colorDrawable=new ColorDrawable(0xb0000000);
			popupWindow.setBackgroundDrawable(colorDrawable);
			popupWindow.setTouchable(true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setFocusable(true);
			
			view.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					int height=view.findViewById(R.id.chuck_layout1).getTop();
					int y=(int) event.getY();
					if(event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_DOWN){
						if(y<height){
							popupWindow.dismiss();
							popupWindow=null;
						}
						if(y>height){
							popupWindow.dismiss();
							popupWindow=null;
						}
					}
					return true;
				}
			});
			
			chuck_edit=(EditText) view.findViewById(R.id.chuck_edit);
			chuck_toast=(ImageView) view.findViewById(R.id.chuck_toast);
			chuck_keyboard=(Button) view.findViewById(R.id.chuck_keyboard);
			chuck_speak=(Button) view.findViewById(R.id.chuck_speak);
			
			chuck_keyboard.setOnClickListener(listener);
			chuck_speak.setOnClickListener(listener);
		}
		

				public  void  initChuckPop1(){
					inflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					view=inflater.inflate(R.layout.chuck_pop1, null);
					pop1=new PopupWindow(view, WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
					pop1.setTouchable(true);
					pop1.setOutsideTouchable(true);
					pop1.setFocusable(true);
					
					view.setOnTouchListener(new OnTouchListener() {
						
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							int height=view.findViewById(R.id.chuck_pop_layout).getTop();
							int y=(int) event.getY();
							if(event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_DOWN){
								if(y<height){
									pop1.dismiss();
									pop1=null;
								}
								if(y>height){
									pop1.dismiss();
									pop1=null;
								}
							}
							return true;
						}
					});
					
					chuck_empty2 = (ImageView) view.findViewById(R.id.chuck_empty2);
					chuck_empty1=(ImageView) view.findViewById(R.id.chuck_empty1);
					chuck_spray=(ImageView) view.findViewById(R.id.chuck_spray);
					
					
				}

				@Override
				public boolean onTouchEvent(MotionEvent event) {

					float x=event.getX();
					float y=event.getY();

					switch (event.getAction()) {

					case MotionEvent.ACTION_DOWN:
						m++;
					if(m%2==0)
						layout.setVisibility(View.GONE);
					else
						layout.setVisibility(View.VISIBLE);
				
						break;
					}
					return super.onTouchEvent(event);
				
				}
    private class MyTask extends AsyncTask{
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
                    String json= EntityUtils.toString(entity,"UTF-8");
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
                    Intent in4=new Intent(DriftBottleActivity.this,chuck_animation.class);
                    startActivity(in4);
                    popupWindow.dismiss();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DriftBottleActivity.this);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(DriftBottleActivity.this);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setTitle("Warning").setMessage(msg).create().show();
                }else{
                    Intent intent=new Intent(DriftBottleActivity.this,ShowMessage.class);
                    intent.putExtra("content", content);
                    startActivity(intent);
                }
            }
        }
    }
	
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uihelper.onActivityResult(requestCode, resultCode, data);
    }
}
