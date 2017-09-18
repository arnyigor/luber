package com.arny.lubereckiy;

import android.app.Application;
import android.support.multidex.MultiDex;
import io.realm.Realm;
public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
        MultiDex.install(this);
		// Initialize Realm
		Realm.init(this);
	}
}
