package com.example.template.ui.common.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.template.R

open class AlertView(
    context: Context,
    private val title: String,
    private val message: String,
    private val posActionHandler: ActionHandler? = null,
    private val navActionHandler: ActionHandler? = null
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_alert)

        // TODO: define the action button here.
    }

    class ActionHandler(val title: String, val onAction: (() -> Unit)? = null)
}