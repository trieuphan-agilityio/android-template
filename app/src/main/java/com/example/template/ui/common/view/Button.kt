package com.example.template.ui.common.view

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.template.R

open class Button(context: Context, attrs: AttributeSet) : AppCompatButton(
    context,
    attrs
) {
    companion object {
        const val RELATIVE_LAYOUT = 0
        const val CONSTRAIN_LAYOUT = 1
    }

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @IntDef(
        RELATIVE_LAYOUT,
        CONSTRAIN_LAYOUT
    )
    annotation class TypeLayoutDef

    private var isFull: Boolean = false
    private var parentId = -1

    @TypeLayoutDef
    var typeLayout: Int? = -1

    var marginValue = 0

    init {
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.Button, 0, 0)
        parentId = typedArray.getResourceId(R.styleable.Button_parentId, -1)
        typeLayout =
            typedArray.getInteger(
                R.styleable.Button_typeLayout,
                RELATIVE_LAYOUT
            )
        marginValue = typedArray.getDimensionPixelSize(R.styleable.Button_margin, 0)
        typedArray.recycle()
    }

    /**
     * Show background by status
     */
    fun showBackgroundByStatus(isFull: Boolean) {
        this.isFull = isFull
        var layoutParams: ViewGroup.LayoutParams? = null

        when (typeLayout) {
            RELATIVE_LAYOUT -> {
                layoutParams = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)

                if (!isFull) {
                    layoutParams.setMargins(marginValue, marginValue, marginValue, marginValue)
                }
            }

            CONSTRAIN_LAYOUT -> {
                layoutParams = ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                layoutParams.bottomToBottom = parentId

                if (!isFull) {
                    layoutParams.setMargins(marginValue, marginValue, marginValue, marginValue)
                }
            }
        }

        if (isFull) {
            this.setBackgroundResource(R.drawable.selector_btn_orange_no_corner)
        } else {
            this.setBackgroundResource(R.drawable.selector_btn_orange)
        }
        this.layoutParams = layoutParams
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener {
            l?.onClick(it)
            showBackgroundByStatus(false)
        }
    }

    fun setColorFilter(colorRes: Int) {
        val drawables = compoundDrawables
        for (drawable in drawables) {
            drawable?.setColorFilter(
                ContextCompat.getColor(context, colorRes),
                PorterDuff.Mode.SRC_ATOP
            )
        }
    }
}