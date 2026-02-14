package com.kenotracker.domain.usecase

import com.kenotracker.domain.model.NumberRecommendation
import com.kenotracker.domain.repository.DrawRepository
import javax.inject.Inject

class GetRecommendationsUseCase @Inject constructor(
    private val repository: DrawRepository
) {
    suspend fun getHotNumbers(count: Int = 9): List<NumberRecommendation> {
        val draws = repository.getAllDrawsOnce()
        if (draws.isEmpty()) return emptyList()

        val frequencyMap = mutableMapOf<Int, Int>()
        draws.forEach { draw ->
            draw.numbers.forEach { number ->
                frequencyMap[number] = (frequencyMap[number] ?: 0) + 1
            }
        }

        val maxHits = frequencyMap.values.maxOrNull() ?: 1

        return frequencyMap.entries
            .sortedByDescending { it.value }
            .take(count)
            .map { (number, hits) ->
                NumberRecommendation(
                    number = number,
                    confidence = (hits.toFloat() / maxHits * 1.5f).coerceIn(0f, 1f),
                    hitCount = hits,
                    hitRate = hits.toFloat() / draws.size * 100,
                    reason = "Hit $hits times (${String.format("%.1f", hits.toFloat() / draws.size * 100)}%)"
                )
            }
    }

    suspend fun getColdNumbers(count: Int = 9): List<NumberRecommendation> {
        val draws = repository.getAllDrawsOnce()
        if (draws.isEmpty()) return emptyList()

        val frequencyMap = mutableMapOf<Int, Int>()
        draws.forEach { draw ->
            draw.numbers.forEach { number ->
                frequencyMap[number] = (frequencyMap[number] ?: 0) + 1
            }
        }

        val allNumbers = (1..80).toSet()
        val neverDrawn = allNumbers - frequencyMap.keys

        val sorted = (neverDrawn.map { it to 0 } + frequencyMap.entries.map { it.key to it.value })
            .sortedBy { it.second }
            .take(count)

        return sorted.map { (number, hits) ->
            NumberRecommendation(
                number = number,
                confidence = if (hits == 0) 0.2f else 0.3f,
                hitCount = hits,
                hitRate = if (draws.isEmpty()) 0f else hits.toFloat() / draws.size * 100,
                reason = if (hits == 0) "Never drawn" else "Only $hits hits"
            )
        }
    }

    suspend fun getTrendNumbers(count: Int = 9): List<NumberRecommendation> {
        val draws = repository.getAllDrawsOnce()
        if (draws.isEmpty()) return emptyList()

        val sortedDraws = draws.sortedByDescending { it.timestamp }

        val metrics = (1..80).associate { number ->
            val recency = calculateRecencyScore(number, sortedDraws)
            val gap = calculateGapScore(number, sortedDraws)
            val frequency = calculateFrequencyScore(number, draws)
            val cluster = calculateClusterScore(number, sortedDraws)

            val score = recency * 0.30f + gap * 0.25f + frequency * 0.25f + cluster * 0.20f

            number to NumberRecommendation(
                number = number,
                confidence = score,
                hitCount = sortedDraws.count { it.numbers.contains(number) },
                details = mapOf(
                    "recency" to recency,
                    "gap" to gap,
                    "cluster" to cluster
                )
            )
        }

        return metrics.values
            .sortedByDescending { it.confidence }
            .take(count)
            .map { rec ->
                rec.copy(reason = buildTrendReason(rec.details))
            }
    }

    private fun calculateRecencyScore(number: Int, sortedDraws: List<com.kenotracker.domain.model.Draw>): Float {
        var weight = 1.0f
        var score = 0f
        val maxWeight = sortedDraws.size.coerceAtMost(50)

        sortedDraws.take(maxWeight).forEach { draw ->
            if (draw.numbers.contains(number)) {
                score += weight
            }
            weight *= 0.92f
        }

        return (score / maxWeight).coerceIn(0f, 1f)
    }

    private fun calculateGapScore(number: Int, sortedDraws: List<com.kenotracker.domain.model.Draw>): Float {
        val roundsSinceHit = sortedDraws.indexOfFirst { it.numbers.contains(number) }
            .let { if (it == -1) sortedDraws.size else it }

        if (roundsSinceHit == sortedDraws.size) return 1f
        return (roundsSinceHit.toFloat() / 8f).coerceIn(0f, 1f)
    }

    private fun calculateFrequencyScore(number: Int, draws: List<com.kenotracker.domain.model.Draw>): Float {
        if (draws.isEmpty()) return 0f
        val totalHits = draws.count { it.numbers.contains(number) }
        val expectedHits = draws.size * 20f / 80f
        return (totalHits / expectedHits).coerceIn(0f, 1f)
    }

    private fun calculateClusterScore(number: Int, sortedDraws: List<com.kenotracker.domain.model.Draw>): Float {
        val relevantDraws = sortedDraws.take(30).filter { it.numbers.contains(number) }
        if (relevantDraws.isEmpty()) return 0.5f

        val avgCoOccurrences = relevantDraws.sumOf {
            it.numbers.count { n -> n != number }
        }.toFloat() / relevantDraws.size

        return (avgCoOccurrences / 5f).coerceIn(0f, 1f)
    }

    private fun buildTrendReason(details: Map<String, Float>): String {
        val reasons = mutableListOf<String>()

        if ((details["recency"] ?: 0f) > 0.6f) reasons.add("recently hot")
        if ((details["gap"] ?: 0f) > 0.7f) reasons.add("overdue")
        if ((details["cluster"] ?: 0f) > 0.6f) reasons.add("clusters well")

        return reasons.joinToString(", ").ifEmpty { "balanced trend" }
    }
}
