package com.uteev.clevercalc.screens.graphic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.uteev.clevercalc.R
import com.uteev.clevercalc.screens.circle.CircleViewModel

class GraphicFragment : Fragment() {
    private val viewModel : GraphicViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewGraphic = inflater.inflate(R.layout.fragment_graphic, container, false)
        return  viewGraphic
    }


}