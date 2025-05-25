package com.mtcnextlabs.imnuricrestine.state

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class FavoriteSnackbarViewModel : ViewModel() {
    private val _snackBarEvent = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val snackBarEvent: SharedFlow<String> = _snackBarEvent.asSharedFlow()

    fun showSnackbar(message: String) {
        _snackBarEvent.tryEmit(message) // or launch to emit in suspend context
    }
}