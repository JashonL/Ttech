package com.tianji.ttech.ui.home.pv

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tianji.ttech.R
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.databinding.FragmentSystemInvStatusBinding
import com.tianji.ttech.ui.home.pv.viewmodel.PvStationViewmodel
import kotlinx.coroutines.delay

class PvStatusFragment : BaseFragment() {


    private lateinit var _binding: FragmentSystemInvStatusBinding


    private val viewModel: PvStationViewmodel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val arguments = arguments
        viewModel.stationId = arguments?.let {
            val stationId = it.getString("stationId")
            stationId
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSystemInvStatusBinding.inflate(inflater, container, false)
        initData()
        return _binding.root
    }


    private fun initData() {
        viewModel.statusLiveData.observe(viewLifecycleOwner) {
            _binding.srlRefresh.finishRefresh()

            _binding.tvSolarPower.text = it?.solar
            _binding.tvGridPower.text = it?.grid
            _binding.tvHomePower.text = it?.home

            _binding.tvPower.text = it?.power
            _binding.tvEnergyToday.text = it?.energyToday
            _binding.tvEnergyTotal.text = it?.energyTotal



            val onlineStatus = it?.onlineStatus
            if ("1" == onlineStatus){
                _binding.ivStatus.setImageResource(R.drawable.check_normal)
                _binding.tvStatus.text = getString(R.string.m82_system_nomal)
            }else{
                _binding.ivStatus.setImageResource(R.drawable.exception)
                _binding.tvStatus.text = getString(R.string.m83_system_exception)

            }

        }

        //获取数据
        viewModel.getDataOverview()

        //开启定时刷新
        timerStart()
    }


    fun getDataByStationId(id: String) {
        viewModel.stationId = id
        //获取数据
        viewModel.getDataOverview()
    }



    //启动定时任务
    private fun timerStart() {
        lifecycleScope.launchWhenResumed {
            repeat(Int.MAX_VALUE) {
                //获取数据
                viewModel.getDataOverview()
                delay(15 * 1000)
            }
        }
    }


}