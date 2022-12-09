package com.tianji.ttech.ui.home.storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.databinding.FragmentSystemStatusBinding
import com.tianji.ttech.model.StationModel

class HomeStatusFragment(val station: StationModel) : BaseFragment() {


    private lateinit var _binding: FragmentSystemStatusBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSystemStatusBinding.inflate(inflater, container, false)
        return _binding.root
    }

}