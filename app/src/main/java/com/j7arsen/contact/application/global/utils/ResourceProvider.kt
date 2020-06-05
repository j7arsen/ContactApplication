package com.j7arsen.contact.application.global.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import androidx.annotation.*
import androidx.core.content.ContextCompat

class ResourceProvider(private val context: Context) {

    fun getString(@StringRes resId: Int): String {
        return context.resources.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.resources.getString(resId, *formatArgs)
    }

    fun getStringArray(@ArrayRes resId: Int): Array<String> {
        return context.resources.getStringArray(resId)
    }

    fun getQuantityString(@PluralsRes resId: Int, count: Int): String {
        return context.resources.getQuantityString(resId, count)
    }

    fun getQuantityString(@PluralsRes resId: Int, count: Int, vararg formatArgs: Any): String {
        return context.resources.getQuantityString(resId, count, *formatArgs)
    }

    fun getColor(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }

    fun getDrawable(@DrawableRes resId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }

    fun getDrawableId(name: String?): Int {
        return if (name != null && !TextUtils.isEmpty(name))
            context.resources.getIdentifier(name, "drawable", context.packageName)
        else 0
    }

    fun getPxSize(@DimenRes resId: Int): Int {
        return context.resources.getDimensionPixelSize(resId)
    }

    fun getBoolean(@BoolRes resId: Int): Boolean {
        return context.resources.getBoolean(resId)
    }

    fun getInt(@IntegerRes resId: Int): Int {
        return context.resources.getInteger(resId)
    }

    fun getIntArray(@ArrayRes resId: Int): IntArray {
        return context.resources.getIntArray(resId)
    }

    fun getDensity(): Float {
        return context.resources.displayMetrics.density
    }
}