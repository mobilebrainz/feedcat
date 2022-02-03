package app.khodko.feedcat.data.dao

import androidx.room.*
import app.khodko.feedcat.data.entity.GameResult

@Dao
interface GameResultDao {

    @Query("SELECT * FROM game_result WHERE user_id = :userId ORDER BY id ASC")
    suspend fun getGameResults(userId: Long): List<GameResult>

    @Query("SELECT * FROM game_result WHERE user_id = :userId ORDER BY id DESC LIMIT 1")
    suspend fun getLastGameResult(userId: Long): List<GameResult>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gameResult: GameResult)

    @Delete
    suspend fun delete(gameResult: GameResult)

}
