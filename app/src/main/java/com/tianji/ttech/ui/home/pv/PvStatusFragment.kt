package com.tianji.ttech.ui.home.pv

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tianji.ttech.R
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.databinding.FragmentSystemInvStatusBinding
import com.tianji.ttech.ui.home.pv.viewmodel.PvStationViewmodel
import com.tianji.ttech.utils.ValueUtil
import com.ttech.lib.util.setDrawableStart
import com.ttech.lib.util.textColor
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

            ValueUtil.valueFromW(it?.solar?.toDouble()).apply {
                _binding.tvSolarPower.text = String.format("%s%s", first, second)
            }
            ValueUtil.valueFromW(it?.grid?.toDouble()).apply {
                _binding.tvGridPower.text = String.format("%s%s", first, second)
            }
            ValueUtil.valueFromW(it?.home?.toDouble()).apply {
                _binding.tvHomePower.text = String.format("%s%s", first, second)
            }

            ValueUtil.valueFromW(it?.power?.toDouble()).apply {
                _binding.tvPower.text = String.format("%s%s", first, second)
            }
            ValueUtil.valueFromW(it?.energyToday?.toDouble()).apply {
                _binding.tvEnergyToday.text = String.format("%s%s", first, second)
            }
            ValueUtil.valueFromW(it?.energyTotal?.toDouble()).apply {
                _binding.tvEnergyToday.text = String.format("%s%s", first, second)
            }


            val onlineStatus = it?.onlineStatus
            if ("1" == onlineStatus){
                _binding.ivStatus.setImageResource(R.drawable.check_normal)
                _binding.tvStatus.text = getString(R.string.m82_system_nomal)

                _binding.tvStatus.textColor(R.color.status_online)
                _binding.tvOlStatus.text = getString(R.string.m124_on_line)
                _binding.tvOlStatus.setDrawableStart(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.wifi_normal
                    )
                )
            }else{
                _binding.ivStatus.setImageResource(R.drawable.exception)
                _binding.tvStatus.text = getString(R.string.m83_system_exception)

                _binding.tvOlStatus.text = getString(R.string.m125_off_line)
                _binding.tvStatus.textColor(R.color.status_offline)
                _binding.tvOlStatus.setDrawableStart(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.wifi_offline
                    )
                )

            }




            //根据值的大小设置图片
            if (it?.grid?.toDouble() != null && it.grid.toDouble() != 0.0) {
                _binding.ivSysGrid.setImageResource(R.drawable.system_grid)
            } else {
                _binding.ivSysGrid.setImageResource(R.drawable.system_grid_offline)
            }


            if (it?.home?.toDouble() != null && it.home.toDouble() != 0.0) {
                _binding.ivSysLoad.setImageResource(R.drawable.system_home)
            } else {
                _binding.ivSysLoad.setImageResource(R.drawable.system_home_offline)
            }

            if (it?.solar?.toDouble() != null && it.solar.toDouble() != 0.0) {
                _binding.ivSysPpv.setImageResource(R.drawable.system_bat)
            } else {
                _binding.ivSysPpv.setImageResource(R.drawable.system_bat_offline)
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