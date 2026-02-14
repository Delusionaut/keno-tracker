package com.kenotracker.data.repository

import com.kenotracker.data.local.dao.DrawDao
import com.kenotracker.data.local.entity.DrawEntity
import com.kenotracker.domain.model.Draw
import com.kenotracker.domain.repository.DrawRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrawRepositoryImpl @Inject constructor(
    private val drawDao: DrawDao
) : DrawRepository {

    override suspend fun saveDraw(draw: Draw): Long {
        return drawDao.insertDraw(draw.toEntity())
    }

    override fun getAllDraws(): Flow<List<Draw>> {
        return drawDao.getAllDraws().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getAllDrawsOnce(): List<Draw> {
        return drawDao.getAllDrawsOnce().map { it.toDomain() }
    }

    override fun getDrawCount(): Flow<Int> {
        return drawDao.getDrawCount()
    }

    override suspend fun getDrawCountOnce(): Int {
        return drawDao.getDrawCountOnce()
    }

    override suspend fun deleteAllDraws() {
        drawDao.deleteAllDraws()
    }

    override suspend fun getRecentDraws(limit: Int): List<Draw> {
        return drawDao.getRecentDraws(limit).map { it.toDomain() }
    }

    override suspend fun getLatestDraw(): Draw? {
        return drawDao.getLatestDraw()?.toDomain()
    }

    private fun Draw.toEntity(): DrawEntity {
        return DrawEntity(
            id = id,
            timestamp = timestamp,
            numbers = numbers.joinToString(",")
        )
    }

    private fun DrawEntity.toDomain(): Draw {
        return Draw(
            id = id,
            timestamp = timestamp,
            numbers = numbers.split(",").map { it.trim().toInt() }.sorted()
        )
    }
}
