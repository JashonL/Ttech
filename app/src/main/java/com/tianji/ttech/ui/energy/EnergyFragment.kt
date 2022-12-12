package com.tianji.ttech.ui.energy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.tianji.ttech.R
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.databinding.FragmentEnergyBinding
import com.tianji.ttech.model.StationModel
import com.tianji.ttech.ui.station.viewmodel.StationViewModel
import com.tianji.ttech.view.pop.ListPopuwindow
import com.tianji.ttech.view.popuwindow.ListPopModel
import com.ttech.lib.util.gone
import com.ttech.lib.util.visible

class EnergyFragment : BaseFragment(), View.OnClickListener {

    private lateinit var _binding: FragmentEnergyBinding

    private val viewModel: StationViewModel by viewModels()

    private val energyViewModel: EnergyViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnergyBinding.inflate(inflater, container, false)
        setliseners()
        initData()
        return _binding.root
    }


    private fun initData() {
        viewModel.getPlantListLiveData.observe(viewLifecycleOwner) {
            _binding.srlEmptyview.finishRefresh()
            if (it.first) {
                val second = it.second
                if (second == null || second.isEmpty()) {//没有电站  显示空
                    _binding.header.tvTitle.text = ""

                    _binding.srlRefresh.visible()
                    _binding.srlEmptyview.gone()


                } else {

                    _binding.srlRefresh.visible()
                    _binding.srlEmptyview.gone()

                    //默认选中第一个电站
                    _binding.header.tvTitle.text = second[0].stationName
                    //显示系统图
                    showEnergyChart(second[0])
                }
            }
        }
        fetchPlantList()
    }


    private fun setliseners() {
        _binding.header.tvTitle.setOnClickListener(this)
        _binding.date.tvDateType.setOnClickListener(this)
        _binding.srlRefresh.setOnRefreshListener {
            fetchPlantList()
        }
    }


    private fun fetchPlantList() {
        showDialog()
        viewModel.getPlantList()
    }

    override fun onClick(p0: View?) {
        when {
            p0 === _binding.header.tvTitle -> {
                showPlantList()
            }

            p0 === _binding.date.tvDateType -> {
                //选择年月日
                showDateChoose()
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

            val curItem: String = if (viewModel.currentStation != null) {
                viewModel.currentStation!!.id
            } else {
                ""
            }
            context?.let {
                ListPopuwindow.showPop(
                    it,
                    options,
                    _binding.header.tvTitle,
                    curItem
                ) {
                    _binding.header.tvTitle.text=options[it].title
                    showEnergyChart(second[it])
                }
            }
        }
    }


    private fun showDateChoose() {
        val date = listOf(
            getString(R.string.m89_total),
            getString(R.string.m72_year),
            getString(R.string.m71_month),
            getString(R.string.m70_day)
        )

        val options = mutableListOf<ListPopModel>()
        date.forEach {
            options.add(ListPopModel(it, false))
        }

        context?.let {
            ListPopuwindow.showPop(
                it,
                options,
                _binding.date.tvDateType,
                ""
            ) {
                //根据日期请求图表数据
                _binding.date.tvDateType.text=options[it].title




            }
        }
    }


    private fun showEnergyChart(station: StationModel) {
        viewModel.currentStation = station
        val stationType = station.stationType
        //根据电站类型显示不同界面
        childFragmentManager.commit(true) {
        }

    }


}