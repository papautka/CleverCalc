package com.uteev.clevercalc.screens.circle

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.uteev.clevercalc.R
import com.uteev.clevercalc.databinding.FragmentCircleBinding
import com.uteev.clevercalc.screens.data.DataModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class CircleFragment : Fragment(){

    private val dataModel: DataModel by activityViewModels()
    lateinit var binding_circle : FragmentCircleBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_circle = FragmentCircleBinding.inflate(inflater)
        return binding_circle.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bCreateGraphic = view.findViewById<Button>(R.id.bCreateGraph)
        val controller = findNavController()
        binding_circle.bCreateGraph.setOnClickListener {
            dataModel.messageForFragmentGraphic.value = "Hello fragment graphic"
            controller.navigate(R.id.graphicFragment)
        }

        binding_circle.bCircleAnalyze.setOnClickListener {
            lifecycleScope.launch {
                calcCircle()
            }
        }
    }

    private suspend fun calcCircle() {
        val listNum = returnMasNumber()
        if(listNum.isEmpty()) {
            binding_circle.infoResult.setText("Корректно заполните все ячейки!")
        } else {
            binding_circle.progBar.visibility = View.VISIBLE
            delay(3000)
            checkCircle(listNum)
            binding_circle.progBar.visibility = View.INVISIBLE
        }
    }

    private fun checkCircle(n: MutableList<Float>) {
        val dist_betw_p = sqrt((n[0]-n[3])*(n[0]-n[3]) + (n[1]-n[4])*(n[1]-n[4]))
        val strResult = checkIntersect(dist_betw_p, n)
        binding_circle.infoResult.setText(String.format(strResult))
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
            listNum.add(binding_circle.editxc1.text.toString().toFloat())
            listNum.add(binding_circle.edityc1.text.toString().toFloat())
            listNum.add(binding_circle.editrc1.text.toString().toFloat())
            listNum.add(binding_circle.editxc2.text.toString().toFloat())
            listNum.add(binding_circle.edityc2.text.toString().toFloat())
            listNum.add(binding_circle.editrc2.text.toString().toFloat())
        } catch (e : NumberFormatException) {
            Log.d("${R.string.ca_name_fun_returnMasNumber}", "${R.string.ca_no_str_to_float}")
            listNum.clear()
        }
        return listNum
    }
}