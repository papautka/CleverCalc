package com.uteev.clevercalc.screens.prime

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.uteev.clevercalc.R
import java.lang.Exception

class PrimeFragment: Fragment() {
    private val viewModel : PrimeViewModel by viewModels()

    private lateinit var editnumber : EditText
    private lateinit var checkReverse : CheckBox
    private lateinit var bPrimeAnalyze : Button
    private lateinit var infoResult : TextView

    private fun initView(viewPrime : View) {
        editnumber = viewPrime.findViewById(R.id.editnumber)
        checkReverse =  viewPrime.findViewById(R.id.checkReverse)
        bPrimeAnalyze =  viewPrime.findViewById(R.id.bPrimeAnalyze)
        infoResult =  viewPrime.findViewById(R.id.infoResultPrime)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewPrime = inflater.inflate(R.layout.fragment_prime, container, false)
//        initView(viewPrime)
        editnumber = viewPrime.findViewById(R.id.editnumber)
        checkReverse =  viewPrime.findViewById(R.id.checkReverse)
        bPrimeAnalyze =  viewPrime.findViewById(R.id.bPrimeAnalyze)
        infoResult =  viewPrime.findViewById(R.id.infoResultPrime)

        bPrimeAnalyze.setOnClickListener {
            checkPrime()
        }
        return viewPrime
    }

    private fun checkPrime() {
        if(editnumber.text.isEmpty()) {
//            Toast.makeText(this, "Введите целое число!", Toast.LENGTH_SHORT).show()
        } else {
            if (checkRangeInteger() == 0) {
//                Toast.makeText(this, "Число в недопустимом диапозоне!", Toast.LENGTH_SHORT).show()
            } else {
                var strTmp = editnumber.text.toString()
                if (checkReverse.isChecked) {
                    strTmp = strTmp.reversed()
                }
                calcPrime(strTmp)
            }
        }
    }



    private fun calcPrime(strTmp: String) {
        var number: Int = strTmp.toString().toInt()
        var numbers =  mutableListOf<Int>()

        if(number < 10) {
            if(primaryNumber(number)) {
                infoResult.setText("${number} - prime")
            } else {
                infoResult.setText("${number} - no prime" + "\n ${arithCreate(number)}")
            }
        } else {
            numbers = digitCapacity(number)
            for(i in numbers.indices) {
                if(primaryNumber(numbers[i])) {
                    infoResult.setText("${numbers[i]} - prime")
                } else {
                    infoResult.setText("${numbers[i]} - no prime" + "\n ${arithCreate(numbers[i])}")
                }
            }
        }
    }

    private fun digitCapacity(number : Int) : MutableList<Int> {
        val residues_from_division = mutableListOf<Int>()
        val residues_from_division_all = mutableListOf<Int>()
        val ITOGO = mutableListOf<Int>()
        var divider = 10
        var tmp_number = number
        if (number >= divider) {
            while (tmp_number >= 10) {
                residues_from_division.add(tmp_number)
                tmp_number = tmp_number / divider
                if (tmp_number < 10) {
                    residues_from_division.add(tmp_number)
                }
            }
        }
        for (number_tmp in residues_from_division.reversed()) {
            residues_from_division_all.add(number_tmp)
        }
        return residues_from_division_all
    }

    private fun primaryNumber(number : Int) : Boolean {
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

    private fun arithCreate(num: Int): String {
        var numMassive = mutableListOf<Int>()
        var deletel = 2
        var tmpNum = num

        while (tmpNum > 1) {
            while (tmpNum % deletel == 0) {
                numMassive.add(deletel)
                tmpNum /= deletel
            }
            deletel++
        }

        val strResult: String = numMassive.joinToString(separator = " ")
        return strResult
    }

    private fun checkRangeInteger() : Int {
        var res = 0
        try {
            res = editnumber.text.toString().toInt()
        } catch (e : Exception) {
            Log.d("checkRangeInteger", "Число вышло из диапозона целых")
        }
        return res
    }
}