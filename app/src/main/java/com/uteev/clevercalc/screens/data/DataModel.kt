package com.uteev.clevercalc.screens.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel : ViewModel() {
    val messageForFragmentGraphic : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}