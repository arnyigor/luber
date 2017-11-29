package com.arny.pik;

import android.app.Application;
import android.support.multidex.MultiDex;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
        MultiDex.install(this);
        // or load with glide
        BigImageViewer.initialize(GlideImageLoader.with(this));
	}
}
