package com.arny.pik

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.facebook.stetho.Stetho
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader

class PikApp : Application() {

    companion object {
        @JvmStatic
        lateinit var appContext: Context
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        BigImageViewer.initialize(GlideImageLoader.with(this))
        Stetho.initializeWithDefaults(this)
    }
}
