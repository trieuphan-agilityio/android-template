package com.example.template.ui.common.view.edittext

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import com.example.common.extensions.hideSoftKeyboard
import com.example.common.extensions.showSoftKeyboard

/**
 * Using for support hide keyboard when a user click on button back, done, next, go...
 */
class AutoHideKeyboardEditText : AppCompatEditText {

    private var listener : Listener? = null

    interface Listener {
        fun onFocusChange(v: View?, hasFocus: Boolean)
    }

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
               hideSoftKeyboard()
            } else {
                showSoftKeyboard()
            }
            listener?.onFocusChange(v, hasFocus)
        }
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        // custom hide keyboard when user click on button back
        if (keyCode == KeyEvent.KEYCODE_BACK && this.isFocused) {
            this.clearFocus()
            return true
        }
        return super.onKeyPreIme(keyCode, event)
    }

    override fun onEditorAction(actionCode: Int) {
        if (actionCode == EditorInfo.IME_ACTION_DONE ||
            actionCode == EditorInfo.IME_ACTION_GO
        ) {
            this.clearFocus()
        }
        super.onEditorAction(actionCode)
    }

    fun doneEdit() {
        this.clearFocus()
        onEditorAction(EditorInfo.IME_ACTION_DONE)
    }
}
