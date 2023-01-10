package com.tianji.ttech.ui.station.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.tianji.ttech.databinding.FragmentHomePlantListBinding
import com.tianji.ttech.ui.home.viewmodel.PlantFilterViewModel
import com.tianji.ttech.ui.station.activity.AddPlantActivity
import com.tianji.ttech.ui.station.activity.AddTtchPlantActivity
import com.tianji.ttech.ui.station.viewmodel.PlantFilterModel

class StationFragment :Fragment(),OnClickListener{

    private lateinit var _binding: FragmentHomePlantListBinding

    private val filterViewModel: PlantFilterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePlantListBinding.inflate(inflater, container, false)
        initData()
        initLisenter()
        return _binding.root
    }

    private fun initLisenter() {
        _binding.title.setOnRightImageClickListener {
            AddTtchPlantActivity.start(requireContext())
        }

    }


    private fun initData() {
        filterViewModel.setFilterType(PlantFilterModel.getDefaultFilter().filterType)

        _binding.ivSearch.setOnClickListener {
            val plantName = _binding.tvSearch.text.toString()
            filterViewModel.setSearchWord(plantName)
        }
    }

    override fun onClick(v: View?) {


    }
}