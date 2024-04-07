package com.uteev.clevercalc.screens.smart

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlin.math.cbrt
import kotlin.math.sqrt

class SmartViewModel : ViewModel() {
    fun factorial(x : Float) : Float {
        var result = 1f
        if (x >= 35) {
            return 0f
        }
        for (i in 1..x.toInt()) {
            result *= i
            Log.d("SmartViewModel", "x = $x, i = $i, result = $result")
        }
        return result
    }
    fun calcSquareRoot(x : Float) : Float {
        return sqrt(x)
    }

    fun calcCubeRoot(x : Float) : Float {
        return cbrt(x)
    }

}