package com.j7arsen.contact.application.global.view

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.j7arsen.contact.application.R

class StubView : LinearLayout {

    constructor(context: Context) : super(context){
        initView(context, null)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?) :  super(context, attrs) {
        initView(context, attrs)
    }

    constructor(
        context: Context,
        @Nullable attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes){
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.StubView, 0, 0)
        val bgColor = a.getColor(R.styleable.StubView_color, -1)
        var iconId = a.getResourceId(R.styleable.StubView_icon, -1)
       var  text = a.getString(R.styleable.StubView_text)
        if (text.isNullOrEmpty()) {
            text = context.resources.getString(R.string.message_error_default)
        }
        if (iconId == -1) {
            iconId = R.mipmap.ic_launcher
        }
        setBackgroundColor(bgColor)
        orientation = VERTICAL
        gravity = Gravity.CENTER
        val defaultMargin = dpToPx(8f, context)
        val params = LayoutParams(dpToPx(64f, context), dpToPx(64f, context))
        params.setMargins(defaultMargin, 0, defaultMargin, 0)
        val ivIcon = ImageView(context)
        ivIcon.layoutParams = params
        ivIcon.setImageResource(iconId)
        val textViewParams =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textViewParams.setMargins(defaultMargin, defaultMargin, defaultMargin, defaultMargin)
        val tvTitle = AppCompatTextView(context)
        tvTitle.gravity = Gravity.CENTER
        tvTitle.text = text
        tvTitle.setTextColor(ContextCompat.getColor(context, R.color.red))
        tvTitle.layoutParams = textViewParams
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpToPx(16f, context).toFloat())
        addView(ivIcon)
        addView(tvTitle)
        invalidate()
    }

    fun hide() {
        visibility = GONE
    }

    fun show() {
        visibility = VISIBLE
    }

    private fun dpToPx(valueInDp: Float, context: Context): Int {
        val metrics: DisplayMetrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics).toInt()
    }

}