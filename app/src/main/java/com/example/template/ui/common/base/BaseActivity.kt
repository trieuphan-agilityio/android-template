package com.example.template.ui.common.base

import android.content.SharedPreferences
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseActivity<out VM: BaseViewModel>(clazz: KClass<VM>) : AppCompatActivity() {

    @LayoutRes
    abstract fun getLayoutRes(): Int

    // Lazy Inject ViewModel with clazz type
    open val viewModel: VM by viewModel(clazz)

    // Lazy Inject SharePreference
    open val sharedPref: SharedPreferences by inject()

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