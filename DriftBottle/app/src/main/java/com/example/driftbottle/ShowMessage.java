package com.example.driftbottle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by yamin on 15/3/12.
 */
public class ShowMessage extends Activity {
    private TextView messageText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        messageText=(TextView)findViewById(R.id.messageText);
        messageText.setText(getIntent().getStringExtra("content"));

    }
}
