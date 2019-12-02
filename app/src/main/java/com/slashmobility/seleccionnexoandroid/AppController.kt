package com.slashmobility.seleccionnexoandroid

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.slashmobility.seleccionnexoandroid.di.components.DaggerAppComponent
import com.slashmobility.seleccionnexoandroid.di.modules.ApiModule
import com.slashmobility.seleccionnexoandroid.di.modules.DatabaseModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class AppController : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .apiModule(ApiModule())
            .dbModule(DatabaseModule())
            .build()
            .inject(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
