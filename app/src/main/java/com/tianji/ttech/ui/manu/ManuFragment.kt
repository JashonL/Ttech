package com.tianji.ttech.ui.manu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.databinding.FragmentHomeMineBinding
import com.tianji.ttech.ui.manu.activity.SettingActivity

class ManuFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentHomeMineBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeMineBinding.inflate(inflater, container, false)
        initData()
        return binding.root
    }

    private fun initData() {
        val email = accountService().user()?.email
        binding.tvUserName.text = email
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when {
            p0 === binding.llAccount -> SettingActivity.start(context)
//            p0 === binding.itemAbout -> AboutActivity.start(context)
//            p0 === binding.itemMessageCenter -> MessageCenterActivity.start(context)
        }
    }

}