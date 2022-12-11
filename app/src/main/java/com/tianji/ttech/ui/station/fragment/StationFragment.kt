package com.tianji.ttech.ui.station.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.tianji.ttech.databinding.FragmentHomePlantListBinding
import com.tianji.ttech.ui.home.viewmodel.PlantFilterViewModel
import com.tianji.ttech.ui.station.viewmodel.PlantFilterModel

class StationFragment :Fragment(){

    private lateinit var _binding: FragmentHomePlantListBinding

    private val filterViewModel: PlantFilterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePlantListBinding.inflate(inflater, container, false)
        initData()

        return _binding.root
    }



    private fun initData() {
        filterViewModel.setFilterType(PlantFilterModel.getDefaultFilter().filterType)
    }
}