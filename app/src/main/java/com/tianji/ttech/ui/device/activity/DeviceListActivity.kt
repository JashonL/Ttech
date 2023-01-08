package com.tianji.ttech.ui.device.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tianji.ttech.ui.device.adapter.DeviceAdapter
import com.olp.weco.ui.device.viewmodel.DeviceListViewModel
import com.tianji.ttech.R

import com.tianji.ttech.base.BaseActivity
import com.tianji.ttech.databinding.ActivityDevicelistBinding
import com.tianji.ttech.ui.station.viewmodel.StationViewModel
import com.tianji.ttech.view.itemdecoration.DividerItemDecoration
import com.tianji.ttech.view.pop.ListPopuwindow
import com.tianji.ttech.view.popuwindow.ListPopModel

class DeviceListActivity : BaseActivity() {

    companion object {
        fun start(context: Context, plantId: String) {
            val intent = Intent(context, DeviceListActivity::class.java)
            intent.putExtra("plantId", plantId)
            context.startActivity(intent)
        }
    }


    private lateinit var _binding: ActivityDevicelistBinding


    //绑定电站viewmodel
    private val viewModel: StationViewModel by viewModels()


    private val deviceListViewModel: DeviceListViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDevicelistBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        setliseners()
        initViews()
        initData()
    }


    private fun setliseners() {
        _binding.title.setOnLeftIconClickListener {
            finish()
        }
        _binding.srlRefresh.setOnRefreshListener {
            showDeviceList()
        }
        _binding.title.setOnTitleClickListener {
            showPlantList()
        }

        _binding.title.setOnRightTextClickListener {
            OperationLogActivity.start(this,)
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
            ListPopuwindow.showPop(
                this,
                options,
                _binding.title.getTitilView(),
                curItem ?: ""
            ) {
                _binding.title.setTitleText(second[it].stationName)
                //请求设备列表
                deviceListViewModel.currentPlantId = second[it].id
                showDeviceList()
            }
        }
    }


    private fun fetchPlantList() {
        showDialog()
        viewModel.getPlantList()
    }


    private fun initViews() {
        _binding.rlvDeviceList.layoutManager = LinearLayoutManager(this)
        _binding.rlvDeviceList.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL,
                resources.getColor(R.color.nocolor),
                10f
            )
        )
        _binding.rlvDeviceList.adapter = DeviceAdapter()


    }


    private fun initData() {
        deviceListViewModel.currentPlantId=intent.getStringExtra("plantId")

        //请求设备列表
        deviceListViewModel.deviceListLiveData.observe(this) {
            dismissDialog()
            if (it.first) {
                val second = it.second
                (_binding.rlvDeviceList.adapter as DeviceAdapter).refresh(second?.toList())
            }
        }


        viewModel.getPlantListLiveData.observe(this) {
            dismissDialog()
            val second = it.second
            if (!second.isNullOrEmpty()) {
                //获取从外面传进来的电站id
                val plantId = intent.getStringExtra("plantId")
                for (i in second.indices) {
                    val plantModel = second[i]
                    if (plantModel.id == plantId) {
                        viewModel.currentStation = plantModel
                        break
                    }
                }

                //设置当前选中
                val name = viewModel.currentStation?.stationName
                deviceListViewModel.currentPlantId = viewModel.currentStation?.id
                _binding.title.setTitleText(name)

                //请求设备列表
                showDeviceList()

            }
        }


        //请求电站列表
        fetchPlantList()

    }


    fun showDeviceList() {
        _binding.srlRefresh.finishRefresh()
        showDialog()
        deviceListViewModel.getDevicelist()
    }


}