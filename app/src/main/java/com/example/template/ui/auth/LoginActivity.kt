package com.example.template.ui.auth

import android.os.Bundle
import androidx.biometric.BiometricConstants
import androidx.biometric.BiometricPrompt
import com.example.common.utils.MainExecutor
import com.example.common.utils.hasBiometricEnrolled
import com.example.common.utils.hasSupportBiometric
import com.example.template.R
import com.example.template.ui.common.base.BaseActivity

class LoginActivity : BaseActivity<LoginViewModel>(LoginViewModel::class) {

    private val executor = MainExecutor()
    private var biometricPrompt: BiometricPrompt? = null

    override fun getLayoutRes(): Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBiometric();
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

}