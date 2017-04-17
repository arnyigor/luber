package com.arny.lubereckiy.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.service.BackgroundIntentService;
import com.arny.lubereckiy.utils.Config;
import com.arny.lubereckiy.utils.Utility;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private Context context = HomeActivity.this;
    private Intent mMyServiceIntent;
    private TextView tvRefreshing;
    private Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mMyServiceIntent = new Intent(context, BackgroundIntentService.class);
        IntentFilter filter = new IntentFilter(BackgroundIntentService.ACTION);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
        tvRefreshing = (TextView) findViewById(R.id.tvRefreshing);
        buttonUpdate = (Button) findViewById(R.id.btnGetData);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRunning = Utility.checkServiceRunning(BackgroundIntentService.class, HomeActivity.this);
                Log.i(HomeActivity.class.getSimpleName(), "onClick: isRunning = " + isRunning);
                if (!isRunning) {
                    startUpdate();
                }
            }
        });
        if (!Utility.empty(Config.getString(Config.LAST_UPDATE, this))) {
            tvRefreshing.setText(getString(R.string.lastUpdateIs).concat(Config.getString(Config.LAST_UPDATE, this)));
        }
    }

    private void startUpdate() {
//        buttonUpdate.setEnabled(false);
        tvRefreshing.setText(R.string.updateData);
        Config.remove(Config.LAST_UPDATE, this);
        mMyServiceIntent.putExtra(BackgroundIntentService.EXTRA_KEY_OPERATION_CODE, BackgroundIntentService.OPERATION_PARSE_GSON);
        startService(mMyServiceIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        initUpdate(Config.getString(Config.LAST_UPDATE, HomeActivity.this));
    }

    public void initUpdate(String lastupdate) {
        long startUpdate = System.currentTimeMillis();
        boolean isRunning = Utility.checkServiceRunning(BackgroundIntentService.class, HomeActivity.this);
        Log.i(HomeActivity.class.getSimpleName(), "initUpdate: isRunning = " + isRunning);
        if (isRunning) {
            tvRefreshing.setText(R.string.updateData);
            buttonUpdate.setEnabled(false);
        }else{
            if (Utility.empty(lastupdate)) {
                startUpdate();
            }else if (isMustUpdate(lastupdate)) {
                startUpdate();
            }else{
                if (Config.getString(Config.LAST_UPDATE, this) != null) {
                    tvRefreshing.setText(getString(R.string.lastUpdateIs).concat(Config.getString(Config.LAST_UPDATE, this)));
                }
                tvRefreshing.setText(getString(R.string.lastUpdateIs).concat(Config.getString(Config.LAST_UPDATE, this)));
            }
        }
        long finishUpdate = System.currentTimeMillis();
        Log.i(HomeActivity.class.getSimpleName(), "initUpdate: time =  " + (finishUpdate-startUpdate));
    }

    private boolean isMustUpdate(String lastupdate) {
        return Utility.empty(lastupdate) || isUpdateTimeOver(lastupdate);
    }

    private boolean isUpdateTimeOver(String lastupdate) {
        long lustUpdateTimeStamp = Utility.convertTimeStringToLong(lastupdate, "HH:mm dd MM yyyy");
        long currentTimeStamp = System.currentTimeMillis();
        long overTime = (1000 * 60 * 60) * Config.getInt(Config.UPDATE_INTERVAL,HomeActivity.this,1);
        return (currentTimeStamp - lustUpdateTimeStamp) > overTime;
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public HashMap<String, String> hashMap;
        public boolean operationSuccess;
        public String mOperationResult;
        public int mOperation;
        public boolean finishOperation;

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                finishOperation = intent.getBooleanExtra(BackgroundIntentService.EXTRA_KEY_FINISH, false);
                mOperation = intent.getIntExtra(BackgroundIntentService.EXTRA_KEY_OPERATION_CODE, BackgroundIntentService.OPERATION_PARSE_GSON);
                mOperationResult = intent.getStringExtra(BackgroundIntentService.EXTRA_KEY_OPERATION_RESULT);
                operationSuccess = intent.getBooleanExtra(BackgroundIntentService.EXTRA_KEY_FINISH_SUCCESS, false);
                hashMap = (HashMap<String, String>) intent.getSerializableExtra(BackgroundIntentService.EXTRA_KEY_OPERATION_DATA);
                Log.i(TAG, "onReceive: finishOperation = " + finishOperation);
                Log.i(TAG, "onReceive: mOperation = " + mOperation);
                Log.i(TAG, "onReceive: mOperationResult = " + mOperationResult);
                Log.i(TAG, "onReceive: operationSuccess = " + operationSuccess);
                Log.i(TAG, "onReceive: hashMap = " + hashMap);
                if (finishOperation && operationSuccess) {
                    buttonUpdate.setEnabled(true);
                    if (Config.getString(Config.LAST_UPDATE, HomeActivity.this) != null) {
                        tvRefreshing.setText(getString(R.string.lastUpdateIs).concat(Config.getString(Config.LAST_UPDATE, HomeActivity.this)));
                    }
                    tvRefreshing.setText(getString(R.string.lastUpdateIs).concat(Config.getString(Config.LAST_UPDATE, HomeActivity.this)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

}
