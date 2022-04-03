package de.dertev.jumpandsee

import kotlinx.serialization.Serializable

@Serializable
class Question (
    val question: String,
    val answers: List<String>
)