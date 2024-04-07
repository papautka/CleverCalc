package com.uteev.clevercalc.screens.therm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uteev.clevercalc.databinding.FragmentCircleBinding
import com.uteev.clevercalc.databinding.FragmentThermBinding

class ThermFragment : Fragment() {
    lateinit var binding_therm : FragmentThermBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_therm = FragmentThermBinding.inflate(inflater)
        return binding_therm.root
    }
}