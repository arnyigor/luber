package com.arny.lubereckiy.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.arny.lubereckiy.R;
import com.arny.lubereckiy.service.OperationProvider;
import com.arny.lubereckiy.service.Operations;
import com.arny.lubereckiy.utils.ToastMaker;
import com.arny.lubereckiy.utils.Utility;

public class HomeActivity extends AppCompatActivity {
    private Context context = HomeActivity.this;
    public boolean operationFinished = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.btnGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationFinished = false;
                if (!operationFinished) {
                    setTitle("Обновление генплана");
                }
                Operations.onStartOperation(context,Operations.EXTRA_KEY_TYPE_ASYNC,Operations.OPERATION_GET_GEN_PLAN,null);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Operations.ACTION);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(this).registerReceiver(operationReciever, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(operationReciever);
    }

    private BroadcastReceiver operationReciever = new BroadcastReceiver() {
        public boolean success;
        public int operation;
        public String operationResult = "";
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                OperationProvider provider = extras.getParcelable(Operations.EXTRA_KEY_OPERATION);
                operationFinished = provider.isFinished();
                success = provider.isSuccess();
                operation = provider.getId();
                operationResult = provider.getResult();
                Log.i(HomeActivity.class.getSimpleName(), "onReceive: extras operation= " + operation);
                Log.i(HomeActivity.class.getSimpleName(), "onReceive: extras operationFinished= " + operationFinished);
                Log.i(HomeActivity.class.getSimpleName(), "onReceive: extras success= " + success);
//                Log.i(HomeActivity.class.getSimpleName(), "onReceive: extras getOperationData= " + provider.getOperationData());
                Log.i(HomeActivity.class.getSimpleName(), "onReceive: extras operationResult= " + operationResult);
                Log.i(HomeActivity.class.getSimpleName(), "onReceive: time = " + Utility.getDateTime());
            }
            if (operationFinished) {
                ToastMaker.toast(context,"Результат операции " + operation + ":"+ operationResult, success);
                if (operation==Operations.OPERATION_PARSE_GEN_PLAN){
                    setTitle("Обновление генплана завершено");
                }
            }
        }
    };

}
