package app.khodko.feedcat

import android.app.Application
import app.khodko.feedcat.data.FeedCatRoomDatabase
import app.khodko.feedcat.data.repository.GameResultRepository
import app.khodko.feedcat.data.repository.UserRepository

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    val database by lazy { FeedCatRoomDatabase.getDatabase(this) }
    val gameResultRepository by lazy { GameResultRepository(database.gameResultDao()) }
    val userRepository by lazy {UserRepository(database.userDao())}

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}