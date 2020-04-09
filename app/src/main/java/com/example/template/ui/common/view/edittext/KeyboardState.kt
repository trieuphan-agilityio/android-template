package com.example.template.ui.common.view.edittext

import android.view.View

interface KeyboardState {
    fun onHideKeyboard(view: View?)
    fun onShowKeyboard(view: View?)
}