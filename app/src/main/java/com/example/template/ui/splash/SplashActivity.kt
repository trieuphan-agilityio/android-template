package com.example.template.ui.splash

import com.example.template.R
import com.example.template.ui.common.base.BaseActivity

class SplashActivity : BaseActivity<SplashViewModel>(SplashViewModel::class) {
    override fun getLayoutRes(): Int = R.layout.activity_splash
}