package com.tianji.ttech.ui.home.storage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tianji.ttech.R
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.databinding.FragmentSystemStatusBinding
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            _binding.tvGridPower.text = it?.grid
            _binding.tvHomePower.text = it?.home
            _binding.tvSolarPower.text = it?.solar
            _binding.tvBatPower.text = it?.bat
            _binding.tvLoadPower.text = it?.load


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