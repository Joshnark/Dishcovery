package com.naranjo.dishcovery.framework.utils

data class Response <T> (
    val data: T,
    val errorMessage: String?
)