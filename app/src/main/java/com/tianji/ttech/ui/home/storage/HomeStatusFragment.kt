package com.tianji.ttech.ui.home.storage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tianji.ttech.R
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.databinding.FragmentSystemStatusBinding
import com.tianji.ttech.utils.ValueUtil
import com.ttech.lib.util.setDrawableStart
import com.ttech.lib.util.textColor
import kotlinx.coroutines.delay

class HomeStatusFragment : BaseFragment() {


    private lateinit var _binding: FragmentSystemStatusBinding


    private val viewModel: StorageViewmodel by viewModels()

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
        _binding = FragmentSystemStatusBinding.inflate(inflater, container, false)
        initData()
        return _binding.root
    }


    private fun initData() {
        viewModel.statusLiveData.observe(viewLifecycleOwner) {
            _binding.srlRefresh.finishRefresh()
            ValueUtil.valueFromW(it?.grid?.toDouble()).apply {
                _binding.tvGridPower.text = String.format("%s%s", first, second)
            }
            ValueUtil.valueFromW(it?.home?.toDouble()).apply {
                _binding.tvHomePower.text = String.format("%s%s", first, second)
            }
            ValueUtil.valueFromW(it?.solar?.toDouble()).apply {
                _binding.tvSolarPower.text = String.format("%s%s", first, second)
            }
            ValueUtil.valueFromW(it?.bat?.toDouble()).apply {
                _binding.tvBatPower.text = String.format("%s%s", first, second)
            }
            ValueUtil.valueFromW(it?.load?.toDouble()).apply {
                _binding.tvLoadPower.text = String.format("%s%s", first, second)
            }

            val onlineStatus = it?.onlineStatus
            if ("1" == onlineStatus) {
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


            } else {
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
                _binding.ivSysHome.setImageResource(R.drawable.system_home)
            } else {
                _binding.ivSysHome.setImageResource(R.drawable.system_home_offline)
            }

            if (it?.bat?.toDouble() != null && it.bat.toDouble() != 0.0) {
                _binding.ivSysBat.setImageResource(R.drawable.system_bat)
            } else {
                _binding.ivSysBat.setImageResource(R.drawable.system_bat_offline)
            }

            if (it?.solar?.toDouble() != null && it.solar.toDouble() != 0.0) {
                _binding.ivSysPpv.setImageResource(R.drawable.system_ppv)
            } else {
                _binding.ivSysPpv.setImageResource(R.drawable.system_ppv_offline)
            }



            if (it?.load?.toDouble() != null && it.load.toDouble() != 0.0) {
                _binding.ivSysLoad.setImageResource(R.drawable.system_load)
            } else {
                _binding.ivSysLoad.setImageResource(R.drawable.system_load_offline)
            }

        }

        //获取数据
        viewModel.getDataOverview()

        //开启定时刷新
        timerStart()
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


    public fun getDataByStationId(id: String) {

    }


}