package app.khodko.feedcat.database.dao

import androidx.room.*
import app.khodko.feedcat.database.entity.GameResult

@Dao
interface GameResultDao {

    @Query("SELECT * FROM game_result WHERE user_id = :userId ORDER BY id ASC")
    suspend fun getGameResults(userId: Long): List<GameResult>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gameResult: GameResult)

    @Delete
    suspend fun delete(gameResult: GameResult)

}
