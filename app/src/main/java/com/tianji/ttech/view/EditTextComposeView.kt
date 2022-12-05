package com.tianji.ttech.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.tianji.ttech.R
import com.tianji.ttech.databinding.EditextComposeBinding
import com.ttech.lib.util.gone
import com.ttech.lib.util.visible

class EditTextComposeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    private var bingding: EditextComposeBinding

    private var hideString: String
    private var leftIcon: Drawable?


    private var rightShow: Boolean
    private var rightIcon: Drawable?
    private var rightText: String?

    private var rightType: Int


    init {
        val view = LayoutInflater.from(context).inflate(R.layout.editext_compose, this)
        bingding = EditextComposeBinding.bind(view)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.EditTextComposeView,
            0, 0
        ).apply {
            try {
                leftIcon = getDrawable(R.styleable.EditTextComposeView_compose_left_icon)
                hideString = getString(R.styleable.EditTextComposeView_compose_hide) ?: ""

                rightShow = getBoolean(R.styleable.EditTextComposeView_compose_right_show, false)
                rightIcon = getDrawable(R.styleable.EditTextComposeView_compose_rigth_icon)
                rightText = getString(R.styleable.EditTextComposeView_compose_rigth_text) ?: ""

                rightType = getInt(R.styleable.EditTextComposeView_compose_right_type, 1)


            } finally {
                recycle()
            }
        }

        initViews()

    }


    private fun initViews() {
        bingding.ivIcon.setImageDrawable(leftIcon)
        bingding.etContent.hint = hideString




        if (rightShow) bingding.flRight.visible() else bingding.flRight.gone()


        if (rightType == 1) {
            bingding.ivRightIcon.gone()
            bingding.tvRightText.visible()
        } else {
            bingding.ivRightIcon.visible()
            bingding.tvRightText.gone()
        }



        bingding.ivRightIcon.setImageDrawable(rightIcon)
        bingding.tvRightText.text = rightText


    }


}