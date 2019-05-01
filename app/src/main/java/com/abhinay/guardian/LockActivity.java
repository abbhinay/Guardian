package com.abhinay.guardian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LockActivity extends AppCompatActivity {
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        mTextView = (TextView) findViewById(R.id.textView2);

        Intent myIntent = getIntent();
        String city = myIntent.getStringExtra("abhinay_pkg_name");
        mTextView.setText(city);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
