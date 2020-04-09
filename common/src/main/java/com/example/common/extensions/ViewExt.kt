package com.example.common.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

/**
 * Inspired from http://kotlinextensions.com/#view
 *
 */


/**
 * RECYCLER VIEW
 */
fun <T : RecyclerView.ViewHolder> T.onClick(flatPosition: Int? = null, event: (view: View, position: Int, type: Int) -> Unit): T {
    this.itemView.setOnClickListener { event.invoke(it, flatPosition ?: this@onClick.adapterPosition, this@onClick.itemViewType) }
    return this
}

fun <T : RecyclerView.ViewHolder> T.onLongClick(event: (view: View, position: Int, type: Int) -> Unit): T {
    itemView.setOnLongClickListener {
        event.invoke(it, adapterPosition, itemViewType)
        true
    }
    return this
}

/**
 * Extension method to show a keyboard for View.
 */
fun View.showSoftKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    this.requestFocus()
    imm?.showSoftInput(this, 0)
}

/**
 * Try to hide the keyboard and returns whether it worked
 * https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
 */
fun View.hideSoftKeyboard(): Boolean {
    try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        if (inputMethodManager != null) {
            return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }
    } catch (ignored: RuntimeException) { }
    return false
}

inline fun EditText.validate(crossinline validator: (String?) -> Boolean): Flowable<Boolean> {
    return Flowable.create({ emitter ->
        emitter.onNext(validator(this.text.toString()))
        this.doAfterTextChanged {
            emitter.onNext(validator(it?.toString()))
        }
    }, BackpressureStrategy.BUFFER)
}

/**
 * The extension property will help shorten code for inflating a viewGroup
 */
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}