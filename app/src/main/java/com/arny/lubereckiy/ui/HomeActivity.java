package com.arny.lubereckiy.ui;

import android.content.*;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.models.Korpus;
import com.arny.lubereckiy.service.BackgroundIntentService;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private Context context = HomeActivity.this;
    private Intent mMyServiceIntent;
    private TextView tvRefreshing,tvInfo;
    private Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mMyServiceIntent = new Intent(context, BackgroundIntentService.class);
        tvRefreshing = (TextView) findViewById(R.id.tvRefreshing);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        buttonUpdate = (Button) findViewById(R.id.btnGetData);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRunning = Utility.isServiceRunning(BackgroundIntentService.class, HomeActivity.this);
                if (!isRunning) {
                    startUpdate();
                }
            }
        });
        if (!Utility.empty(Config.getString(Config.LAST_UPDATE, this))) {
            tvRefreshing.setText(getString(R.string.lastUpdateIs).concat(Config.getString(Config.LAST_UPDATE, this)));
        }
        Korpus korpus = DBProvider.getKorpus(this, "2b3ecc9b-bfad-e611-9fbe-001ec9d5643c");
        if (korpus != null) {
            tvInfo.setText(korpus.getTitle() + " свободных: " + korpus.getFree() + " заняты:" + korpus.getBusy() + " продано: " + korpus.getSold());
        }

    }

    private void startUpdate() {
        buttonUpdate.setEnabled(false);
        tvRefreshing.setText(R.string.updateData);
        mMyServiceIntent.putExtra(BackgroundIntentService.EXTRA_KEY_OPERATION_CODE, BackgroundIntentService.OPERATION_PARSE_GSON);
        startService(mMyServiceIntent);
    }

    public void initUpdate(String lastupdate) {
        long startUpdate = System.currentTimeMillis();
        Log.i(HomeActivity.class.getSimpleName(), "initUpdate: " + Utility.getDateTime(startUpdate));
        boolean isRunning = Utility.isServiceRunning(BackgroundIntentService.class, HomeActivity.this);
        Log.i(HomeActivity.class.getSimpleName(), "initUpdate: проверка запущен ли сервис  =  " + (System.currentTimeMillis()-startUpdate));
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
            }
        }
        Log.i(HomeActivity.class.getSimpleName(), "initUpdate: finish =  " + (System.currentTimeMillis()-startUpdate));
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
    protected void onResume() {
        super.onResume();
//        IntentFilter filter = new IntentFilter(BackgroundIntentService.ACTION);
//        filter.addCategory(Intent.CATEGORY_DEFAULT);
//        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
//        Log.i(HomeActivity.class.getSimpleName(), "onResume: serviceRuning = " + Utility.isServiceRunning(BackgroundIntentService.class,this));
//        initUpdate(Config.getString(Config.LAST_UPDATE, HomeActivity.this));
    }

    @Override
    protected void onPause() {
//        long startPause = System.currentTimeMillis();
//        Log.i(HomeActivity.class.getSimpleName(), "onPause: start = " +  Utility.getDateTime(startPause) );
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
//        Log.i(HomeActivity.class.getSimpleName(), "onPause: finish register = " +(System.currentTimeMillis()-startPause) );
        super.onPause();
//        Log.i(HomeActivity.class.getSimpleName(), "onPause: finish pause = " +(System.currentTimeMillis()-startPause) );
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public HashMap<String, String> hashMap;
        public boolean operationSuccess;
        public String mOperationResult;
        public int mOperation;
        public boolean finishOperation;

        @Override
        public void onReceive(Context context, Intent intent) {
//            try {
//                finishOperation = intent.getBooleanExtra(BackgroundIntentService.EXTRA_KEY_FINISH, false);
//                mOperation = intent.getIntExtra(BackgroundIntentService.EXTRA_KEY_OPERATION_CODE, BackgroundIntentService.OPERATION_PARSE_GSON);
//                mOperationResult = intent.getStringExtra(BackgroundIntentService.EXTRA_KEY_OPERATION_RESULT);
//                operationSuccess = intent.getBooleanExtra(BackgroundIntentService.EXTRA_KEY_FINISH_SUCCESS, false);
//                hashMap = (HashMap<String, String>) intent.getSerializableExtra(BackgroundIntentService.EXTRA_KEY_OPERATION_DATA);
////                Log.i(TAG, "onReceive: finishOperation = " + finishOperation);
////                Log.i(TAG, "onReceive: mOperation = " + mOperation);
////                Log.i(TAG, "onReceive: mOperationResult = " + mOperationResult);
////                Log.i(TAG, "onReceive: operationSuccess = " + operationSuccess);
////                Log.i(TAG, "onReceive: hashMap = " + hashMap);
//                if (finishOperation) {
//                    buttonUpdate.setEnabled(true);
//                    if (Config.getString(Config.LAST_UPDATE, HomeActivity.this) != null) {
//                        tvRefreshing.setText(getString(R.string.lastUpdateIs).concat(Config.getString(Config.LAST_UPDATE, HomeActivity.this)));
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        }
    };

}
