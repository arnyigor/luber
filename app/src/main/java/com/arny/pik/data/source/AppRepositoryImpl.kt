package com.arny.pik.data.source

import android.content.Context
import com.arny.pik.PikApp

class AppRepositoryImpl : BaseRepository {
    private object Holder {
        val INSTANCE = AppRepositoryImpl()
    }

    companion object {
        val instance: AppRepositoryImpl by lazy { Holder.INSTANCE }
    }

    override fun getContext(): Context {
        return PikApp.appContext
    }
}