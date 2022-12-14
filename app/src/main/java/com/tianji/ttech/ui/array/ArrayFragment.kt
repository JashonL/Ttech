package com.tianji.ttech.ui.array

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.databinding.FragmentArrayBinding
import com.tianji.ttech.databinding.FragmentEnergyBinding

class ArrayFragment : BaseFragment() {

    private lateinit var _binding: FragmentArrayBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArrayBinding.inflate(layoutInflater)
        return _binding.root
    }


}