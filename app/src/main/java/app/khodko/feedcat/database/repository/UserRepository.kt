package app.khodko.feedcat.database.repository

import androidx.annotation.WorkerThread
import app.khodko.feedcat.database.dao.UserDao
import app.khodko.feedcat.database.entity.User

class UserRepository(private val userDao: UserDao) {

    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    @WorkerThread
    suspend fun delete(user: User) {
        userDao.delete(user)
    }

    @WorkerThread
    suspend fun existUser(name: String) = userDao.existUser(name)

    @WorkerThread
    suspend fun getUser(user: User) = userDao.getUser(user.name, user.password)
}
