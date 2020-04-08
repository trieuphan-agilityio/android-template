package com.example.template.ui.auth

import android.os.Bundle
import android.view.View
import androidx.biometric.BiometricConstants
import androidx.biometric.BiometricPrompt
import androidx.lifecycle.Observer
import com.example.common.utils.MainExecutor
import com.example.common.utils.hasBiometricEnrolled
import com.example.common.utils.hasSupportBiometric
import com.example.core.data.remote.common.Result
import com.example.core.data.remote.common.isSuccess
import com.example.core.data.remote.request.BaseRequest
import com.example.core.data.remote.response.UserDataResponse
import com.example.template.R
import com.example.template.ui.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginViewModel>(LoginViewModel::class), View.OnClickListener {

    private val executor = MainExecutor()
    private var biometricPrompt: BiometricPrompt? = null

    override fun getLayoutRes(): Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBiometric()
        btnLogin.setOnClickListener(this)
        // Observer get user data
        viewModel.userData.observe(this, userDataObserver)
    }

    /**
     * Setup fingerprint layout
     */
    private fun setupBiometric() {
        if (hasSupportBiometric(this)) {
            if (!hasBiometricEnrolled(this)) { //BIOMETRIC_ERROR_NONE_ENROLLED
                // TODO: handle none enrolled maybe show notify message.
            } else { //BIOMETRIC_SUCCESS
                // TODO: handle enrolled.
                biometricPrompt = BiometricPrompt(this, executor, biometricAuthenticationCallback())
                biometricPrompt?.authenticate(buildPromptInfo())
            }
        }
    }

    /**
     * Listener after setup fingerprint
     */
    private fun biometricAuthenticationCallback(): BiometricPrompt.AuthenticationCallback {
        return object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                // TODO: Handle authenticate success
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricConstants.ERROR_NEGATIVE_BUTTON) biometricPrompt?.cancelAuthentication()
            }
        }
    }

    /**
     * Setup UI popup fingerprint
     */
    private fun buildPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometric_title))
            .setDescription(getString(R.string.biometric_decs))
            .setNegativeButtonText(getString(R.string.biometric_negative_text))
            .build()
    }

    /**
     * Observer get user data
     */
    private val userDataObserver = Observer<Result<UserDataResponse>> {

        if (!it.isSuccess()) {
            it.error?.let { _ ->
                onResponseError(it.error)
            }
        } else {
            // Handle success.
        }
    }

    override fun onClick(v: View?) {
        val body = BaseRequest("e634b09a2f3a7757", "android019", "2VCzFmepI0soVKkZH6C9lg==", "EMAIL", "password")
        viewModel.login(body)
    }
}