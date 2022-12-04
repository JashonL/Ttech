package com.tianji.ttech.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.StringDef
import com.tianji.ttech.R
import com.tianji.ttech.databinding.DateSelectBinding
import com.ttech.lib.util.DateUtils
import com.ttech.lib.util.Util
import com.ttech.lib.view.dialog.DatePickerFragment
import com.ttech.lib.view.dialog.OnDateSetListener
import java.util.*

class DateSelectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private val binding: DateSelectBinding

    lateinit var nowDate: Date

    private var dateFormat = "yyyy-MM-dd"


    private var selectedListener: OntimeselectedListener? = null


    init {
        val view = LayoutInflater.from(context).inflate(R.layout.date_select, this)
        binding = DateSelectBinding.bind(view)
        initData()
        initViews()
    }

    private fun initData() {
        nowDate = Date()
    }


    private fun initViews() {
        binding.ivNext.setOnClickListener(this)
        binding.ivPre.setOnClickListener(this)
        binding.tvDate.setOnClickListener(this)

        parserDate()
    }

    private fun parserDate() {
        binding.tvDate.text = DateUtils.parserDate(nowDate, dateFormat)
    }


    override fun onClick(v: View?) {
        when {
            v === binding.ivNext -> {
                nowDate = DateUtils.addDateDays(nowDate, 1)
            }


            v === binding.ivPre -> {
                nowDate = DateUtils.addDateDays(nowDate, -1)
            }

            v === binding.tvDate -> {
                showDatePickDialog()
            }
            
        }
        selectedListener?.onDateSelectedListener(nowDate)


    }


    private fun showDatePickDialog() {
        Util.getFragmentManagerForContext(context)?.also {
            DatePickerFragment.show(it, Date().time, object : OnDateSetListener {
                override fun onDateSet(date: Date) {
                    nowDate = date
                    parserDate()
                }
            })
        }
    }


    fun setListener(selectedListener: OntimeselectedListener) {
        this.selectedListener = selectedListener
    }


    interface OntimeselectedListener {
        fun onDateSelectedListener(date: Date)
    }


    /**
     * 设备状态
     */
    @StringDef(
        DateType.DAY,
        DateType.MONTH,
        DateType.YEAR,
        DateType.TOTAL
    )
    @Retention(AnnotationRetention.SOURCE)
    @Target(
        AnnotationTarget.VALUE_PARAMETER,
        AnnotationTarget.PROPERTY
    )
    annotation class DateType {
        companion object {
            const val DAY = "1"
            const val MONTH = "2"
            const val YEAR = "3"
            const val TOTAL = "4"
        }
    }

}