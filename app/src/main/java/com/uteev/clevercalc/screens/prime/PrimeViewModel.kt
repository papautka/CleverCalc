package com.uteev.clevercalc.screens.prime

import androidx.lifecycle.ViewModel

class PrimeViewModel : ViewModel() {
    fun primaryNumber(number : Int) : Boolean {
        var is_primary_number = true
        var divider = 2
        var count = 1
        if(number == 1 || number == 0) {
            is_primary_number = false
        } else {
            while(count != 2 || divider > number) {
                if(number % divider == 0) {
                    if(number == divider) break
                    is_primary_number = false
                    count++
                } else {
                    divider++
                }
            }
        }
        return is_primary_number
    }
}