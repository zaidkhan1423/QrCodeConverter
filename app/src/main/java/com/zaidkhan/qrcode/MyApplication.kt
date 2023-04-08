package com.zaidkhan.qrcode

import android.app.Application
import com.zaidkhan.qrcode.di.AppComponent
import com.zaidkhan.qrcode.di.DaggerAppComponent

class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .build()
    }
}