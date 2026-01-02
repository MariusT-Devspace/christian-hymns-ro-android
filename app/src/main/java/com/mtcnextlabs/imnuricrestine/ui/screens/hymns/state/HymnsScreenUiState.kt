package com.mtcnextlabs.imnuricrestine.ui.screens.hymns.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logIndexNavigation
import com.mtcnextlabs.imnuricrestine.models.Hymn
import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.pagination.Page
import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.pagination.PaginationAction
import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.pagination.PaginationConfig.totalPages

// State holder saver
fun indexScreenUiStateSaver(
    hymns: List<Hymn>
) = Saver<HymnsScreenUiState, Map<String, Any>>(
    // Save the state
    save = { state ->
        mapOf(
            "currentPageIndex" to state.currentPageIndex
        )
    },
    // Restore the state
    restore = { restoredState ->
        HymnsScreenUiState(
            hymns
        ).apply {
            val pageIndex = restoredState["currentPageIndex"] as Int
            setPage(pageIndex)
        }
    }
)

// State holder class
class HymnsScreenUiState(
    val hymns: List<Hymn>
) {
    private val _hymns = mutableStateOf(hymns)

    private val _pages = mutableStateOf<List<Page>>(emptyList())

    private val _paginationAppBarUiState: MutableState<PaginationAppBarUiState> = mutableStateOf(
        PaginationAppBarUiState(
            isPreviousButtonEnabled = false,
            isNextButtonEnabled = true
        )
    )

    // Exposed state properties
    val paginationAppBarUiState: State<PaginationAppBarUiState> = _paginationAppBarUiState
    val pages : State<List<Page>> = _pages

    var currentPageIndex by mutableIntStateOf(0)
        private set
    private val pageItems = derivedStateOf {
        _hymns.value
            .subList(pages.value[currentPageIndex].start, pages.value[currentPageIndex].end + 1)
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
    operator fun component1() = currentPageIndex
    operator fun component2() = pages
    operator fun component3() = pageItems
    operator fun component4() = paginationAppBarUiState
    operator fun component5() = ::onPaginationAction
    operator fun component6() = ::updatePages
    operator fun component7() = ::updatePageItems

    // Update page state
    fun onPaginationAction(
        action: PaginationAction
    ) {
        when (action) {
            PaginationAction.Next -> {
                logIndexNavigation("next button", pages.value[currentPageIndex].title, pages.value[currentPageIndex+1].title)
                setPage(currentPageIndex + 1)
            }

            PaginationAction.Previous -> {
                logIndexNavigation("previous button", pages.value[currentPageIndex].title, pages.value[currentPageIndex-1].title)
                setPage(currentPageIndex - 1)
            }

            is PaginationAction.JumpToPage -> {
                logIndexNavigation("select page button", pages.value[currentPageIndex].title, pages.value[action.pageIndex].title)
                setPage(action.pageIndex)
            }
        }
    }

    fun setPage(index: Int) {
        currentPageIndex = index
        updatePaginationAppBarUiState()
    }

    private fun updatePaginationAppBarUiState() {
        when (currentPageIndex) {
            in 1..< totalPages - 1 ->
                _paginationAppBarUiState.value = paginationAppBarUiState.value.copy(
                    isPreviousButtonEnabled = true,
                    isNextButtonEnabled = true
                )
            0 ->
                _paginationAppBarUiState.value = paginationAppBarUiState.value.copy(
                    isPreviousButtonEnabled = false,
                    isNextButtonEnabled = true
                )
            totalPages - 1 ->
                _paginationAppBarUiState.value = paginationAppBarUiState.value.copy(
                    isPreviousButtonEnabled = true,
                    isNextButtonEnabled = false
                )
        }
    }

    fun updatePages(pages: List<Page>) {
        _pages.value = pages
    }

    fun updatePageItems(hymns: List<Hymn>){
        _hymns.value = hymns
    }
}