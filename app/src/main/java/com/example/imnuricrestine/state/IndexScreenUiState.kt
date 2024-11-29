package com.example.imnuricrestine.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import com.example.imnuricrestine.MainActivity.Companion.indexScreenPages
import com.example.imnuricrestine.state.PaginationConfig.totalPages

// State holder saver
val IndexScreenUiStateSaver = Saver<IndexScreenUiState, Map<String, Any>>(
    // Save the state
    save = { state ->
        mapOf(
            "currentPageIndex" to state.page.value.index
        )
    },
    // Restore the state
    restore = { restoredState ->
        IndexScreenUiState().apply {
            val pageIndex = restoredState["currentPageIndex"] as Int
            onChangePageAction(null, pageIndex)
        }
    }
)

// State holder class
class IndexScreenUiState() {
    private val _page: MutableState<Page> = mutableStateOf(indexScreenPages[0])
    private val _paginationAppBarUiState: MutableState<PaginationAppBarUiState> = mutableStateOf(
        PaginationAppBarUiState(
            isPreviousButtonEnabled = false,
            isNextButtonEnabled = true
        )
    )

    // Exposed state properties
    val page: State<Page> = _page
    val paginationAppBarUiState: State<PaginationAppBarUiState> = _paginationAppBarUiState

    // Configure destructuring
    operator fun component1() = page
    operator fun component2() = paginationAppBarUiState
    operator fun component3() = ::onChangePageAction

    // Update page state
    fun onChangePageAction(
        action: PageChangeAction?,
        selectedPage: Int? = null
    ) {
        fun updatePaginationAppBarUiState() {
            when (page.value.index) {
                in 2..<totalPages ->
                    _paginationAppBarUiState.value = paginationAppBarUiState.value.copy(
                        isPreviousButtonEnabled = true,
                        isNextButtonEnabled = true
                    )
                1 ->
                    _paginationAppBarUiState.value = paginationAppBarUiState.value.copy(
                        isPreviousButtonEnabled = false,
                        isNextButtonEnabled = true
                    )
                totalPages ->
                    _paginationAppBarUiState.value = paginationAppBarUiState.value.copy(
                        isPreviousButtonEnabled = true,
                        isNextButtonEnabled = false
                    )
            }
        }

        when (action) {
            PageChangeAction.NEXT -> {
                val index = page.value.index + 1
                _page.value = indexScreenPages[index-1]
                updatePaginationAppBarUiState()
            }

            PageChangeAction.PREVIOUS -> {
                val index = page.value.index - 1
                _page.value = indexScreenPages[index-1]
                updatePaginationAppBarUiState()
            }
            else -> {
                val index = selectedPage ?: page.value.index
                _page.value = indexScreenPages[index-1]
                updatePaginationAppBarUiState()
            }
        }
    }
}