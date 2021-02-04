package com.example.practiceapp

import android.app.Application
import com.example.practiceapp.di.AppInjector
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchAndroidInjector

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Logger.addLogAdapter(AndroidLogAdapter())
        }
        AppInjector.init(this)
    }
}
