package com.example.template.ui.common.view.edittext.validation

abstract class Validate(message: String?) {
    var message: String? = null
    var isValid = false
    abstract fun performValidate(input: String?): Boolean

    init {
        this.message = message
    }
}