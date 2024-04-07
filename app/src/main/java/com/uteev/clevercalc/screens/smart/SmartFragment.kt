package com.uteev.clevercalc.screens.smart

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.uteev.clevercalc.databinding.FragmentSmartBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cbrt
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.sqrt

class SmartFragment : Fragment() {
    private val viewModel : SmartViewModel by viewModels()
    lateinit var b_s : FragmentSmartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b_s = FragmentSmartBinding.inflate(inflater)
        return  b_s.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b_s.bFactorial.setOnClickListener {
            lifecycleScope.launch {
                if (checkEmptyInput()) factorial()
            }
        }
        b_s.bSqrt.setOnClickListener {
            lifecycleScope.launch {
                if (checkEmptyInput()) sqrt()
            }
        }
        b_s.bLog.setOnClickListener {
            lifecycleScope.launch {
                if (checkEmptyInput()) log()
            }
        }
        b_s.bmult.setOnClickListener {
            lifecycleScope.launch {
                if (checkEmptyInput()) mult()
            }
        }
        b_s.bAllStart.setOnClickListener {
            lifecycleScope.launch {
                if (checkEmptyInput()) {
                    factorial()
                    sqrt()
                    mult()
                    log()
                }
            }
        }
    }

    private fun factorial() {
        val x = b_s.inputX.text.toString().toFloat()
        val result = viewModel.factorial(x)
        b_s.xFactorial.setText("x! = ${result}")
    }

    private fun sqrt() {
        val x = b_s.inputX.text.toString().toFloat()
        val result1 = String.format("%.3f", sqrt(x))
        val result2 = String.format("%.3f", cbrt(x))
        b_s.xSqrt.setText("x^(1/2) = ${result1}")
        b_s.xSqrt3.setText("x^(1/3) = ${result2}")
    }

    private suspend fun log() {
        val x = b_s.inputX.text.toString().toFloat()
        if(x>0) {
            val result = Math.log(x.toDouble()).toFloat()
            b_s.xlog.setText("log(x) = ${String.format("%.3f", result)}")
            b_s.xln.setText("ln(x) = ${String.format("%.3f", Math.log(x.toDouble()))}")
        } else {
            b_s.inputX.setText("Input x > 0!")
            delay(1000L)
            b_s.inputX.setText("")
        }
    }

    private fun mult() {
        val x = b_s.inputX.text.toString().toFloat()
        val result = x * x
        b_s.xmult2.setText("x^2 = ${result}")
        b_s.xmult3.setText("x^3 = ${x * x * x}")
    }

    private suspend  fun checkEmptyInput() : Boolean {
        if (b_s.inputX.text.isEmpty()) {
            b_s.inputX.setText("Input x!")
            delay(1000L)
            b_s.inputX.setText("")
            return false
        }
        return true
    }
}