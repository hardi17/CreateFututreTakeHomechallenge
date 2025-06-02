package com.createfuture.takehome.utils

/*
* Sealed interface to represent loading, success,
* and error states for UI data handling.
* */
sealed interface Uistate<out T> {
    object Loading : Uistate<Nothing>
    data class Success<T>(val result: T) : Uistate<T>
    data class Error(val message: String?) : Uistate<Nothing>
}