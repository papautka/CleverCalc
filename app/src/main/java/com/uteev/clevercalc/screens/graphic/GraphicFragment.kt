package com.uteev.clevercalc.screens.graphic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.uteev.clevercalc.databinding.FragmentGraphicBinding
import com.uteev.clevercalc.screens.data.DataModel

class GraphicFragment : Fragment() {
    private val dataModel: DataModel by activityViewModels()
    lateinit var binding_fragment : FragmentGraphicBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_fragment = FragmentGraphicBinding.inflate(inflater)
        return binding_fragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataModel.messageForFragmentGraphic.observe(activity as LifecycleOwner) {
            binding_fragment.textView3.text = it
        }
    }
}