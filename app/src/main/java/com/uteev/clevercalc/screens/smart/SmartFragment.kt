package com.uteev.clevercalc.screens.smart

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.uteev.clevercalc.databinding.FragmentSmartBinding
import kotlinx.coroutines.Job
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
                if (checkEmptyInput()) {
                    val factorialJob = factorial()
                    factorialJob.join()
                }
            }
        }
        b_s.bSqrt.setOnClickListener {
            lifecycleScope.launch {
                if (checkEmptyInput()) {
                    val sqrtJob = sqrt()
                    sqrtJob.join()
                }
            }
        }
        b_s.bLog.setOnClickListener {
            lifecycleScope.launch {
                if (checkEmptyInput()) {
                    val logJob = log()
                    logJob.join()
                }
            }
        }
        b_s.bmult.setOnClickListener {
            lifecycleScope.launch {
                if (checkEmptyInput()) {
                    val multJob = mult()
                    multJob.join()
                }
            }
        }
        b_s.bAllStart.setOnClickListener {
            lifecycleScope.launch {
                if (checkEmptyInput()) {
                    val factorialJob = factorial()
                    factorialJob.join()
                    val sqrtJob = sqrt()
                    sqrtJob.join()
                    val logJob = log()
                    log().join()
                    val multJob = mult()
                    mult().join()
                }
            }
        }
    }

    private fun factorial() : Job{
        return lifecycleScope.launch {
            b_s.bFactorial.setText("STOP")
            val x = b_s.inputX.text.toString().toFloat()
            val result = viewModel.factorial(x)
            delay(500L)
            b_s.xFactorial.setText("x! = ${result}")
            b_s.bFactorial.setText("RUN")
        }
    }

    private fun sqrt() : Job {
        return lifecycleScope.launch {
            b_s.bSqrt.setText("STOP")
            b_s.progressBar.visibility = View.VISIBLE
            val x = b_s.inputX.text.toString().toFloat()
            val result1 = String.format("%.3f", sqrt(x))
            val result2 = String.format("%.3f", cbrt(x))
            delay(500L)
            b_s.xSqrt.setText("x^(1/2) = ${result1}")
            b_s.xSqrt3.setText("x^(1/3) = ${result2}")
            b_s.bSqrt.setText("RUN")
            b_s.progressBar.visibility = View.INVISIBLE
        }
    }

    private suspend fun log() : Job {
        return lifecycleScope.launch {
            b_s.bLog.setText("STOP")
            b_s.progressBar.visibility = View.VISIBLE
            val x = b_s.inputX.text.toString().toFloat()
            if(x>0) {
                val result = Math.log10(x.toDouble()).toFloat()
                delay(500L)
                b_s.xlog.setText("log(x) = ${String.format("%.3f", result)}")
                b_s.xln.setText("ln(x) = ${String.format("%.3f", Math.log(x.toDouble()))}")
            } else {
                b_s.inputX.setText("Input x > 0!")
                delay(1000L)
                b_s.inputX.setText("")
            }
            b_s.bLog.setText("RUN")
            b_s.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun mult() : Job {
        return lifecycleScope.launch {
            b_s.bmult.setText("STOP")
            b_s.progressBar.visibility = View.VISIBLE
            val x = b_s.inputX.text.toString().toFloat()
            val result = x * x
            delay(500L)
            b_s.xmult2.setText("x^2 = ${result}")
            b_s.xmult3.setText("x^3 = ${x * x * x}")
            b_s.bmult.setText("RUN")
            b_s.progressBar.visibility = View.INVISIBLE
        }

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