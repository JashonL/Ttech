package com.tianji.ttech.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.databinding.FragmentHomeBinding
import com.tianji.ttech.ui.common.viewmodel.SelectAreaViewModel
import com.tianji.ttech.ui.station.StationViewModel
import com.tianji.ttech.view.dialog.OptionsDialog
import com.tianji.ttech.view.pop.ListPopuwindow
import com.tianji.ttech.view.popuwindow.ListPopModel
import com.ttech.lib.util.ToastUtil

class HomeFragment : BaseFragment(), OnClickListener {


    private lateinit var _binding: FragmentHomeBinding


    private val viewModel: StationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initData()
        setliseners()
        return _binding.root
    }

    private fun setliseners() {
        _binding.header.tvTitle.setOnClickListener(this)
        _binding.srlRefresh.setOnRefreshListener {
            fetchPlantList()
        }

    }


    private fun initData() {
        viewModel.getPlantListLiveData.observe(viewLifecycleOwner) {
            if (it.first) {
                val second = it.second
                if (second == null || second.isEmpty()) {//没有电站  显示空
                    _binding.header.tvTitle.text = ""

                }
            }
        }
        fetchPlantList()
    }


    private fun fetchPlantList() {
        showDialog()
        viewModel.getPlantList()
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onClick(p0: View?) {
        when {
            p0 === _binding.header.tvTitle -> {
                showPlantList()
            }


        }

    }


    private fun showPlantList() {
        val second = viewModel.getPlantListLiveData.value?.second
        if (second == null || second.isEmpty()) {
            fetchPlantList()
        } else {
            val options = mutableListOf<ListPopModel>()
            for (plant in second) {
                options.add(ListPopModel(plant.stationName, false))
            }
            context?.let { ListPopuwindow.showPop(it, options, _binding.header.tvTitle) }
        }
    }


}