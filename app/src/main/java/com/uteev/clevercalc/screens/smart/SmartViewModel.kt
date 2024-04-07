package com.uteev.clevercalc.screens.smart

import android.util.Log
import androidx.lifecycle.ViewModel

class SmartViewModel : ViewModel() {
    fun factorial(x : Float) : Float {
        var result = 1f
        for (i in 1..x.toInt()) {
            result *= i
            Log.d("SmartViewModel", "x = $x, i = $i, result = $result")
        }
        return result

    }
}