package com.uteev.clevercalc.screens.smart

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.uteev.clevercalc.databinding.FragmentSmartBinding
import com.uteev.clevercalc.screens.prime.PrimeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cbrt
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.sqrt

class SmartFragment : Fragment() {
    private val viewModel : SmartViewModel by viewModels()
    private val viewPrimeModel : PrimeViewModel by viewModels()
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
                    val primatyJob = primaryNumber()
                    primatyJob.join()
                }
            }
        }
        b_s.bSimple.setOnClickListener {
            lifecycleScope.launch {
                if (checkEmptyInput()) {
                    val primatyJob = primaryNumber()
                    primatyJob.join()
                }
            }
        }
    }

    private fun primaryNumber() : Job{
        return lifecycleScope.launch {
            b_s.bSimple.setText("STOP")
            delay(500L)
            val x = b_s.inputX.text.toString().toInt()
            val simpleJob = viewPrimeModel.primaryNumber(x)
            if (simpleJob) {
                b_s.tSimple.setText("${x} - prime")
            } else {
                b_s.tSimple.setText("${x} - no prime")
            }
            b_s.bSimple.setText("RUN")
        }
    }

    private fun factorial() : Job{
        return lifecycleScope.launch {
            b_s.bFactorial.setText("STOP")
            val x = b_s.inputX.text.toString().toFloat()
            val result = viewModel.factorial(x)
            delay(500L)
            if (result == 0f) {
                b_s.xFactorial.setText("x! = ERROR")
                b_s.bFactorial.setText("RUN")
            } else {
                b_s.xFactorial.setText("x! = ${result}")
                b_s.bFactorial.setText("RUN")
            }
        }
    }

    private fun sqrt() : Job {
        return lifecycleScope.launch {
            b_s.bSqrt.setText("STOP")
            b_s.progressBar.visibility = View.VISIBLE
            val x = b_s.inputX.text.toString().toFloat()

            // async and await with coroutines for multithreading
            val squareRootDeferred = async { viewModel.calcSquareRoot(x) }
            val cubeRootDeferred = async { viewModel.calcCubeRoot(x) }

            val squareRootResult = squareRootDeferred.await()
            val cubeRootResult = cubeRootDeferred.await()

            val result1 = String.format("%.3f", squareRootResult)
            val result2 = String.format("%.3f", cubeRootResult)

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
                val resLog10 = async { Math.log10(x.toDouble()).toFloat() }.await()
                val resLn = async {  Math.log(x.toDouble()).toFloat() }.await()

                delay(500L)
                b_s.xlog.setText("log(x) = ${String.format("%.3f", resLog10)}")
                b_s.xln.setText("ln(x) = ${String.format("%.3f", resLn)}")
            } else {
                b_s.inputX.setHint("Input x > 0!")
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
            val res1 = async { x*x }.await()
            val res2 = async { x*x*x }.await()
            delay(500L)
            b_s.xmult2.setText("x^2 = ${res1}")
            b_s.xmult3.setText("x^3 = ${res2}")
            b_s.bmult.setText("RUN")
            b_s.progressBar.visibility = View.INVISIBLE
        }

    }

    private suspend  fun checkEmptyInput() : Boolean {
        if (b_s.inputX.text.isEmpty()) {
            b_s.inputX.setHint("Input x!")
            delay(1000L)
            return false
        }
        return true
    }
}