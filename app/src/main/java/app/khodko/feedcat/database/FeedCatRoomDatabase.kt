
package app.khodko.feedcat.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.khodko.feedcat.database.dao.GameResultDao
import app.khodko.feedcat.database.entity.GameResult
import kotlinx.coroutines.CoroutineScope

@Database(entities = [GameResult::class], version = 1, exportSchema = false)
abstract class FeedCatRoomDatabase : RoomDatabase() {

    abstract fun gameResultDao(): GameResultDao

    companion object {

        @Volatile
        private var INSTANCE: FeedCatRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): FeedCatRoomDatabase {
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
