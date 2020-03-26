package com.example.template.ui.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.template.R

open class NormalEditText : AppCompatEditText {

    var buttonFocusId: Int? = -1
//    var buttonView: Button? = null
    var isFocus = false
    var isNotTouchEye = true
    var isSecure = false
        set(value) {
            field = value
//            if (value) {
//                addEyeIcon(isNotTouchEye)
//            }
        }


    constructor(context: Context?) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NormalEditText)

        if (buttonFocusId == -1) {
            buttonFocusId = typedArray.getResourceId(R.styleable.NormalEditText_normalButtonFocusId, -1)
        }
        if (!isFocus) {
            isFocus = typedArray.getBoolean(R.styleable.NormalEditText_normalIsFocus, false)
        }
        typedArray.recycle()

        //set status focus change
//        focusChange()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

//        if (buttonFocusId != -1) {
//            buttonView = buttonFocusId?.let { rootView.findViewById(it) }
//
//            if (isFocus) {
//                requestFocus()
//            }
//        }

        //set status focus change
//        focusChange()
    }

//    fun formatter(format: (String?) -> String?, isDecimal: Boolean = false) {
//        addTextChangedListener(MaskTextWatcher(this, isDecimal, format))
//    }

    /*******************************
     *  For security
     ******************************/
//    @SuppressLint("ClickableViewAccessibility")
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        if (event.x >= this.width - this.totalPaddingRight) {
//            if (event.action == MotionEvent.ACTION_UP) {
//                isNotTouchEye = !isNotTouchEye
//                if(isSecure) {
//                    addEyeIcon(isNotTouchEye)
//                }
//            }
//        }
//
//        return super.onTouchEvent(event)
//    }

//    private fun addEyeIcon(isNotTouchEye: Boolean) {
//        updateRightDrawable(isNotTouchEye)
//        updateTransformationMethod(isNotTouchEye)
//    }

//    private fun updateTransformationMethod(isNotTouchEye: Boolean) {
//        transformationMethod =
//            if (isNotTouchEye)
//                PasswordTransformationMethod.getInstance()
//            else
//                HideReturnsTransformationMethod.getInstance()
//    }
//
//    private fun updateRightDrawable(isNotTouchEye: Boolean) {
//        val eyeIcon = if (isNotTouchEye) {
//            ContextCompat.getDrawable(context, R.drawable.ic_eye_on)
//        } else {
//            ContextCompat.getDrawable(context, R.drawable.ic_eye_off)
//        }
//        eyeIcon?.setBounds(0, 0, eyeIcon.intrinsicWidth, eyeIcon.intrinsicHeight)
//        setCompoundDrawables(null, null, eyeIcon, null)
//    }

//    /**
//     * Set status focus change
//     */
//    fun focusChange() {
//        setOnFocusChangeListener { v, hasFocus ->
//            if (!hasFocus) v.hideSoftKeyboard() else v.showSoftKeyboard()
//            buttonView?.showBackgroundByStatus(hasFocus)
//        }
//    }
}