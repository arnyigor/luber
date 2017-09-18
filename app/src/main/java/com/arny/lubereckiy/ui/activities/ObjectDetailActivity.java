package com.arny.lubereckiy.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.common.Local;

public class ObjectDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_detail);
        if (getIntent() != null) {
            Intent intent = getIntent();
            String url = intent.getStringExtra("url");
            Local.loadGenplan(url);
        }
    }
}
