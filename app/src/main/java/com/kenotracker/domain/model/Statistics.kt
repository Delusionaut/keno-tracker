package com.kenotracker.domain.model

data class Statistics(
    val totalDraws: Int = 0,
    val totalNumbers: Int = 0,
    val averageHitsPerDraw: Float = 0f,
    val hotNumbers: List<NumberRecommendation> = emptyList(),
    val coldNumbers: List<NumberRecommendation> = emptyList(),
    val trendNumbers: List<NumberRecommendation> = emptyList()
)
