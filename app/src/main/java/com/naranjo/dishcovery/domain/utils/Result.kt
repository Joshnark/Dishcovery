package com.naranjo.dishcovery.domain.utils

sealed class Result<T> {
    data class Success<T>(val data: T)
    data class Error(val exception: Exception)
}