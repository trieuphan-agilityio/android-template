package com.example.template.ui.splash

import android.os.Bundle
import com.example.common.extensions.launchActivity
import com.example.template.R
import com.example.template.ui.auth.LoginActivity
import com.example.template.ui.common.base.BaseActivity

class SplashActivity : BaseActivity<SplashViewModel>(SplashViewModel::class) {
    override fun getLayoutRes(): Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchActivity<LoginActivity>(true)
    }
}