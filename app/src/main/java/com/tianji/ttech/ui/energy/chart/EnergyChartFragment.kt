package com.tianji.ttech.ui.energy.chart

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import com.tianji.ttech.R
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.databinding.FragmentEnergyChartBinding
import com.tianji.ttech.model.energy.ChartModel
import com.tianji.ttech.ui.chart.BarChartFragment
import com.tianji.ttech.ui.chart.LineChartFragment
import com.tianji.ttech.ui.common.model.DataType
import com.tianji.ttech.ui.energy.EnergyViewModel

class EnergyChartFragment : BaseFragment() {

    private lateinit var _binding: FragmentEnergyChartBinding

    private val energyViewModel: EnergyViewModel by viewModels(ownerProducer = { requireParentFragment() })


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnergyChartBinding.inflate(inflater, container, false)
        initData()
        return _binding.root
    }


    private fun initData() {
        //初始化请求
        energyViewModel.stationLiveData.observe(viewLifecycleOwner) {
            val second = it.second
            if (second != null) {
                setTotalData(second)
            }
        }


    }


    private fun setTotalData(chartData: ChartModel) {
        val energyTotal = chartData.energyTotal
        val solarTotal = chartData.solarTotal
        val batTotal = chartData.batTotal
        val gridTotal = chartData.gridTotal
        val loadTotal = chartData.loadTotal

        _binding.chartViewVlude.tvBat.text = batTotal
        _binding.chartViewVlude.tvGrid.text = gridTotal
        _binding.chartViewVlude.tvPpv.text = solarTotal
        _binding.chartViewVlude.tvHome.text = loadTotal
        _binding.chartViewVlude.tvNetUse.text = energyTotal + "kWh"


        val dateType = energyViewModel.dateType
        val findFragment = this.childFragmentManager.findFragmentById(R.id.fcv_chart)
        if (dateType == DataType.DAY) {//显示曲线图
            if (findFragment is LineChartFragment) {
                findFragment.refresh(energyViewModel.chartLiveData.value,"kW")
            } else {
                childFragmentManager.commit(true) {
                    replace(R.id.fcv_chart, LineChartFragment())
                }
            }



        } else {
            //刷新数据
            if (findFragment is BarChartFragment) {
                findFragment.refresh(energyViewModel.chartLiveData.value,"kW")
            } else {
                childFragmentManager.commit(true) {
                    replace(R.id.fcv_chart, BarChartFragment())
                }
            }
        }


    }


}