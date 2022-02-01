package app.khodko.feedcat.preferences

import android.content.Context
import android.content.SharedPreferences
import app.khodko.feedcat.database.entity.User
import app.khodko.feedcat.preferences.PreferenceHelper.editMe

private const val USER_PREFERENCES = "USER_PREFERENCES"
private const val USER_ID = "USER_ID"
private const val USER_NAME = "NAME"
private const val USER_PASSWORD = "PASSWORD"

object UserPreferences {

    fun getPreferences(context: Context) =
        PreferenceHelper.customPreference(context, USER_PREFERENCES)

    fun saveUser(context: Context, user: User) {
        val prefs = getPreferences(context)
        prefs.userId = user.id
        prefs.userName = user.name
        prefs.userPassword = user.password
    }

    fun getUser(context: Context): User? {
        val prefs = getPreferences(context)
        if (prefs.userId > 0L) {
            val user = User(prefs.userName!!, prefs.userPassword!!)
            user.id = prefs.userId
            return user
        }
        return null
    }

    fun removeUser(context: Context) {
        val prefs = getPreferences(context)
        prefs.editMe {
            it.remove(USER_ID)
            it.remove(USER_NAME)
            it.remove(USER_PASSWORD)
        }
    }

    private var SharedPreferences.userId
        get() = getLong(USER_ID, 0L)
        set(value) {
            editMe { it.putLong(USER_ID, value) }
        }

    private var SharedPreferences.userName
        get() = getString(USER_NAME, "")
        set(value) {
            editMe { it.putString(USER_NAME, value) }
        }

    private var SharedPreferences.userPassword
        get() = getString(USER_PASSWORD, "")
        set(value) {
            editMe { it.putString(USER_PASSWORD, value) }
        }
}