package com.tencent.android.rcs.message.util

import android.text.Spannable
import android.text.TextUtils
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import kotlin.random.Random

@BindingMethods(value = [(BindingMethod(type = DataBindingUtil::class, attribute = "checkData", method = "hasData"))])
object DataBindingUtil {

    @JvmStatic
    fun <T> isNonNull(list : List<T>?, index : Int) : Boolean {
        return list != null && list.size > index && list[index] != null
    }

    @JvmStatic
    fun <T> isNotEmpty(str : String) : Boolean {
        return str != null && str.isNotEmpty()
    }

    @JvmStatic
    fun buildReadyInMinuteContent(min : Int) : String {
        var content = "$min minutes"
        if(min <= 1 ) {
            "$min minute"
        }
        return content
    }

    @JvmStatic
    fun buildAggregateLikesContent(likeCnt : Long) : String {
        var content = "$likeCnt likes"
        if(likeCnt <= 1 ) {
            "$likeCnt like"
        }
        return content
    }
}