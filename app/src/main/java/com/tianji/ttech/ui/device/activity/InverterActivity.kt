package com.tianji.ttech.ui.device.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.allViews
import androidx.core.view.get
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.tianji.ttech.R
import com.tianji.ttech.ui.device.fragment.DataFragment
import com.tianji.ttech.ui.device.viewmodel.InverterViewModel
import com.tianji.ttech.base.BaseActivity
import com.tianji.ttech.databinding.ActivityInverterBinding

class InverterActivity : BaseActivity() {


    companion object {
        fun start(context: Context, deviceSN: String) {
            val intent = Intent(context, InverterActivity::class.java)
            intent.putExtra("deviceSN", deviceSN)
            context.startActivity(intent)
        }
    }


    private lateinit var binding: ActivityInverterBinding

    private val viewModel: InverterViewModel by viewModels()


    private val fragments: MutableList<Fragment> by lazy(LazyThreadSafetyMode.NONE) {
        mutableListOf(
            DataFragment().apply {
                //传参过去
                val bundle = Bundle()
                bundle.putInt(DataFragment.POSITION, 0)
                this.arguments = bundle
            },
            DataFragment().apply {
                val bundle = Bundle()
                bundle.putInt(DataFragment.POSITION, 1)
                this.arguments = bundle

            },
            DataFragment().apply {
                val bundle = Bundle()
                bundle.putInt(DataFragment.POSITION, 2)
                this.arguments = bundle
            },
            DataFragment().apply {
                val bundle = Bundle()
                bundle.putInt(DataFragment.POSITION, 3)
                this.arguments = bundle
            }
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInverterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initData()
    }


    private fun initViews() {
        //绑定Viewpager
        binding.viewpager.adapter = Adapter(this, fragments)
        //动态添加指示器
        repeat(fragments.size) {
            val textView = TextView(this)
            textView.apply {
                //设置大小
                val pixe = resources.getDimensionPixelOffset(R.dimen.dp_10)
                val layoutParams = LinearLayout.LayoutParams(pixe, pixe)
                layoutParams.setMargins(20, 0, 0, 0)
                setLayoutParams(layoutParams)

                //设置背景
                background = if (it == 0) {
                    ContextCompat.getDrawable(context, R.drawable.circle_black)
                } else {
                    ContextCompat.getDrawable(context, R.drawable.circle_gray)
                }
            }
            binding.llIndicator.addView(textView)

        }


        binding.viewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val count = binding.llIndicator.childCount
                repeat(count) {
                    if (position == it) {
                        binding.llIndicator.get(it).background =
                            ContextCompat.getDrawable(
                                this@InverterActivity,
                                R.drawable.circle_black
                            )
                    } else {
                        binding.llIndicator.get(it).background =
                            ContextCompat.getDrawable(this@InverterActivity, R.drawable.circle_gray)
                    }
                }

            }
        })


    }


    class Adapter(fragmenActivity: FragmentActivity, private val fragments: MutableList<Fragment>) :
        FragmentStateAdapter(fragmenActivity) {


        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }


    }


    private fun initData() {
        viewModel.deviceSn = intent.getStringExtra("deviceSN") ?: ""
        //请求数据
        showDialog()
        viewModel.getdeviceDetails()
    }


}