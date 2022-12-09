package com.tianji.ttech.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tianji.ttech.R
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.databinding.FragmentHomeBinding
import com.tianji.ttech.model.StationModel
import com.tianji.ttech.ui.common.viewmodel.SelectAreaViewModel
import com.tianji.ttech.ui.home.storage.HomeStatusFragment
import com.tianji.ttech.ui.station.StationViewModel
import com.tianji.ttech.view.dialog.OptionsDialog
import com.tianji.ttech.view.pop.ListPopuwindow
import com.tianji.ttech.view.popuwindow.ListPopModel
import com.ttech.lib.util.LogUtil
import com.ttech.lib.util.ToastUtil
import com.ttech.lib.util.gone
import com.ttech.lib.util.visible

class HomeFragment : BaseFragment(), OnClickListener {


    private lateinit var _binding: FragmentHomeBinding


    private val viewModel: StationViewModel by viewModels()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        LogUtil.i("homestatusFragment", "HomeFragment onAttach")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.i("homestatusFragment", "HomeFragment onCreate")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        LogUtil.i("homestatusFragment", "HomeFragment onCreateView")
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
            _binding.srlRefresh.finishRefresh()
            if (it.first) {
                val second = it.second
                if (second == null || second.isEmpty()) {//没有电站  显示空
                    _binding.header.tvTitle.text = ""
                    _binding.emptyView.root.visible()
                    _binding.fragmentSystem.gone()
                }else {
                    _binding.emptyView.root.gone()
                    _binding.fragmentSystem.visible()

                    //默认选中第一个电站
                    _binding.header.tvTitle.text = second[0].stationName
                    //显示系统图
                    showSystemSatus(second[0])

                }
            }
        }
        fetchPlantList()
    }



    private fun showSystemSatus(station: StationModel){
        val stationType = station.stationType
        //根据电站类型显示不同界面
        childFragmentManager.commit(true) {
            if ("PV Station" == stationType) {//纯光伏电站
                add(R.id.fragment_system, HomeStatusFragment())
            } else {

            }
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



    override fun onStart() {
        super.onStart()
        LogUtil.i("homestatusFragment", "HomeFragment onStart")
    }


    override fun onResume() {
        super.onResume()
        LogUtil.i("homestatusFragment", "HomeFragment onResume")
    }


    override fun onPause() {
        super.onPause()
        LogUtil.i("homestatusFragment", "HomeFragment onPause")
    }


    override fun onStop() {
        super.onStop()
        LogUtil.i("homestatusFragment", "HomeFragment onStop")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        LogUtil.i("homestatusFragment", "HomeFragment onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.i("homestatusFragment", "HomeFragment onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        LogUtil.i("homestatusFragment", "HomeFragment onDetach")
    }


}