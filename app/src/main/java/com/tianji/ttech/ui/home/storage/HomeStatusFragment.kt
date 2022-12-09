package com.tianji.ttech.ui.home.storage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.databinding.FragmentSystemStatusBinding
import com.tianji.ttech.model.StationModel
import com.ttech.lib.util.LogUtil

class HomeStatusFragment() : BaseFragment() {


    private lateinit var _binding: FragmentSystemStatusBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)
        LogUtil.i("homestatusFragment", "homestatusFragment onAttach")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.i("homestatusFragment", "homestatusFragment onCreate")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        LogUtil.i("homestatusFragment", "homestatusFragment onCreateView")
        _binding = FragmentSystemStatusBinding.inflate(inflater, container, false)
        return _binding.root
    }




    override fun onStart() {
        super.onStart()
        LogUtil.i("homestatusFragment", "homestatusFragment onStart")
    }


    override fun onResume() {
        super.onResume()
        LogUtil.i("homestatusFragment", "homestatusFragment onResume")
    }


    override fun onPause() {
        super.onPause()
        LogUtil.i("homestatusFragment", "homestatusFragment onPause")
    }


    override fun onStop() {
        super.onStop()
        LogUtil.i("homestatusFragment", "homestatusFragment onStop")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        LogUtil.i("homestatusFragment", "homestatusFragment onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.i("homestatusFragment", "homestatusFragment onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        LogUtil.i("homestatusFragment", "homestatusFragment onDetach")
    }


}