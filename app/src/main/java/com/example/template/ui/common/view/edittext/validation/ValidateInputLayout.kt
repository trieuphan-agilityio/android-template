package com.example.template.ui.common.view.edittext.validation

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.text.Editable
import android.util.AttributeSet
import android.view.View
import android.view.View.VISIBLE
import android.widget.ImageButton
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import com.example.template.R
import com.example.template.ui.common.view.edittext.InputLayout
import io.reactivex.Observable
import io.reactivex.ObservableEmitter


abstract class ValidateInputLayout(
    context: Context,
    @Nullable attrs: AttributeSet?
) :
    InputLayout(context, attrs), ValidateState {
    private var errorIcon: Drawable? = null
    private var validIcon: Drawable? = null
    protected var firstTimeLostFocus = false
    protected var isAllValid = false
    protected var isAllInvalid = false
    protected var validates: Array<Validate>?
    var validateEmitter: ObservableEmitter<Boolean>? = null
    abstract fun buildValidate(): Array<Validate>?
    fun onValidateObservable(): Observable<Boolean> {
        return Observable.create { emitter: ObservableEmitter<Boolean>? ->
            validateEmitter = emitter
        }
    }

//    fun afterTextChanged(s: Editable?) {
//        super.afterTextChanged(s)
//        if (firstTimeLostFocus) performValidate(value)
//    }
//
//    fun onFocusChange(v: View?, hasFocus: Boolean) {
//        super.onFocusChange(v, hasFocus)
//        if (firstTimeLostFocus) {
//            performValidate(value)
//        } else if (!hasFocus) {
//            firstTimeLostFocus = true
//            performValidate(value)
//        }
//    }

    override fun onValidFocus(vararg validates: Validate?) {
        onFocus(hasValue())
        onValid()
    }

    override fun onValidUnfocus(vararg validates: Validate?) {
        onLostFocus(hasValue())
        onValid()
    }

    override fun onInvalidFocus(vararg validates: Validate?) {
        setInvalidInputState()
        onInvalid(*validates)
    }

    override fun onInvalidUnfocus(vararg validates: Validate?) {
        setInvalidInputState()
        onInvalid(*validates)
    }

    override fun onValid() {
        showIconImageButton(validIcon, imgBtnRightTwo)
    }

    override fun onInvalid(vararg validates: Validate?) {
        showIconImageButton(errorIcon, imgBtnRightTwo)
    }

    private fun init(typedArray: TypedArray) {
        val showErrorIcon =
            typedArray.getBoolean(R.styleable.ValidateInputLayout_showErrorIcon, false)
        if (showErrorIcon) {
            val errorIcon =
                typedArray.getDrawable(R.styleable.ValidateInputLayout_errorIcon)
            setErrorIcon(errorIcon)
            val validatedIcon =
                typedArray.getDrawable(R.styleable.ValidateInputLayout_validIcon)
            setValidIcon(validatedIcon)
        }
    }

    private fun performValidate(input: String) {
        if (validates == null || validates!!.size == 0) {
            return
        }

        // check validate state
        var isAllValid = true
        var isAllInvalid = false
        for (validate in validates!!) {
            if (validate.performValidate(input)) {
                isAllInvalid = true
                validate.isValid = isAllInvalid
            } else {
                isAllValid = false
                validate.isValid = isAllValid
            }
        }

        // emitter validate state to observer
        if (validateEmitter != null) validateEmitter!!.onNext(isAllValid)
        this.isAllValid = isAllValid
        this.isAllInvalid = isAllInvalid
        if (isAllValid && hasFocus) {
            onValidFocus(*validates!!)
        } else if (isAllValid) {
            onValidUnfocus(*validates!!)
        } else if (hasFocus) {
            onInvalidFocus(*validates!!)
        } else {
            onInvalidUnfocus(*validates!!)
        }
    }

    private fun setValidIcon(drawable: Drawable?) {
        validIcon = drawable
        if (drawable == null) validIcon = ContextCompat.getDrawable(context, R.drawable.ic_done)
    }

    private fun setErrorIcon(drawable: Drawable?) {
        errorIcon = drawable
        if (drawable == null) errorIcon = ContextCompat.getDrawable(context, R.drawable.ic_error)
    }

    private fun showIconImageButton(
        icon: Drawable?,
        imgButton: ImageButton
    ) {
        if (icon != null) {
            imgButton.visibility = VISIBLE
            imgButton.setImageDrawable(icon)
        }
    }

    private fun setInvalidInputState() {
        val color: Int = ContextCompat.getColor(context, R.color.color_error)
        vBottomEdit.setBackgroundColor(color)
        if (enableHint) tvHint.setTextColor(color)
    }

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ValidateInputLayout, 0, 0)
        init(typedArray)
        validates = buildValidate()
    }
}