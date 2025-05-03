package com.eliasjuniornino.budgetplanner

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform