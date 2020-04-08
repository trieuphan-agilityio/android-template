package com.example.core.data.remote.request

/**
 * @property deviceId User device identity
 * @property principal Username|email|phone number
 * @property credential Password credentials
 * @property validationType Validation type of Email|Password
 * @property credentialType Credential type of password|fingerprint
 */
class BaseRequest(
    var deviceId: String,
    val principal: String,
    val credential: String,
    var validationType: String = VALIDATION_EMAIL,
    var credentialType: String = PASSWORD_AUTHENTICATE
) {
    companion object {
        const val PASSWORD_AUTHENTICATE = "password"
        const val FINGER_PRINT_AUTHENTICATE = "fingerprint"
        const val VALIDATION_EMAIL = "EMAIL"
        const val VALIDATION_SMS = "SMS"
    }
}