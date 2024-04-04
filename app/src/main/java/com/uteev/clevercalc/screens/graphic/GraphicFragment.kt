package com.uteev.clevercalc.screens.graphic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.uteev.clevercalc.R
import com.uteev.clevercalc.screens.circle.CircleViewModel

class GraphicFragment : Fragment() {
    private val viewModel : CircleViewModel by viewModels()
    private var inputStr : String? = null
    private lateinit var textView3 : TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewGraphic = inflater.inflate(R.layout.fragment_graphic, container, false)
        textView3 = viewGraphic.findViewById(R.id.textView3)

        viewModel.inputStringLiveData.observe(viewLifecycleOwner, Observer { input ->
            inputStr = input
            textView3.text = inputStr
        })

        return  viewGraphic
    }

}