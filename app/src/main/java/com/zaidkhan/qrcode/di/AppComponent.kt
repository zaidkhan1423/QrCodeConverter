package com.zaidkhan.qrcode.di

import androidx.lifecycle.ViewModelProvider
import com.zaidkhan.qrcode.view.fragment.HomeFragment
import com.zaidkhan.qrcode.view.fragment.ResultFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MyModule::class])
interface AppComponent {

    fun inject(homeFragment: HomeFragment)
    fun inject(resultFragment: ResultFragment)
    fun viewModelFactory(): ViewModelProvider.Factory

}