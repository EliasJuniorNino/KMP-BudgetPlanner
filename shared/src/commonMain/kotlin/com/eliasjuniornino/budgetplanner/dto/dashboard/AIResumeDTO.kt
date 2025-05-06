package com.eliasjuniornino.budgetplanner.dto.dashboard

import kotlinx.serialization.Serializable

@Serializable
data class AIResumeDTO(
    val generalMessage: String,
    val criticalAlertsCount: Int,
    val neutralAlertsCount: Int,
    val suggestionsCount: Int,
)