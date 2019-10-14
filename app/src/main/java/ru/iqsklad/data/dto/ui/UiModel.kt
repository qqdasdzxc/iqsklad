package ru.iqsklad.data.dto.ui

sealed class UiModel<out T>
object LoadingUiModel : UiModel<Nothing>()
class SuccessUiModel<out T>(val data: T) : UiModel<T>()
class ErrorUiModel(val error: String?) : UiModel<Nothing>()