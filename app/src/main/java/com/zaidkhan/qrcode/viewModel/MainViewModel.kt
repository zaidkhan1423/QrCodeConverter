package com.zaidkhan.qrcode.viewModel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zaidkhan.qrcode.repository.MainRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val dataBitmap: LiveData<Bitmap> = repository.dataOfBitmap

    fun getERGEncoder(data: String, dimension: Int) {
        repository.getERGEncoder(data, dimension)
    }

}