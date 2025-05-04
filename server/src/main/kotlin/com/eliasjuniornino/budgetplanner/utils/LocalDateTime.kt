package com.eliasjuniornino.budgetplanner.utils

import java.time.Clock
import java.time.LocalDateTime

fun getCurrentLocalDateTime(): LocalDateTime {
    return LocalDateTime.now(Clock.systemDefaultZone())
}