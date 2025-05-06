package com.eliasjuniornino.budgetplanner.dto.expenses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExpenseDTO(
    val id: Int,
    val name: String,
    val expenseType: ExpenseType,
    val value: Double,
    val valueMultiplier: Double,
    val categoryId: Int? = null,
    val subCategoryId: Int? = null,
    val isEssential: Boolean,
    val description: String? = null,
    val parcelCurrent: Int? = null,
    val parcelTotal: Int? = null,
    val frequencyType: FrequencyType? = null,
    val frequencyValue: String? = null,
    val isDone: Boolean,
    val date: String,
    val dateStart: String,
    val createdAt: String,
    val updatedAt: String,
)

@Serializable
enum class ExpenseType {
    @SerialName("unique")
    UNIQUE,

    @SerialName("frequency")
    FREQUENCY,

    @SerialName("parcel")
    PARCEL
}

@Serializable
enum class FrequencyType {
    @SerialName("unique")
    UNIQUE,

    @SerialName("daily")
    DAILY,

    @SerialName("weekly")
    WEEKLY,

    @SerialName("monthly")
    MONTHLY,

    @SerialName("yearly")
    YEARLY,

    @SerialName("daysOfWeek")
    DAYS_OF_WEEK,

    @SerialName("monthsOfYear")
    MONTHS_OF_YEAR,

    @SerialName("eachTwoMonths")
    EACH_TWO_MONTHS
}