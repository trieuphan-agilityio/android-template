package com.example.template.ui.common.view.edittext

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.*
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import com.example.common.extensions.hideSoftKeyboard
import com.example.common.extensions.showSoftKeyboard
import com.example.template.R
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import org.apache.commons.lang3.StringUtils

open class InputLayout(
    context: Context,
    @Nullable attrs: AttributeSet?
) :
    LinearLayout(context, attrs), OnFocusChangeListener, TextWatcher, InputState, KeyboardState {
    protected val rlEdit: RelativeLayout
    protected val editText: EditText
    protected val tvHint: TextView
    protected val vBottomEdit: View
    protected val tvErrorOne: TextView
    protected val tvErrorTwo: TextView
    protected val imgBtnRightOne: ImageButton
    protected val imgBtnRightTwo: ImageButton
    protected val imgBtnLeft: ImageButton
    protected var enableHint: Boolean = false
    protected var hasFocus: Boolean = false
    protected var isFocusDefault: Boolean = false
    protected var isSetValue: Boolean = false
    protected var emitterTextChange: ObservableEmitter<String>? = null

    private var offsetTvHintAnimation: Float = 0f

    override fun setOnFocusChangeListener(onFocusChangeListener: OnFocusChangeListener?) {
        this.onFocusChangeListener = onFocusChangeListener
    }

    var value: String
        get() = editText.text.toString()
        set(value) {
            isSetValue = true
            editText.setText(value)
            editText.setSelection(value.length)
        }

    fun hasValue(): Boolean {
        return !StringUtils.isEmpty(value)
    }

    val observableTextChange: Observable<String>
        get() = Observable.create { emitter: ObservableEmitter<String>? ->
            emitterTextChange = emitter
        }

    override fun setEnabled(isEnable: Boolean) {
        super.setEnabled(isEnable)
        editText.isEnabled = isEnable
    }

    fun requestFocusInput() {
        editText.requestFocus()
    }

    fun setFocusableInput(isFocusable: Boolean) {
        editText.isFocusable = isFocusable
    }

    val isFocusedInput: Boolean
        get() {
            return editText.isFocused
        }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        this.hasFocus = hasFocus
        if (onFocusChangeListener != null) onFocusChangeListener!!.onFocusChange(v, hasFocus)
        val hasValue: Boolean = !StringUtils.isEmpty(value)
        if (hasFocus) {
            onFocus(hasValue)
        } else {
            onLostFocus(hasValue)
        }
    }

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
    }

    override fun afterTextChanged(s: Editable) {
        if (emitterTextChange != null) emitterTextChange!!.onNext(s.toString().trim { it <= ' ' })
    }

    override fun onFocus(hasTest: Boolean) {
        ContextCompat.getColor(context, R.color.colorTextDefault)
        val color = ContextCompat.getColor(context, R.color.colorPrimary)
        if (enableHint) {
            tvHint.setTextColor(color)
            if (!hasTest) loadAnimationFocus(tvHint, offsetTvHintAnimation)
        }
        vBottomEdit.setBackgroundColor(color)
        onShowKeyboard(editText)
    }

    override fun onLostFocus(hasTest: Boolean) {
        val color = ContextCompat.getColor(context, R.color.brown_grey_60)
        if (enableHint) {
            tvHint.setTextColor(color)
            if (!hasTest) loadAnimationLostFocus(tvHint)
        }
        vBottomEdit.setBackgroundColor(color)
        onHideKeyboard(editText)
    }

    override fun onHideKeyboard(view: View?) {
        hideSoftKeyboard()
    }

    override fun onShowKeyboard(view: View?) {
        showSoftKeyboard()
    }

    private fun init(typedArray: TypedArray) {
        val isEnabled: Boolean =
            typedArray.getBoolean(R.styleable.InputLayout_android_enabled, true)
        editText.isEnabled = isEnabled
        val text: String? = typedArray.getString(R.styleable.InputLayout_android_text)
        if (!StringUtils.isEmpty(text)) {
            editText.setText(text)
        }
        val hint: String? = typedArray.getString(R.styleable.InputLayout_textHint)
        setHint(hint)
        isFocusDefault = typedArray.getBoolean(R.styleable.InputLayout_focusDefault, false)
        setFocus(isFocusDefault)
        val inputType: Int =
            typedArray.getInt(R.styleable.InputLayout_android_inputType, InputType.TYPE_CLASS_TEXT)
        setInputType(inputType)
        val maxLength: Int =
            typedArray.getInt(R.styleable.InputLayout_android_maxLength, Int.MAX_VALUE)
        setMaxLength(maxLength)
        val gravity: Int =
            typedArray.getInteger(R.styleable.InputLayout_android_gravity, Gravity.CENTER_VERTICAL)
        editText.gravity = gravity
    }

    private fun setMaxLength(maxLength: Int) {
        val filterArray: Array<InputFilter?> = arrayOfNulls(1)
        filterArray[0] = LengthFilter(maxLength)
        editText.filters = filterArray
    }

    private fun setHint(hint: String?) {
        if (StringUtils.isEmpty(hint)) {
            tvHint.visibility = View.GONE
            enableHint = false
            updateLayoutForDisableTextHint(false)
        } else {
            tvHint.visibility = View.VISIBLE
            tvHint.text = hint
            enableHint = true
            updateLayoutForDisableTextHint(true)
        }
    }

    private fun setFocus(isFocus: Boolean) {
        if (isFocus) editText.requestFocus()
    }

    private fun setInputType(typeInput: Int) {
        editText.inputType = typeInput
    }

    private fun setPaddingEndEditText(paddingEnd: Int) {
        editText.setPadding(
            editText.paddingStart,
            editText.paddingTop,
            paddingEnd,
            editText.paddingBottom
        )
    }

    private fun updatePaddingEditTextShowRightButton() {
        val isRightImgBtnOneVisible: Boolean =
            imgBtnRightOne.visibility == View.VISIBLE
        val isRightImgBtnTwoVisible: Boolean =
            imgBtnRightTwo.visibility == View.VISIBLE
        val bufferValueSingle = 25
        if (isRightImgBtnOneVisible && isRightImgBtnTwoVisible) {
            val bufferValue = 50
            setPaddingEndEditText(imgBtnRightOne.width + imgBtnRightTwo.width + bufferValue)
        } else if (isRightImgBtnOneVisible) {
            setPaddingEndEditText(imgBtnRightOne.width + bufferValueSingle)
        } else if (isRightImgBtnTwoVisible) {
            setPaddingEndEditText(imgBtnRightTwo.width + bufferValueSingle)
        }
        val isLeftImgBtnVisible: Boolean =
            imgBtnLeft.visibility == View.VISIBLE
        if (isLeftImgBtnVisible) {
            setPaddingEndEditText(imgBtnLeft.width + bufferValueSingle)
        }
    }

    private fun updateLayoutForDisableTextHint(isHasHint: Boolean) {
        val layoutParams: LayoutParams =
            rlEdit.layoutParams as LayoutParams
        layoutParams.height =
            if (isHasHint) resources.getDimensionPixelSize(R.dimen.height_50) else resources.getDimensionPixelSize(
                R.dimen.height_50
            )
        rlEdit.layoutParams = layoutParams
    }

    init {
        val layoutInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutInflater.inflate(R.layout.layout_input, this, true)
        rlEdit = findViewById(R.id.rl_edit)
        tvHint = findViewById(R.id.tv_hint)
        vBottomEdit = findViewById(R.id.view_bottom_edit)
        editText = findViewById(R.id.edit_text)
        imgBtnRightOne = findViewById(R.id.img_btn_right_one)
        imgBtnRightTwo = findViewById(R.id.img_btn_right_two)
        imgBtnLeft = findViewById(R.id.img_btn_left)
        tvErrorOne = findViewById(R.id.tv_error_one)
        tvErrorTwo = findViewById(R.id.tv_error_two)
        editText.onFocusChangeListener = this
        editText.addTextChangedListener(this)
        editText.viewTreeObserver.addOnGlobalLayoutListener {
            val offset: Int = editText.height / 2
            if (offset != 0) {
                val buffer = 5
                offsetTvHintAnimation = offset + buffer.toFloat()
            }
            if (isFocusDefault || isSetValue) {
                if (isFocusDefault) isFocusDefault = false
                loadAnimationFocus(tvHint, offsetTvHintAnimation)
            }
        }
        val layoutChange = OnGlobalLayoutListener { updatePaddingEditTextShowRightButton() }
        imgBtnRightOne.viewTreeObserver.addOnGlobalLayoutListener(layoutChange)
        imgBtnRightTwo.viewTreeObserver.addOnGlobalLayoutListener(layoutChange)
        imgBtnLeft.viewTreeObserver.addOnGlobalLayoutListener(layoutChange)
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.InputLayout, 0, 0)
        init(typedArray)
    }
}