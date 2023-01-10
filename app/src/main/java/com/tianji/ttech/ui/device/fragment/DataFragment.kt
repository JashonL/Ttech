package com.tianji.ttech.ui.device.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.tianji.ttech.ui.device.model.DeviceDataModel
import com.tianji.ttech.ui.device.viewmodel.InverterViewModel
import com.tianji.ttech.R
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.base.BaseViewHolder
import com.tianji.ttech.base.OnItemClickListener
import com.tianji.ttech.databinding.FragmentDataBinding
import com.tianji.ttech.databinding.ItemDeviceParam1Binding
import com.tianji.ttech.view.itemdecoration.DividerItemDecoration

class DataFragment : BaseFragment() {


    var position = 0


    companion object {
        const val POSITION = "position"
    }


    private lateinit var bingding: FragmentDataBinding


    private val viewModel: InverterViewModel by activityViewModels()


    private lateinit var dataTitles1: List<String>
    private lateinit var dataTitles2: List<String>
    private lateinit var dataTitles3: List<String>
    private lateinit var dataTitles4: List<String>


    override fun onAttach(context: Context) {
        super.onAttach(context)
        position = arguments?.let {
            val page = it.getInt(POSITION)
            page
        } ?: 0

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bingding = FragmentDataBinding.inflate(inflater)
        initTitles()
        initViews()
        initData()
        return bingding.root
    }

    private fun initTitles() {
        dataTitles1 = listOf(
            getString(R.string.m188_inv_model), getString(R.string.m189_inv_sn),
            getString(R.string.m190_software_version), getString(R.string.m191_work_pattern),
            getString(R.string.m192_grid_voltage), getString(R.string.m193_grid_current),
            getString(R.string.m194_output_voltage), getString(R.string.m195_output_current),
            getString(R.string.m196_out_power), getString(R.string.m197_bus_voltage),
            getString(R.string.m198_gt_current), getString(R.string.m199_reserved_bit),
            getString(R.string.m200_inv_temp), getString(R.string.m201_inv_Power),
        )


        dataTitles2 = listOf(
            getString(R.string.m202_energy_real), getString(R.string.m203_energy_day),
            getString(R.string.m204_energy_week), getString(R.string.m205_energy_month),
            getString(R.string.m206_energy_year), getString(R.string.m207_energy_total),
            getString(R.string.m208_host), getString(R.string.m209_stave1),
            getString(R.string.m210_stave2), getString(R.string.m211_stave3),
            getString(R.string.m212_ph1), getString(R.string.m213_ph2),
            getString(R.string.m214_ph3), getString(R.string.m215_reserved_temp_bit)
        )


        dataTitles3 = listOf(
            getString(R.string.m216_pv1_current), getString(R.string.m217_PV1_Voltage),
            getString(R.string.m218_PV1_Power), getString(R.string.m219_PV2_Current),
            getString(R.string.m220_PV2_Voltage), getString(R.string.m221_PV2_Power),
            getString(R.string.m222_charge_current), getString(R.string.m223_temp),
            getString(R.string.m224_charge_voltage), getString(R.string.m225_Charge_Current),
            getString(R.string.m226_Charge_Power), getString(R.string.m227_Charge_Voltage),
            getString(R.string.m228_BAT_Energy), getString(R.string.m229_BAT_Capacity)
        )


        dataTitles4 = listOf(
            getString(R.string.m230_DisCharge_Energy), getString(R.string.m231_DisCharge_Current),
            getString(R.string.m232_Charge_Voltage), getString(R.string.m233_Charge_Energy),
            getString(R.string.m234_Charge_Power), getString(R.string.m235_Charge_Current)
        )

    }


    private fun initData() {
        viewModel.inverterLiveData.observe(viewLifecycleOwner) {
            dismissDialog()
            if (it.first) {
                val second = it.second
                val datas = mutableListOf<DeviceDataModel>()


                if (position == 0) {
                    for (i in dataTitles1.indices) {
                        val title = dataTitles1[i]
                        val value = when (i) {
                            0 -> second?.invModel
                            1 -> second?.invSN
                            2 -> second?.softwareVersion
                            3 -> second?.workPattern
                            4 -> second?.gridVoltage
                            5 -> second?.gridCurrent
                            6 -> second?.outputVoltage
                            7 -> second?.outputCurrent
                            8 -> second?.outputPower
                            9 -> second?.busVoltage
                            10 -> second?.gtCurrent
                            11 -> second?.reservedBit
                            12 -> second?.invTemp
                            13 -> second?.inPower
                            else -> {
                                ""
                            }
                        }
                        val model = DeviceDataModel(title, value.toString())
                        datas.add(model)
                    }
                } else if (position == 1) {
                    for (i in dataTitles2.indices) {
                        val title = dataTitles2[i]
                        val value = when (i) {
                            0 -> second?.energyTotal
                            1 -> second?.energyDay
                            2 -> second?.energyWeek
                            3 -> second?.energyMonth
                            4 -> second?.energyYear
                            5 -> second?.energyTotal
                            6 -> second?.host
                            7 -> second?.slave1
                            8 -> second?.slave2
                            9 -> second?.slave3
                            10 -> second?.ph1
                            11 -> second?.ph2
                            12 -> second?.ph3
                            13 -> second?.reservedTempBit
                            else -> {
                                ""
                            }
                        }
                        val model = DeviceDataModel(title, value.toString())
                        datas.add(model)
                    }

                } else if (position == 2) {
                    for (i in dataTitles3.indices) {
                        val title = dataTitles3[i]
                        val value = when (i) {
                            0 -> second?.pv1Current
                            1 -> second?.pv1Voltage
                            2 -> second?.pv1Power
                            3 -> second?.pv2Current
                            4 -> second?.pv2Voltage
                            5 -> second?.pv2Power
                            6 -> second?.chargeCurrent
                            7 -> second?.temp
                            8 -> second?.chargeVoltage
                            9 -> second?.chargeCurrent
                            10 -> second?.batChargePower
                            11 -> second?.batChargeVoltage
                            12 -> second?.batChargeEnergy
                            13 -> second?.batCapacity
                            else -> {
                                ""
                            }
                        }
                        val model = DeviceDataModel(title, value.toString())
                        datas.add(model)
                    }

                } else if (position == 3) {
                    for (i in dataTitles4.indices) {
                        val title = dataTitles4[i]
                        val value = when (i) {
                            0 -> second?.batDischargeEnergy
                            1 -> second?.batDischargeCurrent
                            2 -> second?.batChargeVoltage
                            3 -> second?.batChargeEnergy
                            4 -> second?.batChargePower
                            5 -> second?.batChargeCurrent
                            else -> {
                                ""
                            }
                        }
                        val model = DeviceDataModel(title, value.toString())
                        datas.add(model)
                    }
                }




                (bingding.rlvData.adapter as Adapter).refresh(datas)

            }


        }

    }


    private fun initViews() {
        bingding.rlvData.layoutManager = GridLayoutManager(context, 2)
        bingding.rlvData.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL,
                resources.getColor(R.color.nocolor),
                10f
            )
        )
        bingding.rlvData.adapter = Adapter(mutableListOf())

    }


    class Adapter(val datalist: MutableList<DeviceDataModel>) :
        RecyclerView.Adapter<BaseViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
            return DataOneViewHolder.create(parent)
        }

        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            (holder as DataOneViewHolder).bindData(datalist[position])
        }

        override fun getItemCount(): Int {
            return datalist.size
        }

        @SuppressLint("NotifyDataSetChanged")
        fun refresh(deviceList: MutableList<DeviceDataModel>?) {
            this.datalist.clear()
            if (!deviceList.isNullOrEmpty()) {
                this.datalist.addAll(deviceList)
            }
            notifyDataSetChanged()
        }


    }


    class DataOneViewHolder(
        itemView: View,
        onItemClickListener: OnItemClickListener? = null
    ) :
        BaseViewHolder(itemView, onItemClickListener) {
        lateinit var binding: ItemDeviceParam1Binding

        companion object {
            fun create(
                parent: ViewGroup,
                onItemClickListener: OnItemClickListener? = null
            ): DataOneViewHolder {
                val binding =
                    ItemDeviceParam1Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                val holder = DataOneViewHolder(binding.root, onItemClickListener)
                holder.binding = binding
                holder.binding.root.setOnClickListener(holder)
                return holder
            }
        }

        fun bindData(dataModel: DeviceDataModel?) {
            binding.tvKey.text = dataModel?.key
            binding.tvValue.text = dataModel?.value
        }

    }


}