package ru.turizmryazan.repository

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import ru.turizmryazan.model.models.AuthorizationAnswer
import ru.turizmryazan.utils.Constants

class PreferencesRepository(app: Application) {

    val USER_ID = "userId"
    val USER_NAME = "userName"
    val USER_EMAIL = "userEmail"
    val USER_EMAIL_VERIFIED_AT = "userEmailVerefiedAt"
    val USER_REGION = "userRegion"
    val USER_AGE = "userAge"
    val USER_SEX = "userSex"
    val TOKEN_TYPE = "tokenType"
    val EXPIRIES_IN = "expiriesIn"
    val ACCESS_TOKEN = "accessToken"
    val REFRESH_TOKEN = "refreshToken"

    val sharedPreferences: SharedPreferences
            = app.getSharedPreferences(Constants.NAME_FILE_PREFERENCES, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun setAuthorizationData(answer: AuthorizationAnswer?) {
        answer?.let {
            it.user?.let { user ->
                editor.putString(USER_ID, user.id)
                editor.putString(USER_NAME, user.name)
                editor.putString(USER_EMAIL, user.email)
                editor.putString(USER_EMAIL_VERIFIED_AT, user.emailVerifiedAt)
                editor.putString(USER_REGION, user.region)
                editor.putString(USER_AGE, user.age)
                editor.putString(USER_SEX, user.sex)
                editor.putString(TOKEN_TYPE, it.tokenType)
                editor.putInt(EXPIRIES_IN, it.expiresIn ?: 60)
                editor.putString(ACCESS_TOKEN, it.accessToken)
                editor.putString(REFRESH_TOKEN, it.refreshToken)
                editor.apply()
            }
        }
    }
}