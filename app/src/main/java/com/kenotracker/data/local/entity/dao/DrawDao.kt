package com.kenotracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kenotracker.data.local.entity.DrawEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DrawDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDraw(draw: DrawEntity): Long

    @Query("SELECT * FROM draws ORDER BY timestamp DESC")
    fun getAllDraws(): Flow<List<DrawEntity>>

    @Query("SELECT * FROM draws ORDER BY timestamp DESC")
    suspend fun getAllDrawsOnce(): List<DrawEntity>

    @Query("SELECT COUNT(*) FROM draws")
    fun getDrawCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM draws")
    suspend fun getDrawCountOnce(): Int

    @Query("DELETE FROM draws")
    suspend fun deleteAllDraws()

    @Query("SELECT * FROM draws ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentDraws(limit: Int): List<DrawEntity>

    @Query("SELECT * FROM draws ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestDraw(): DrawEntity?
}
