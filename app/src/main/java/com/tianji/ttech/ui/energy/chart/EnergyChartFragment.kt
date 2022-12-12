package com.tianji.ttech.ui.energy.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.databinding.FragmentEnergyChartBinding

class EnergyChartFragment : BaseFragment() {

    private lateinit var _binding: FragmentEnergyChartBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEnergyChartBinding.inflate(inflater, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }








}