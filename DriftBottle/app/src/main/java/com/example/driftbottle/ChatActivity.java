package com.example.driftbottle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by yamin on 15/3/12.
 */
public class ChatActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chat);
    }
}
