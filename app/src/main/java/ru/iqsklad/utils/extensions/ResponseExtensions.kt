package ru.iqsklad.utils.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.dtk.lib.network.DtkApiModel
import ru.dtk.lib.network.ErrorDtkApiModel
import ru.dtk.lib.network.LoadingDtkApiModel
import ru.dtk.lib.network.SuccessDtkApiModel
import ru.iqsklad.data.dto.ui.ErrorUiModel
import ru.iqsklad.data.dto.ui.LoadingUiModel
import ru.iqsklad.data.dto.ui.SuccessUiModel
import ru.iqsklad.data.dto.ui.UiModel
import ru.iqsklad.data.web.response.api.BaseResponse
import ru.iqsklad.data.web.response.api.ErrorResponse
import ru.iqsklad.domain.processor.ErrorProcessor

fun <T : BaseResponse<*>, R> LiveData<DtkApiModel<T>>.mapToResult(
    successAction: (data: T) -> SuccessUiModel<R>,
    errorAction: ((error: ErrorResponse.Error) -> ErrorUiModel)? = null
): LiveData<UiModel<R>> {
    return Transformations.map(this) {
        return@map when (it) {
            is LoadingDtkApiModel -> {
                LoadingUiModel
            }
            is SuccessDtkApiModel<T> -> {
                successAction.invoke(it.data)
            }
            is ErrorDtkApiModel -> {
                errorAction?.invoke(ErrorProcessor.process(it.exception))
            }
        }
    }
}