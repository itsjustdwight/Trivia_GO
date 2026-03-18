package dev.csse.dtaylo37.triviago.ui.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GameCategoryDao {
    @Query("SELECT * FROM GameCategory WHERE id = :id")
    fun getGameCategory(id: Long): Flow<GameCategory?>

    @Query("SELECT * FROM GameCategory ORDER BY categoryName COLLATE NOCASE")
    fun getGameCategories(): Flow<List<GameCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGameCategory(gameCategory: GameCategory): Long

    @Update
    suspend fun updateGameCategory(gameCategory: GameCategory): Int

    @Delete
    suspend fun deleteGameCategory(gameCategory: GameCategory): Int
}
