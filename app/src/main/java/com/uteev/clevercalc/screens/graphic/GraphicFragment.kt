package com.uteev.clevercalc.screens.graphic

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.uteev.clevercalc.R
import com.uteev.clevercalc.databinding.FragmentGraphicBinding
import com.uteev.clevercalc.screens.data.DataModel

class GraphicFragment : Fragment() {
    private val dataModel: DataModel by activityViewModels()
    lateinit var bindingGraphic : FragmentGraphicBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingGraphic = FragmentGraphicBinding.inflate(inflater)
        return bindingGraphic.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataModel.messageForFragmentGraphic.observe(activity as LifecycleOwner) {
            val listNum : FloatArray? = it
            val lineChart : LineChart = bindingGraphic.lineChart
            lineChart.data = createCircle(listNum!!.toMutableList())
            description(Description(), lineChart)
            scale(lineChart)
        }
    }

    private fun createCircleSettingsColor(lineDataSet: LineDataSet) {
        lineDataSet.setDrawCircles(false) // Не рисовать точки
        lineDataSet.setDrawValues(false) // Не показывать значения точек
    }

    private fun drawOx() : ArrayList<Entry> {
        val entriesX = ArrayList<Entry>()
        for (i in -1000..1000 step 1) {
            val x = i.toFloat()
            val y = 0f
            entriesX.add(Entry(x, y))
        }
        return entriesX
    }

    private fun drawOy() : ArrayList<Entry> {
        val entriesY = ArrayList<Entry>()
        for (i in -1000..1000 step 1) {
            val y = i.toFloat()
            val x = 0f
            entriesY.add(Entry(x, y))
        }
        return entriesY
    }

    private fun calcCircle(
        xo : Float, yo : Float, r : Float,
        entriesUP : ArrayList<Entry>,
        entriesDOWN : ArrayList<Entry>) {
        for (angleDegrees in 0..360) {
            val angleRadians = Math.toRadians(angleDegrees.toDouble())
            val x1 = xo + r * kotlin.math.cos(angleRadians).toFloat()
            val y1 = yo + r * kotlin.math.sin(angleRadians).toFloat()
            val x2 = xo + r * kotlin.math.cos(angleRadians).toFloat()
            val y2 = yo - r * kotlin.math.sin(angleRadians).toFloat()
            entriesDOWN.add(Entry(x1, y1))
            entriesUP.add(Entry(x2, y2))
        }
    }

    private fun createCircle(
        listNum: MutableList<Float>
    ) : LineData {
        val entriesUP1 = ArrayList<Entry>()
        val entriesDOWN1 = ArrayList<Entry>()
        val entriesUP2 = ArrayList<Entry>()
        val entriesDOWN2 = ArrayList<Entry>()

        calcCircle(listNum!!.get(0), listNum!!.get(1), listNum!!.get(2), entriesUP1, entriesDOWN1)

        val dataSetDOWN1 = LineDataSet(entriesDOWN1, "Circle 1")
        val dataSetUP1 = LineDataSet(entriesUP1, "")

        dataSetDOWN1.color = R.color.purple
        createCircleSettingsColor(dataSetDOWN1)

        dataSetUP1.color = Color.MAGENTA
        createCircleSettingsColor(dataSetUP1)


        // Вторая окружность
        calcCircle(listNum!!.get(3), listNum!!.get(4), listNum!!.get(5), entriesUP2, entriesDOWN2)

        val dataSetDOWN2 = LineDataSet(entriesDOWN2, "Circle 2")
        val dataSetUP2 = LineDataSet(entriesUP2, "")

        // Настройка стиля линий
        dataSetDOWN2.color = Color.CYAN
        createCircleSettingsColor(dataSetDOWN2)
        dataSetUP2.color = Color.GREEN
        createCircleSettingsColor(dataSetUP2)

        val dataSetX = LineDataSet(drawOx(), "Ox")
        dataSetX.color = Color.CYAN
        createCircleSettingsColor(dataSetX)

        val dataSetY = LineDataSet(drawOy(), "Oy")
        dataSetX.color = Color.YELLOW
        createCircleSettingsColor(dataSetY)

        val lineData = LineData(dataSetDOWN1, dataSetUP1, dataSetDOWN2, dataSetUP2, dataSetX, dataSetY)
        return lineData
    }

    private fun description(description: Description, lineChart: LineChart) {
        val description = Description()
        description.text = "График окружностей"
        lineChart.description = description
        lineChart.invalidate()
    }

    private fun scale(lineChart: LineChart) {
        // Масштабирование графика
        lineChart.setVisibleXRangeMaximum(10f) // Максимальное количество точек по горизонтальной оси
        lineChart.setVisibleYRangeMaximum(10f, lineChart.axisLeft.axisDependency) // Максимальное количество точек по вертикальной оси
        lineChart.moveViewToX(0f) // Перемещение видимой области к началу графика по горизонтальной оси
        // Переместить видимую область графика к указанной координате по оси Y
        lineChart.moveViewTo(0f, 0f, YAxis.AxisDependency.LEFT)
    }
}