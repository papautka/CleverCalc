package com.uteev.clevercalc.screens.smart

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uteev.clevercalc.databinding.FragmentSmartBinding

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
    }
}