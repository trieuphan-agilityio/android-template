package com.example.common.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager

/**
 * Extension method to provide hide keyboard for [Activity].
 */
fun Activity.hideSoftKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(
            Context
            .INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}

    /**
     * Extension method to provide force show keyboard for [Activity].
     */
    fun Activity.showSoftKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     *	Kotlin Extension launching of Activities
     *
     *	// Simple Activities
     *	launchActivity<UserDetailActivity>()
     *
     *	// Add Intent extras
     *	launchActivity<UserDetailActivity> {
     *	    putExtra(INTENT_USER_ID, user.id)
     *	}
     *
     *	// Add custom flags
     *	launchActivity<UserDetailActivity> {
     *	    putExtra(INTENT_USER_ID, user.id)
     *	    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
     *	}
     *
     *	// Add Shared Transistions
     *	val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, avatar, "avatar")
     *	launchActivity<UserDetailActivity>(options = options) {
     *	    putExtra(INTENT_USER_ID, user.id)
     *	}
     *
     *	// Add requestCode for startActivityForResult() call
     *	launchActivity<UserDetailActivity>(requestCode = 1234) {
     *	    putExtra(INTENT_USER_ID, user.id)
     *	}
     */

    inline fun <reified T : Any> Activity.launchActivity (
        requestCode: Int = -1,
        options: Bundle? = null,
        finish: Boolean? = false,
        noinline init: Intent.() -> Unit = {}
    ) {
        val intent = newIntent<T>(this)
        intent.init()
        startActivityForResult(intent, requestCode)
        finish?.let { if (it) finish() }
    }

    inline fun <reified T : Any> Context.launchActivity (
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}
    ) {
        val intent = newIntent<T>(this)
        intent.init()
        startActivity(intent)
    }

    inline fun <reified T : Any> Activity.launchActivity (
        finish: Boolean? = false,
        noinline init: Intent.() -> Unit = {}
    ) {
        val intent = newIntent<T>(this)
        intent.init()
        startActivity(intent)
        finish?.let { if (it) finish() }
    }

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)