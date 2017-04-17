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
        tvRefreshing = (TextView) findViewById(R.id.tvRefreshing);
        buttonUpdate = (Button) findViewById(R.id.btnGetData);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpdate();
                buttonUpdate.setEnabled(false);
            }
        });
        if (!Utility.empty(Config.getString(Config.LAST_UPDATE, this))) {
            tvRefreshing.setText(getString(R.string.lastUpdateIs).concat(Config.getString(Config.LAST_UPDATE, this)));
        }
//        findViewById(R.id.btnGet).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setTitle("Обновление генплана");
//                progressBar.setVisibility(View.VISIBLE);
//                tvRefreshing.setVisibility(View.VISIBLE);
//                startUpdate(context,operationIntent);
//            }
//        });
    }

    private void startUpdate() {
        tvRefreshing.setText(R.string.updateData);
        Config.remove(Config.LAST_UPDATE, this);
        mMyServiceIntent.putExtra(BackgroundIntentService.EXTRA_KEY_OPERATION_CODE, BackgroundIntentService.OPERATION_PARSE_GSON);
        startService(mMyServiceIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(BackgroundIntentService.ACTION);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
//        initUpdate(Config.getString(Config.LAST_UPDATE, this));
    }

    public void initUpdate(String lastupdate) {
        if (Utility.isServiceRunning(BackgroundIntentService.class, this)) {
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
    }

    private static boolean isMustUpdate(String lastupdate) {
        return Utility.empty(lastupdate) || isUpdateTimeOver(lastupdate);
    }

    private static boolean isUpdateTimeOver(String lastupdate) {
        long lustUpdateTimeStamp = Utility.convertTimeStringToLong(lastupdate, "HH:mm dd MM yyyy");
        Log.i(TAG, "isUpdateTimeOver: lastUpdate = " + Utility.getDateTime(lustUpdateTimeStamp));
        long currentTimeStamp = System.currentTimeMillis();
        Log.i(TAG, "isUpdateTimeOver: currentTimeStamp = " + Utility.getDateTime(currentTimeStamp));
        long overTime = (1000 * 60 * 60) * 1;//1 час
        Log.i(TAG, "isUpdateTimeOver: diff = " + (currentTimeStamp - lustUpdateTimeStamp));
        Log.i(TAG, "isUpdateTimeOver: overTime = " + overTime);
        return (currentTimeStamp - lustUpdateTimeStamp) > overTime;
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
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
