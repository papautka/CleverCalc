package com.uteev.clevercalc.screens.therm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.uteev.clevercalc.databinding.FragmentCircleBinding
import com.uteev.clevercalc.databinding.FragmentThermBinding
import com.uteev.clevercalc.screens.prime.PrimeViewModel
import java.math.RoundingMode

class ThermFragment : Fragment() {
    private val viewModel : ThermViewModel by viewModels()
    lateinit var bT: FragmentThermBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bT.bThermAnalyze.setOnClickListener {
            calcTemp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bT = FragmentThermBinding.inflate(inflater)
        return bT.root
    }



    private fun calcTemp() {
        var tmpTemp = bT.taEnteraTempEdit.text.toString()
        var tmpHum = bT.taEnterHumEdit.text.toString()
        if(tmpTemp.isEmpty() || tmpHum.isEmpty()) {
            bT.infoResultTherm.setText("Некорректно заполнены ячейки!")
        } else {
            val mode = temperatureMeasurementScale(bT.spinnerMode)
            val season = seasonMeasurementScale(bT.spinnerMode)
            var tempNow = 0f
            var humid = 0
            try {
                tempNow = bT.taEnteraTempEdit.text.toString().toFloat()
                humid = bT.taEnterHumEdit.text.toString().toInt()
                Log.d("calcTemp", "$mode" + "$season")
                checkRangeMinMaxTemp(season, mode, tempNow)
                val str = printAnalyTemp(checkRangeMinMaxTemp(season, mode, tempNow), mode, humid)
                bT.infoResultTherm.setText(String.format(str))
            } catch (e : Exception) {
                bT.infoResultTherm.setText("Некорректно заполнена температура!")
            }
        }
    }

    private fun temperatureMeasurementScale(spinner: Spinner) : Int {
        var tempScale = 1 // "Kelvin"
        var spinner = bT.spinnerMode
        var strScale = spinner.selectedItem.toString()
        when(strScale) {
            "Kelvin" -> tempScale = 1
            "Celsius" -> tempScale = 2
            "Fahrenheit" -> tempScale = 3
            else -> tempScale = 2
        }
        return tempScale
    }

    private fun seasonMeasurementScale(spinner: Spinner) : Int {
        var seasonScale = 1 // "Winter"
        var spinner = bT.spinnerSeason
        var strScale = spinner.selectedItem.toString()
        when(strScale) {
            "Winter" -> seasonScale = 1
            "Summer" -> seasonScale = 2
            else -> seasonScale = 2
        }
        return seasonScale
    }

    private fun checkRangeMinMaxTemp(resultSeason : Int, tempScale: Int, tempNow : Float) : MutableList<Float> {
        val rMin_rMax_temp = mutableListOf<Float>()
        var rangeMax = 25f
        var rangeMin = 22f
        if(resultSeason == 1) {
            rangeMax = 22f
            rangeMin = 20f
        }
        var tempTmp = tempNow
        when(tempScale) {
            1-> {
                rangeMax = rangeMax + 273.15f
                rangeMin = rangeMin + 273.15f
                tempTmp  = tempNow + 273.15f
            }
            3-> {
                rangeMax = 1.8f * rangeMax + 32f
                rangeMin = 1.8f * rangeMin + 32f
                tempTmp  = 1.8f * tempNow + 32f
            }
        }
        rMin_rMax_temp.add(rangeMin)
        rMin_rMax_temp.add(rangeMax)
        rMin_rMax_temp.add(tempTmp)
        return rMin_rMax_temp
    }

    private fun printAnalyTemp(rMin_rMax_temp: MutableList<Float>, tempScale: Int, humidity : Int): String {
        var deltaTemp = 0f
        var rangeMin = rMin_rMax_temp[0]
        var rangeMax = rMin_rMax_temp[1]
        var tempNow = rMin_rMax_temp[2]
        val result = StringBuilder()

        if (tempScale == 1) {
            result.append("The temperature is $tempNow ˚K\n")
            if (!(tempNow >= rangeMin && tempNow <= rangeMax)) {
                result.append("The comfortable temperature is from $rangeMin to $rangeMax ˚K.\n")
            }
        } else if (tempScale == 3) {
            val roundTempNow = tempNow.toBigDecimal().setScale(2, RoundingMode.UP).toFloat()
            result.append("The temperature is $roundTempNow ˚F\n")
            if (!(tempNow >= rangeMin && tempNow <= rangeMax)) {
                result.append("The comfortable temperature is from $rangeMin to $rangeMax ˚F.\n")
            }
        } else {
            result.append("The temperature is $tempNow ˚C\n")
            if (!(tempNow >= rangeMin && tempNow <= rangeMax)) {
                result.append("The comfortable temperature is from $rangeMin to $rangeMax ˚C.\n")
            }
        }
        if (rangeMin > tempNow) {
            deltaTemp = rangeMin - tempNow
            deltaTemp = deltaTemp.toBigDecimal().setScale(2, RoundingMode.UP).toFloat()
            result.append("Please, make it warmer by $deltaTemp degrees.\n")
        } else if (tempNow > rangeMax) {
            deltaTemp = tempNow - rangeMax
            deltaTemp = deltaTemp.toBigDecimal().setScale(2, RoundingMode.UP).toFloat()
            result.append("Please, make it colder by $deltaTemp degrees.\n")
        } else {
            result.append("The temperature is comfortable\n")
        }
        Thermohydrometer(tempScale, humidity, result)
        return result.toString()
    }

    private fun Thermohydrometer(resultSeason : Int, humidity : Int, result : StringBuilder) {
        var humidityMax = 60
        var humidityMin = 30
        if(resultSeason == 1) {
            result.append("The comfortable humidity is from 30%-45%\n")
            humidityMax = 45
        } else {
            result.append("The comfortable humidity is from 30%-60%\n")
        }
        if(humidity >= humidityMin && humidity <= humidityMax) {
            result.append("The humidity is comfortable\n")
        } else {
            result.append("The humidity is not comfortable\n")
        }

    }

}