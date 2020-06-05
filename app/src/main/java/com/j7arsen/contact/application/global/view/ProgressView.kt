package com.j7arsen.contact.application.global.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import com.j7arsen.contact.application.R
import kotlinx.android.synthetic.main.include_progress_layout.view.*

class ProgressView : FrameLayout {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes){
        init(context)
    }

    private var onRetryListener : OnRetryListener? = null

    fun setOnRetryListener(onRetryListener: OnRetryListener) {
        this.onRetryListener = onRetryListener
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.include_progress_layout, this)
        btn_progress_error_retry.setOnClickListener {onRetryListener?.onRetry()}
    }

    fun startLoading() {
        this.visibility = View.VISIBLE
        cl_progress.visibility = View.VISIBLE
        pb_load.visibility = View.VISIBLE
        ll_progress_error.visibility = View.GONE
    }

    fun completeLoading() {
        this.visibility = View.GONE
        cl_progress.visibility = View.GONE
        pb_load.visibility = View.GONE
        ll_progress_error.visibility = View.GONE
    }

    fun errorLoading(errorMessage: String?) {
        this.visibility = View.VISIBLE
        cl_progress.visibility = View.VISIBLE
        pb_load.visibility = View.GONE
        if (errorMessage != null) {
            ll_progress_error.visibility = View.VISIBLE
            tv_progress_error.text = errorMessage
        }
    }

    fun gone() {
        this.visibility = View.GONE
        cl_progress.visibility = View.GONE
        ll_progress_error.visibility = View.GONE
        pb_load.visibility = View.GONE
    }

    interface OnRetryListener {
        fun onRetry()
    }

}