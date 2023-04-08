package com.zaidkhan.qrcode.repository

import android.graphics.Bitmap
import android.graphics.Color
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class MainRepository @Inject constructor() {

    private val _dataOfBitmap = MutableLiveData<Bitmap>()
    val dataOfBitmap: LiveData<Bitmap> = _dataOfBitmap

    fun getERGEncoder(data: String = "",dimension: Int = 81){
        val qrgEncoder = QRGEncoder(data, QRGContents.Type.TEXT, dimension)
        qrgEncoder.colorBlack = Color.WHITE
        qrgEncoder.colorWhite = Color.BLACK
        // Getting QR-Code as Bitmap
        _dataOfBitmap.postValue(qrgEncoder.bitmap)
    }

}