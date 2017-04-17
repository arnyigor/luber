package com.arny.lubereckiy;

import android.app.Application;
import android.content.Context;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
public class App extends Application {

	private RefWatcher refWatcher;
	@Override public void onCreate() {
		super.onCreate();
		if (LeakCanary.isInAnalyzerProcess(this)) {
			// This process is dedicated to LeakCanary for heap analysis.
			// You should not init your app in this process.
			return;
		}
		LeakCanary.install(this);
		// Normal app init code...
	}

	public static RefWatcher getRefWatcher(Context context) {
		App app = (App) context.getApplicationContext();
		return app.refWatcher;
	}
}