package com.eliasjuniornino.budgetplanner.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseWrapper<T>(
  val data: T,
  val isSuccess: Boolean,
  val message: String?
)