package com.tianji.ttech.ui.array

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.tianji.ttech.R
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.databinding.FragmentArrayBinding
import com.tianji.ttech.databinding.FragmentEnergyBinding
import com.tianji.ttech.model.PlantModel
import com.tianji.ttech.ui.common.model.DataType
import com.tianji.ttech.ui.energy.EnergyViewModel
import com.tianji.ttech.ui.energy.chart.EnergyChartFragment
import com.tianji.ttech.ui.energy.impact.ImpactFragment
import com.tianji.ttech.ui.station.viewmodel.StationViewModel
import com.tianji.ttech.view.DateSelectView
import com.tianji.ttech.view.pop.ListPopuwindow
import com.tianji.ttech.view.popuwindow.ListPopModel
import java.util.*

class ArrayFragment : BaseFragment(), View.OnClickListener {

    private lateinit var _binding: FragmentArrayBinding

    private val viewModel: StationViewModel by viewModels()

    private val arrayViewModel: ArrayViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArrayBinding.inflate(layoutInflater)
        setliseners()
        initViews()
        initData()
        return _binding.root
    }

    private fun initData() {
        arrayViewModel.arrayLiveData.observe(viewLifecycleOwner){

        }
    }


    private fun initViews() {
        //初始化时间 年月日
        setDate()
        _binding.date.dataSelectView.setListener(object : DateSelectView.OntimeselectedListener {
            override fun onDateSelectedListener(date: Date) {
                //1.日期改变时
                arrayViewModel.setTime(date)
                //2.重新请求数据
                getArrayData()
            }
        })

    }


    private fun setDate() {
        arrayViewModel.setTime(_binding.date.dataSelectView.nowDate)
    }


    private fun setliseners() {
        _binding.header.tvTitle.setOnClickListener(this)
        _binding.date.tvDateType.setOnClickListener(this)

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

            val curItem: String? = if (viewModel.currentStation != null) {
                viewModel.currentStation!!.id
            } else {
                ""
            }
            context?.let {
                ListPopuwindow.showPop(
                    it,
                    options,
                    _binding.header.tvTitle,
                    curItem.toString()
                ) {
                    /*  _binding.header.tvTitle.text = options[it].title
                      //选择电站
                      energyViewModel.currentStation = second[it]
                      //重新请求数据
                      getPlantData()*/

                    showPlantData(second[it])

                }
            }
        }
    }


    private fun fetchPlantList() {
        showDialog()
        viewModel.getPlantList()
    }


    private fun showPlantData(station: PlantModel) {
        _binding.header.tvTitle.text = station.stationName

        //选择电站
        arrayViewModel.currentStation = station
        //重新请求数据
        getArrayData()


    }


    private fun showDateChoose() {
        val date = listOf(
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
                _binding.date.tvDateType.text = options[it].title
                arrayViewModel.dateType = it

                //重新请求求数据
                getArrayData()
            }
        }
    }


    /**
     * 请求数据
     */
    private fun getArrayData() {

        //1.请求Array
        arrayViewModel.getArrayData()
        //2.设置日期
        _binding.date.dataSelectView.setDateType(arrayViewModel.dateType)

    }


}