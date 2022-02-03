package app.khodko.feedcat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.khodko.feedcat.data.dao.GameResultDao
import app.khodko.feedcat.data.dao.UserDao
import app.khodko.feedcat.data.entity.GameResult
import app.khodko.feedcat.data.entity.User

@Database(entities = [GameResult::class, User::class], version = 1, exportSchema = false)
abstract class FeedCatRoomDatabase : RoomDatabase() {

    abstract fun gameResultDao(): GameResultDao
    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: FeedCatRoomDatabase? = null

        fun getDatabase(context: Context): FeedCatRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        FeedCatRoomDatabase::class.java,
                        "feed_cat_database"
                    )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
