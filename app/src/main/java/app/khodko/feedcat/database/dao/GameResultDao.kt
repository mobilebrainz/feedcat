
package app.khodko.feedcat.database.dao

import androidx.room.*
import app.khodko.feedcat.database.entity.GameResult
import kotlinx.coroutines.flow.Flow

@Dao
interface GameResultDao {

    @Query("SELECT * FROM game_result ORDER BY id ASC")
    fun getGameResults(): Flow<List<GameResult>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gameResult: GameResult)

    @Delete
    suspend fun delete(gameResult: GameResult)

    @Query("DELETE FROM game_result")
    suspend fun deleteAll()
}
