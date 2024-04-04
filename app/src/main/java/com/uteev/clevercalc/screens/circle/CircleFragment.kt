package com.uteev.clevercalc.screens.circle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.uteev.clevercalc.R
import com.uteev.clevercalc.screens.graphic.GraphicFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class CircleFragment : Fragment(){
    private val viewModel : CircleViewModel by viewModels()

    private lateinit var xc1 : EditText
    private lateinit var yc1 : EditText
    private lateinit var rc1 : EditText
    private lateinit var xc2 : EditText
    private lateinit var yc2 : EditText
    private lateinit var rc2 : EditText
    private lateinit var bCircleAnalyze : Button
    private lateinit var bCreateGraphic : Button
    private lateinit var infoResult : TextView
    private lateinit var prog_bar : ProgressBar



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewCircle = inflater.inflate(R.layout.fragment_circle, container, false)
        xc1 = viewCircle.findViewById(R.id.editxc1)
        yc1 = viewCircle.findViewById(R.id.edityc1)
        rc1 = viewCircle.findViewById(R.id.editrc1)
        xc2 = viewCircle.findViewById(R.id.editxc2)
        yc2 = viewCircle.findViewById(R.id.edityc2)
        rc2 = viewCircle.findViewById(R.id.editrc2)

        bCircleAnalyze = viewCircle.findViewById(R.id.bCircleAnalyze)
        bCreateGraphic = viewCircle.findViewById(R.id.bCreateGraph)
        infoResult = viewCircle.findViewById(R.id.infoResult)
        prog_bar = viewCircle.findViewById(R.id.prog_bar)

        bCircleAnalyze.setOnClickListener {
            lifecycleScope.launch {
                calcCircle()
            }
        }
        return viewCircle
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bCreateGraphic = view.findViewById<Button>(R.id.bCreateGraph)
        val controller = findNavController()
        bCreateGraphic.setOnClickListener {
            viewModel.inputStringLiveData.value = "ХуЙ!"
            controller.navigate(R.id.graphicFragment)
        }
    }

    private suspend fun calcCircle() {
        val listNum = returnMasNumber()
        if(listNum.isEmpty()) {
            infoResult.setText("Корректно заполните все ячейки!")
        } else {
            prog_bar.visibility = View.VISIBLE
            delay(3000)
            checkCircle(listNum)
            prog_bar.visibility = View.INVISIBLE
        }
    }

    private fun checkCircle(n: MutableList<Float>) {
        val dist_betw_p = sqrt((n[0]-n[3])*(n[0]-n[3]) + (n[1]-n[4])*(n[1]-n[4]))
        val strResult = checkIntersect(dist_betw_p, n)
        infoResult.setText(String.format(strResult))
    }

    fun checkTouched(n: MutableList<Float>): String {
        val x1 = n[0]
        val y1 = n[1]
        val r1 = n[2]
        val x2 = n[3]
        val y2 = n[4]
        val r2 = n[5]

        val distance = sqrt((x2 - x1).pow(2) + (y2 - y1).pow(2))
        return " (${x1 + (r1 * (x2 - x1)) / distance}; ${y1 + (r1 * (y2 - y1)) / distance})"
    }

    data class Point(val x: Float, val y: Float)
    fun checkIntersect(dist_betw_p: Float, n: MutableList<Float>): String {
        val center1 = Point(n[0], n[1])
        val center2 = Point(n[3], n[4])
        val radius1 = n[2]
        val radius2 = n[5]

        val distanceBetweenCenters = sqrt((center1.x - center2.x).pow(2) + (center1.y - center2.y).pow(2))

        if (distanceBetweenCenters > radius1 + radius2) {
            return "Окружности не пересекаются"
        } else if (distanceBetweenCenters + minOf(radius1, radius2) < maxOf(radius1, radius2)) {
            return "Одна окружность находится внутри другой"
        } else if (distanceBetweenCenters == radius1 + radius2) {
            return "Окружности касаются внешне в точке : ${checkTouched(n)}"
        } else if (distanceBetweenCenters == abs(radius1 - radius2)) {
            return "Окружности касаются внутренне"
        } else {
            // Находим точки пересечения
            val d = distanceBetweenCenters
            val a = (radius1.pow(2) - radius2.pow(2) + d.pow(2)) / (2 * d)
            val h = sqrt(radius1.pow(2) - a.pow(2))
            val x3 = center1.x + a * (center2.x - center1.x) / d
            val y3 = center1.y + a * (center2.y - center1.y) / d
            val intersect1 = Point(x3 + h * (center2.y - center1.y) / d, y3 - h * (center2.x - center1.x) / d)
            val intersect2 = Point(x3 - h * (center2.y - center1.y) / d, y3 + h * (center2.x - center1.x) / d)

            return "Окружности пересекаются в двух точках:\n" +
                    "1-я точка: x1 = ${"%.2f".format(intersect1.x)} y1 = ${"%.2f".format(intersect1.y)}\n" +
                    "2-я точка: x2 = ${"%.2f".format(intersect2.x)} y2 = ${"%.2f".format(intersect2.y)}"
        }
    }


    private fun returnMasNumber() : MutableList<Float> {
        val listNum : MutableList<Float> = mutableListOf()
        try {
            listNum.add(xc1.text.toString().toFloat())
            listNum.add(yc1.text.toString().toFloat())
            listNum.add(rc1.text.toString().toFloat())
            listNum.add(xc2.text.toString().toFloat())
            listNum.add(yc2.text.toString().toFloat())
            listNum.add(rc2.text.toString().toFloat())
        } catch (e : NumberFormatException) {
            Log.d("${R.string.ca_name_fun_returnMasNumber}", "${R.string.ca_no_str_to_float}")
            listNum.clear()
        }
        return listNum
    }
}