package com.example.template.ui.auth

import com.example.template.R
import com.example.template.ui.common.base.BaseActivity

class LoginActivity : BaseActivity<LoginViewModel>(LoginViewModel::class) {

    override fun getLayoutRes(): Int = R.layout.activity_login
}