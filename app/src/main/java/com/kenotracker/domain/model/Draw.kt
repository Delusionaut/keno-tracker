package com.kenotracker.domain.model

data class Draw(
    val id: Long = 0,
    val timestamp: Long,
    val numbers: List<Int>
)
