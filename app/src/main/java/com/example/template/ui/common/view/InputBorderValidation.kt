package com.example.template.ui.common.view

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.template.R
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.layout_input_boder_validate.view.*

@Retention(AnnotationRetention.SOURCE)
@IntDef(NORMAL, PASSWORD, PREFIX)
annotation class TypeDef

const val NORMAL = 0
const val PASSWORD = 1
const val PREFIX = 2

/**
 * Multiple lines of text in the field corresponds to {@link android.text.InputType#TYPE_TEXT_FLAG_MULTI_LINE}
 */
const val TYPE_TEXT_FLAG_MULTI_LINE = EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE + 1

class InputBorderValidation(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    var text: String = ""
        get() = editText?.text.toString()
        set(value) {
            field = value
            editText.setText(value)
        }

    @StringRes
    var hint: Int = 0
        set(value) {
            field = value
            editText.setHint(value)
        }
    var typeEditText = NORMAL
    var prefixText: String? = ""
        set(value) {
            field = value
            tvPrefix.text = prefixText
        }
    var inputFilter = ""
        set(value) {
            field = value
            editText.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
                if (source != null && value.contains("" + source)) {
                    ""
                } else null
            })
        }

    var maxLength: Int = -1

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_input_boder_validate, this)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditText)
        val hintRes = typedArray.getResourceId(R.styleable.EditText_android_hint, 0)
        if (hintRes != 0) {
            editText.setHint(hintRes)
        }

        maxLength = typedArray.getInteger(R.styleable.EditText_android_maxLength, -1)
        if (maxLength != -1) {
            editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        }

        if (prefixText.isNullOrEmpty()) {
            prefixText = typedArray.getString(R.styleable.EditText_prefix)
            tvPrefix.text = prefixText
        }

        val buttonFocusId = typedArray.getResourceId(R.styleable.EditText_buttonFocusId, 0)
        if (buttonFocusId != 0) {
            editText.buttonFocusId = buttonFocusId
        }

        val isFocus = typedArray.getBoolean(R.styleable.EditText_isFocus, false)
        editText.isFocus = isFocus

        val height = typedArray.getDimensionPixelSize(R.styleable.EditText_heightEditText, 0)
        if (height != 0) {
            val layoutParams = editText.layoutParams
            layoutParams.height = height
            editText.layoutParams = layoutParams
        }

        typeEditText = typedArray.getInteger(R.styleable.EditText_typeEditText, NORMAL)
        showEditTextByType(typeEditText)
        editText.isSecure = typeEditText == PASSWORD

        val inputType = typedArray.getInteger(
            R.styleable.EditText_android_inputType,
            EditorInfo.TYPE_CLASS_TEXT
        )

        if (inputType == EditorInfo.TYPE_CLASS_NUMBER) {
            editText.keyListener = DigitsKeyListener.getInstance("0123456789")
        }

        if (inputType == TYPE_TEXT_FLAG_MULTI_LINE) {
            editText.setSingleLine(false)
        }

        editText.setRawInputType(inputType or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)

        val imeOption = typedArray.getInteger(R.styleable.EditText_android_imeOptions, -1)
        if (imeOption != -1) {
            editText.imeOptions = imeOption
        }

        val gravity = typedArray.getInteger(R.styleable.EditText_android_gravity, -1)
        if (gravity != -1) {
            editText.gravity = gravity
        }

        val isFocusable = typedArray.getBoolean(R.styleable.EditText_android_focusable, true)
        editText.isFocusable = isFocusable

        val paddingTop = typedArray.getDimensionPixelSize(R.styleable.EditText_paddingTop, 0)
        if (paddingTop != 0) {
            val paddingStart = resources.getDimensionPixelSize(R.dimen.edt_padding_start_end)
            editText.setPadding(paddingStart, paddingTop, paddingStart, paddingTop)
        }

        val digits = typedArray.getString(R.styleable.EditText_android_digits)
        if (!digits.isNullOrEmpty()) {
            editText.keyListener = DigitsKeyListener.getInstance(digits)
        }

        val colorTextDefault = ContextCompat.getColor(context, R.color.colorTextDefault)
        val textColor = typedArray.getColor(R.styleable.EditText_android_textColor, colorTextDefault)
        editText.setTextColor(textColor)

        val backgroundRes = typedArray.getResourceId(R.styleable.EditText_backgroundRes, R.drawable.edit_background)
        parentLayout.background = ContextCompat.getDrawable(context, backgroundRes)

        typedArray.recycle()
    }

    fun formatter(format: (String?) -> String?, isDecimal: Boolean = false) {
        editText.formatter(format, isDecimal)
    }

    fun validate(validator: ((String?) -> Boolean), msgError: String = ""): Flowable<Boolean> {
        return Flowable.create({ emitter ->
            emitter.onNext(validator(editText.text.toString()))
            editText.doAfterTextChanged {
                emitter.onNext(validator(it?.toString()))
                showErrorMsg(validator(it?.toString()), msgError)
            }
        }, BackpressureStrategy.BUFFER)
    }

    fun showErrorMsg(hasNotError: Boolean = false, msgError: String = "") {
        tvErrorMsg.isGone = hasNotError || msgError.isEmpty()
        tvErrorMsg.text = msgError
        val bgRes =
            if (hasNotError) R.drawable.edit_background else R.drawable.edit_background_error
        parentLayout.background = ContextCompat.getDrawable(context, bgRes)
    }

    private fun showEditTextByType(@TypeDef typeEditText: Int) {
        tvPrefix.isGone = typeEditText != PREFIX

        if (typeEditText == PREFIX) {
            editText.setPadding(
                0,
                0,
                dpToPx(context, resources.getDimensionPixelSize(R.dimen.edt_padding_start_end)),
                0
            )
        }
    }

    /**
     * Clear data [EditText]
     */
    fun clear() {
        editText.text?.clear()
        showErrorMsg(true)
    }
}