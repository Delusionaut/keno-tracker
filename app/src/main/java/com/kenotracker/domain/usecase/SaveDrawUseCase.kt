package com.kenotracker.domain.usecase

import com.kenotracker.domain.model.Draw
import com.kenotracker.domain.repository.DrawRepository
import javax.inject.Inject

class SaveDrawUseCase @Inject constructor(
    private val repository: DrawRepository
) {
    suspend operator fun invoke(numbers: List<Int>): Long {
        val draw = Draw(
            timestamp = System.currentTimeMillis(),
            numbers = numbers.sorted()
        )
        return repository.saveDraw(draw)
    }
}
