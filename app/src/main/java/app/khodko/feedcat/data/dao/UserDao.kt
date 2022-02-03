package app.khodko.feedcat.data.dao

import androidx.room.*
import app.khodko.feedcat.data.entity.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user WHERE name = :name")
    suspend fun existUser(name: String): List<User>

    @Query("SELECT * FROM user WHERE name = :name AND password = :password")
    suspend fun getUser(name: String, password: String): List<User>

}
