package com.example.template.ui.common.view.edittext.validation

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

interface ValidateState {
    fun onValidFocus(vararg validates: Validate?)
    fun onValidUnfocus(vararg validates: Validate?)
    fun onInvalidFocus(vararg validates: Validate?)
    fun onInvalidUnfocus(vararg validates: Validate?)
    fun onValid()
    fun onInvalid(vararg validates: Validate?)

//    companion object {
//        @ColorRes
//        val defaultValidFocusColor: Int = com.example.template.R.color.colorPrimary
//
//
//        @ColorRes
//        val defaultValidUnFocusColor: Int = com.example.template.R.color.color_gray
//
//        @ColorRes
//        val defaultInvalidColor: Int = com.example.template.R.color.color_red
//
//        @ColorRes
//        val defaultErrorColor = defaultInvalidColor
//
//        @DrawableRes
//        val defaultErrorIcon: Int = com.example.template.R.drawable.ic_error
//
//        @DrawableRes
//        val defaultValidIcon: Int = com.example.template.R.drawable.ic_done
//    }
}