package app.khodko.feedcat

import android.app.Application
import app.khodko.feedcat.database.FeedCatRoomDatabase
import app.khodko.feedcat.database.repository.GameResultRepository
import app.khodko.feedcat.database.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { FeedCatRoomDatabase.getDatabase(this, applicationScope) }
    val gameResultRepository by lazy { GameResultRepository(database.gameResultDao()) }
    val userRepository by lazy {UserRepository(database.userDao())}

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}