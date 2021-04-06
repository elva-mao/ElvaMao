package com.example.elvamao.util

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.TypedValue
import com.example.elvamao.R

object Utils {

    data class TextStyleData(
        var content : String,
        var textColor : Int = Color.parseColor("#333333"),
        var textSize : Int = 12,
        var isBold : Boolean = false)

    fun createRichTextSsb(vararg textdata: TextStyleData) : SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        var startIndex = 0
        textdata.forEach {
            ssb.append(it.content)
            val fontColorSpan = ForegroundColorSpan(it.textColor)
            val fontSizeSpan = AbsoluteSizeSpan(it.textSize, true)
            val styleSpan = if(it.isBold) StyleSpan(Typeface.BOLD) else StyleSpan(Typeface.NORMAL)
            ssb.setSpan(fontColorSpan, startIndex, ssb.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            ssb.setSpan(fontSizeSpan, startIndex, ssb.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            ssb.setSpan(styleSpan, startIndex, ssb.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            ssb.append("  ")
            startIndex = ssb.length
        }
        return ssb
    }

    fun dp2px(context: Context, dpValue: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue.toFloat(), context.resources.displayMetrics).toInt()
    }

    fun getRichContent(context: Context, leftContent : String, rightContent : String) : Spannable {
        val leftTsData = TextStyleData(leftContent, context.resources.getColor(R.color.left_content_text_color), dp2px(context, 12), true)
        val rightTsData = TextStyleData(rightContent, context.resources.getColor(R.color.left_content_text_color), dp2px(context, 12))
        return createRichTextSsb(leftTsData, rightTsData)
    }
}