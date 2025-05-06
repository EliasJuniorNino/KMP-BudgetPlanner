package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.dto.dashboard.AIResumeDTO

data class AIResumeModel(
    var generalMessage: String,
    var criticalAlertsCount: Int,
    var neutralAlertsCount: Int,
    var suggestionsCount: Int
) {
    fun toDTO() = AIResumeDTO(
        generalMessage,
        criticalAlertsCount,
        neutralAlertsCount,
        suggestionsCount
    )
}
