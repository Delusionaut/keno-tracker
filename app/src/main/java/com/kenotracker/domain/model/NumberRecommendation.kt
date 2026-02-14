package com.kenotracker.domain.model

data class NumberRecommendation(
    val number: Int,
    val confidence: Float,
    val hitCount: Int = 0,
    val hitRate: Float = 0f,
    val reason: String = "",
    val details: Map<String, Float> = emptyMap()
)
