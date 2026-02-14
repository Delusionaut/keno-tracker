package com.kenotracker.domain.repository

import com.kenotracker.domain.model.Draw
import kotlinx.coroutines.flow.Flow

interface DrawRepository {
    suspend fun saveDraw(draw: Draw): Long
    fun getAllDraws(): Flow<List<Draw>>
    suspend fun getAllDrawsOnce(): List<Draw>
    fun getDrawCount(): Flow<Int>
    suspend fun getDrawCountOnce(): Int
    suspend fun deleteAllDraws()
    suspend fun getRecentDraws(limit: Int): List<Draw>
    suspend fun getLatestDraw(): Draw?
}
