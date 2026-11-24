package com.nexatv.app.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "favourites")
data class FavouriteEntity(
    @PrimaryKey val id: String,
    val title: String,
    val poster: String,
    val type: String
)

@Entity(tableName = "watch_history")
data class WatchHistoryEntity(
    @PrimaryKey val id: String,
    val title: String,
    val poster: String,
    val position: Long,
    val duration: Long,
    val updatedAt: Long
)

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourites")
    fun getAll(): Flow<List<FavouriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: FavouriteEntity)

    @Query("DELETE FROM favourites WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM favourites WHERE id = :id")
    suspend fun getById(id: String): FavouriteEntity?
}

@Dao
interface WatchHistoryDao {

    @Query("SELECT * FROM watch_history ORDER BY updatedAt DESC")
    fun getAll(): Flow<List<WatchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: WatchHistoryEntity)

    @Query("SELECT * FROM watch_history WHERE id = :id")
    suspend fun getById(id: String): WatchHistoryEntity?
}

@Database(
    entities = [
        FavouriteEntity::class,
        WatchHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao

    abstract fun watchHistoryDao(): WatchHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun get(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "nexa_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
