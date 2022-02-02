package app.khodko.feedcat.database.repository

import androidx.annotation.WorkerThread
import app.khodko.feedcat.database.dao.GameResultDao
import app.khodko.feedcat.database.entity.GameResult

class GameResultRepository(private val gameResultDao: GameResultDao) {

    @WorkerThread
    suspend fun insert(gameResult: GameResult) {
        gameResultDao.insert(gameResult)
    }

    @WorkerThread
    suspend fun delete(gameResult: GameResult) {
        gameResultDao.delete(gameResult)
    }

    @WorkerThread
    suspend fun getGameResults(userId: Long) = gameResultDao.getGameResults(userId)

    @WorkerThread
    suspend fun getLastGameResult(userId: Long) = gameResultDao.getLastGameResult(userId)
}
