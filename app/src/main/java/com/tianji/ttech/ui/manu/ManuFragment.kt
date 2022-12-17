package com.tianji.ttech.ui.manu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tianji.ttech.base.BaseFragment
import com.tianji.ttech.databinding.FragmentHomeMineBinding
import com.tianji.ttech.ui.manu.activity.SettingActivity
import com.tianji.ttech.ui.manu.viewmodel.ManuViewModel
import com.tianji.ttech.ui.manu.viewmodel.SettingViewModel
import com.ttech.lib.util.ToastUtil

class ManuFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentHomeMineBinding? = null

    private val binding get() = _binding!!


    private val viewModel: ManuViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeMineBinding.inflate(inflater, container, false)
        initData()
        setListeners()
        return binding.root
    }


    private fun setListeners() {
        binding.llAccount.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
    }


    private fun initData() {
        val email = accountService().user()?.email
        binding.tvUserName.text = email


        viewModel.logoutLiveData.observe(viewLifecycleOwner) {
            dismissDialog()
            if (it == null) {
                accountService().logout()
                accountService().login(requireActivity())
            } else {
                showResultDialog(it,null,null)
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when {
            p0 === binding.llAccount -> SettingActivity.start(context)
            p0 === binding.btnLogout -> {
                showDialog()
                viewModel.logout(accountService().user()?.email)

            }
//            p0 === binding.itemAbout -> AboutActivity.start(context)
//            p0 === binding.itemMessageCenter -> MessageCenterActivity.start(context)
        }
    }

}