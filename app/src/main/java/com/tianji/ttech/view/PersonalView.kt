package com.ttech.charge.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ttech.charge.R
import com.ttech.charge.databinding.PersonalViewBinding

/**
 * 自定义组合View-设置页面Item
 */
class PersonalView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: PersonalViewBinding


    private var itemName: String?

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.personal_view, this)
        binding = PersonalViewBinding.bind(view)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PersonalView,
            0, 0
        ).apply {
            try {
                itemName = getString(R.styleable.PersonalView_personinfo) ?: ""
            } finally {
                recycle()
            }
        }
        initView()
    }

    private fun initView() {
        binding.tvItemName.text = itemName
    }


    fun setValue(value: String) {
        binding.tvItemValue.text = value
    }


}