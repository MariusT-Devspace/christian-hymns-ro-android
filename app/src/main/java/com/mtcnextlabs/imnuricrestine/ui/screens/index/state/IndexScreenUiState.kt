package com.mtcnextlabs.imnuricrestine.ui.screens.index.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import com.mtcnextlabs.imnuricrestine.MainActivity.Companion.indexScreenPages
import com.mtcnextlabs.imnuricrestine.models.Hymn
import com.mtcnextlabs.imnuricrestine.ui.screens.index.Page
import com.mtcnextlabs.imnuricrestine.ui.screens.index.PageChangeAction
import com.mtcnextlabs.imnuricrestine.ui.screens.index.PaginationConfig.totalPages

// State holder saver
fun indexScreenUiStateSaver(
    hymns: List<Hymn>
) = Saver<IndexScreenUiState, Map<String, Any>>(
    // Save the state
    save = { state ->
        mapOf(
            "currentPageNumber" to state.page.value.number
        )
    },
    // Restore the state
    restore = { restoredState ->
        IndexScreenUiState(
            hymns
        ).apply {
            val pageNumber = restoredState["currentPageNumber"] as Int
            onChangePageAction(null, pageNumber)
        }
    }
)

// State holder class
class IndexScreenUiState(
    val hymns: List<Hymn>
) {
    private val _hymns = mutableStateOf(hymns)

    private val _page: MutableState<Page> = mutableStateOf(
        indexScreenPages.value[0]
    )

    private val _paginationAppBarUiState: MutableState<PaginationAppBarUiState> = mutableStateOf(
        PaginationAppBarUiState(
            isPreviousButtonEnabled = false,
            isNextButtonEnabled = true
        )
    )

    // Exposed state properties
    val page: State<Page> = _page
    val paginationAppBarUiState: State<PaginationAppBarUiState> = _paginationAppBarUiState

    private val pageItems = derivedStateOf {
        _hymns.value
            .subList(page.value.start - 1, page.value.end)
            .map { hymn ->
                HymnsListItemUiState(
                    hymn.id,
                    hymn.index,
                    hymn.title,
                    hymn.isFavorite
                )
            }
    }

    // Destructuring
    operator fun component1() = page
    operator fun component2() = paginationAppBarUiState
    operator fun component3() = pageItems
    operator fun component4() = ::onChangePageAction
    operator fun component5() = ::updatePageItems


    // Update page state
    fun onChangePageAction(
        action: PageChangeAction?,
        selectedPage: Int? = null
    ) {
        fun updatePaginationAppBarUiState() {
            when (page.value.number) {
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
                val index = page.value.number + 1
                _page.value = indexScreenPages.value[index-1]
                updatePaginationAppBarUiState()
            }

            PageChangeAction.PREVIOUS -> {
                val index = page.value.number - 1
                _page.value = indexScreenPages.value[index-1]
                updatePaginationAppBarUiState()
            }
            else -> {
                val index = selectedPage ?: page.value.number
                _page.value = indexScreenPages.value[index-1]
                updatePaginationAppBarUiState()
            }
        }
    }

    fun updatePageItems(hymns: List<Hymn>){
        _hymns.value = hymns
    }
}