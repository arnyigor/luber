package com.arny.lubereckiy;
import android.app.Application;
import android.content.Context;
import com.squareup.leakcanary.LeakCanary;
public class ApplicationController extends Application {
	private static ApplicationController mInstance;
	private static Context mAppContext;

	@Override
	public void onCreate() {
		super.onCreate();
		if (LeakCanary.isInAnalyzerProcess(this)) {
			return;
		}
		LeakCanary.install(this);
		mInstance = this;
		this.setAppContext(getApplicationContext());
	}

	public static ApplicationController getInstance() {
		return mInstance;
	}

	public static Context getAppContext() {
		return mAppContext;
	}

	public void setAppContext(Context mAppContext) {
		ApplicationController.mAppContext = mAppContext;
	}
}