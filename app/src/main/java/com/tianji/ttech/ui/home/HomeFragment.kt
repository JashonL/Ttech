package com.tianji.ttech.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.databinding.FragmentHomeBinding
import com.tianji.ttech.ui.common.viewmodel.SelectAreaViewModel
import com.tianji.ttech.ui.station.StationViewModel
import com.ttech.lib.util.ToastUtil

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private val viewModel: StationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initData()
        return binding.root
    }


    private fun initData() {
        viewModel.getPlantListLiveData.observe(viewLifecycleOwner) {
            if (it.second == null) {
            } else {
                ToastUtil.show(it.second)
            }
        }
        fetchPlantList()
    }



    private fun fetchPlantList() {
        showDialog()
        viewModel.getPlantList()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}