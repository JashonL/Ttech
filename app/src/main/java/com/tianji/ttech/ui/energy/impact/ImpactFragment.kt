package com.tianji.ttech.ui.energy.impact

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.tianji.ttech.R
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.databinding.FragmentImpactBinding
import com.tianji.ttech.ui.chart.BarChartFragment
import com.tianji.ttech.ui.common.model.DataType
import com.tianji.ttech.ui.energy.EnergyViewModel
import com.tianji.ttech.ui.energy.chart.EnergyChartFragment
import com.ttech.lib.util.GsonManager
import com.ttech.lib.util.gone
import com.ttech.lib.util.visible

class ImpactFragment : BaseFragment() {

    private val energyViewModel: EnergyViewModel by viewModels(ownerProducer = { requireParentFragment() })


    private lateinit var _binding: FragmentImpactBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImpactBinding.inflate(inflater, container, false)
        initviews()
        initData()
        return _binding.root
    }

    private fun initviews() {
        //绑定图表
        childFragmentManager.commit {
            add(R.id.impact_chart, BarChartFragment().apply {
                val bundle = Bundle()
                bundle.putString(EnergyChartFragment.UNIT, "$")
                bundle.putParcelableArrayList(
                    EnergyChartFragment.COLORS,
                    EnergyChartFragment.arrayOf
                )
            })
        }

    }


    private fun initData() {
        //初始化请求
        energyViewModel.impactLiveData.observe(viewLifecycleOwner) {
            //天的数据
            _binding.tvIncome.text = "$${it?.impactTotal}"
        }

        energyViewModel.impactChartLiveData.observe(viewLifecycleOwner) {
            val dateType = energyViewModel.dateType
            //设置图表
            if (dateType == DataType.DAY) {//隐藏图表
                _binding.impactChart.gone()
                _binding.llToday.visible()

            } else {
                _binding.impactChart.visible()
                _binding.llToday.gone()
                //设置图表
                val findFragment = this.childFragmentManager.findFragmentById(R.id.impact_chart)
                (findFragment as BarChartFragment).refresh(
                    energyViewModel.impactChartLiveData.value,
                    "$"
                )
            }

        }


    }


}