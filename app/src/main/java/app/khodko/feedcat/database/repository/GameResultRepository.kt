
package app.khodko.feedcat.database.repository

import androidx.annotation.WorkerThread
import app.khodko.feedcat.database.dao.GameResultDao
import app.khodko.feedcat.database.entity.GameResult
import kotlinx.coroutines.flow.Flow

class GameResultRepository(private val gameResultDao: GameResultDao) {

    val allGameResults: Flow<List<GameResult>> = gameResultDao.getGameResults()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(gameResult: GameResult) {
        gameResultDao.insert(gameResult)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(gameResult: GameResult){
        gameResultDao.delete(gameResult)
    }
}
