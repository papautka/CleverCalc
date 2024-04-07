package com.uteev.clevercalc.screens.prime

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.uteev.clevercalc.databinding.FragmentPrimeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class PrimeFragment: Fragment() {
    private val viewModel : PrimeViewModel by viewModels()
    lateinit var b_p : FragmentPrimeBinding
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b_p = FragmentPrimeBinding.inflate(inflater)
        return b_p.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b_p.bPrimeAnalyze.setOnClickListener {
            checkPrime()
        }
    }

    private fun checkPrime() {
        if(b_p.editnumber.text.isEmpty()) {
//            Toast.makeText(this, "Введите целое число!", Toast.LENGTH_SHORT).show()
            b_p.infoResultPrime.text = "Введите целое число!"
        } else {
            if (checkRangeInteger() == 0) {
//                Toast.makeText(this, "Число в недопустимом диапозоне!", Toast.LENGTH_SHORT).show()
                b_p.infoResultPrime.text = "Число в недопустимом диапозоне!"
            } else {
                var strTmp = b_p.editnumber.text.toString()
                if (b_p.checkReverse.isChecked) {
                    strTmp = strTmp.reversed()
                }
                lifecycleScope.launch {
                    calcPrime(strTmp)
                }
            }
        }
    }



    private suspend fun calcPrime(strTmp: String) {
        b_p.progBar.visibility = View.VISIBLE
        delay(3000)
        b_p.progBar.visibility = View.INVISIBLE
        var number: Int = strTmp.toString().toInt()
        var numbers =  mutableListOf<Int>()

        if(number < 10) {
            if(viewModel.primaryNumber(number)) {
                b_p.infoResultPrime.setText("${number} - prime")
            } else {
                b_p.infoResultPrime.setText("${number} - no prime" + "\n ${arithCreate(number)}")
            }
        } else {
            numbers = digitCapacity(number)
            for(i in numbers.indices) {
                if(viewModel.primaryNumber(numbers[i])) {
                    b_p.infoResultPrime.setText("${numbers[i]} - prime")
                } else {
                    b_p.infoResultPrime.setText("${numbers[i]} - no prime" + "\n ${arithCreate(numbers[i])}")
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
            res = b_p.editnumber.text.toString().toInt()
        } catch (e : Exception) {
            Log.d("checkRangeInteger", "Число вышло из диапозона целых")
        }
        return res
    }
}