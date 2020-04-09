package com.example.template.ui.common.view.edittext

import android.animation.ObjectAnimator
import android.view.View
import androidx.annotation.ColorRes

interface InputState {
    fun onFocus(hasTest: Boolean)
    fun onLostFocus(hasTest: Boolean)
    fun loadAnimationFocus(view: View?, offset: Float) {
        val animation = ObjectAnimator.ofFloat(
            view,
            "translationY", -offset
        )
        animation.duration = 300
        animation.start()
    }

    fun loadAnimationLostFocus(view: View?) {
        val animation = ObjectAnimator.ofFloat(
            view,
            "translationY", 0f
        )
        animation.duration = 300
        animation.start()
    }
}