package com.kenotracker.domain.usecase

import com.kenotracker.domain.model.Draw
import com.kenotracker.domain.repository.DrawRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllDrawsUseCase @Inject constructor(
    private val repository: DrawRepository
) {
    operator fun invoke(): Flow<List<Draw>> {
        return repository.getAllDraws()
    }

    suspend fun getOnce(): List<Draw> {
        return repository.getAllDrawsOnce()
    }
}
