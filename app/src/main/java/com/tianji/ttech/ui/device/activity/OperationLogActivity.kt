package com.tianji.ttech.ui.device.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tianji.ttech.R
import com.tianji.ttech.base.BaseActivity
import com.tianji.ttech.base.BaseViewHolder
import com.tianji.ttech.base.OnItemClickListener
import com.tianji.ttech.databinding.ActivityOperationLogBinding
import com.tianji.ttech.databinding.ItemOperationLogBinding
import com.tianji.ttech.ui.device.fragment.DataFragment
import com.tianji.ttech.ui.device.model.DeviceDataModel
import com.tianji.ttech.ui.device.model.OperatorLogModel
import com.tianji.ttech.ui.device.viewmodel.OperationViewModel
import com.tianji.ttech.view.itemdecoration.DividerItemDecoration

class OperationLogActivity:BaseActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, OperationLogActivity::class.java)
            context.startActivity(intent)
        }
    }


    private lateinit var _binding: ActivityOperationLogBinding


    private val viewModel: OperationViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOperationLogBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        initViews()
        initData()
    }


    private fun initViews() {
        _binding.rlvLog.layoutManager = LinearLayoutManager(this)
        _binding.rlvLog.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL,
                resources.getColor(R.color.nocolor),
                10f
            )
        )
        _binding.rlvLog.adapter = Adapter(mutableListOf())

    }



    private fun initData() {
        viewModel.operatorLiveData.observe(this) {
            dismissDialog()
            if (it.first) {
                (_binding.rlvLog.adapter as Adapter).refresh(it.second?.toMutableList())

            }
        }

        showDialog()
        viewModel.operationlist()
    }




    class Adapter(val datalist: MutableList<OperatorLogModel>) :
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
        fun refresh(deviceList: MutableList<OperatorLogModel>?) {
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
        lateinit var binding: ItemOperationLogBinding

        companion object {
            fun create(
                parent: ViewGroup,
                onItemClickListener: OnItemClickListener? = null
            ): DataOneViewHolder {
                val binding =
                    ItemOperationLogBinding.inflate(
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

        fun bindData(dataModel: OperatorLogModel?) {
            binding.tvSn.text=dataModel?.deviceSn
            binding.tvTime.text=dataModel?.opTime

            binding.tvInfoSn.text=dataModel?.deviceSn
            binding.tvOperator.text=dataModel?.content


        }

    }

}