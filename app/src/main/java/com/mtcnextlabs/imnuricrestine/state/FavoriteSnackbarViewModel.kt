package com.mtcnextlabs.imnuricrestine.state

import androidx.lifecycle.ViewModel
import com.mtcnextlabs.imnuricrestine.models.SnackbarData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

typealias ShowSnackbar = (SnackbarData) -> Unit

class FavoriteSnackbarViewModel : ViewModel() {
    private val _snackBarEvent = MutableSharedFlow<SnackbarData>(extraBufferCapacity = 1)
    val snackBarEvent: SharedFlow<SnackbarData> = _snackBarEvent.asSharedFlow()

    fun showSnackbar(snackbarData: SnackbarData) {
        _snackBarEvent.tryEmit(snackbarData)
    }
}