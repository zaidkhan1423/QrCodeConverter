package com.zaidkhan.qrcode.di

import androidx.lifecycle.ViewModelProvider
import com.zaidkhan.qrcode.repository.MainRepository
import com.zaidkhan.qrcode.viewModel.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MyModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(repository: MainRepository): ViewModelProvider.Factory {
        return ViewModelFactory(repository)
    }

}