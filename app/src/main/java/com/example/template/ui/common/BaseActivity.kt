package com.example.template.ui.common

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass

abstract class BaseActivity<out VM: BaseViewModel>(clazz: KClass<VM>) : AppCompatActivity() {

    @LayoutRes
    abstract fun getLayoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
    }

    override fun onDestroy() {
        super.onDestroy()
        // TODO: Handle clear View Model if need.
    }

    protected open fun onResponseError(error: Error?) {
        // TODO: Handle base error here
    }
}