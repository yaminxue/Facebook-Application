package com.example.driftbottle;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBottle extends ListActivity {


    private List<Map<String, Object>> mData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        mData = getData();
        MyAdapter adapter = new MyAdapter(this);
        setListAdapter(adapter);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("icon", R.drawable.ic_launcher);
        map.put("message", "Hello");
        map.put("chatImage",R.drawable.chat);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("icon", R.drawable.ic_launcher);
        map.put("message", "I am sad.");
        map.put("chatImage",R.drawable.chat);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("icon", R.drawable.ic_launcher);
        map.put("message", "I need a shoulder.");
        map.put("chatImage",R.drawable.chat);
        list.add(map);

        return list;
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Log.v("GuideListActivity-click", (String) mData.get(position).get("message"));
    }


    public final class ViewHolder{
        public ImageView icon;
        public TextView message;
        public ImageButton chat;
    }


    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;


        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {

                holder=new ViewHolder();

                convertView = mInflater.inflate(R.layout.my_bottle, null);
                holder.icon = (ImageView)convertView.findViewById(R.id.iconImage);
                holder.message = (TextView)convertView.findViewById(R.id.showmsgText);
                holder.chat = (ImageButton)convertView.findViewById(R.id.chatButton);
                convertView.setTag(holder);

            }else {

                holder = (ViewHolder)convertView.getTag();
            }



            holder.icon.setBackgroundResource((Integer)mData.get(position).get("icon"));
            holder.message.setText((String)mData.get(position).get("message"));
            holder.chat.setBackgroundResource((Integer)mData.get(position).get("chatImage"));

            holder.chat.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyBottle.this, ChatActivity.class);
                    startActivity(intent);
                }
            });


            return convertView;
        }

    }


}
