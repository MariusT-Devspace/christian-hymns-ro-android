package com.example.imnuricrestine.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.imnuricrestine.state.PaginationConfig.PAGE_SIZE
import com.example.imnuricrestine.state.PaginationConfig.TOTAL_PAGES
import com.example.imnuricrestine.state.PaginationConfig.pages

object PaginationConfig {
    const val PAGE_SIZE: Int = 99
    const val TOTAL_PAGES: Int = 920 / PAGE_SIZE
    val pages: Array<Page> = Array(TOTAL_PAGES) { index ->
        Page(index + 1)
    }
}

data class Page(
    val index: Int,
    val start: Int = if (index == 1) 1 else ((index - 1) * PAGE_SIZE) + (index - 1),
    val end: Int = if (index == 1) PAGE_SIZE else start + PAGE_SIZE,
    val title: String = "$start - $end"
)

enum class PageChangeAction {
    NEXT, PREVIOUS, SELECT
}

typealias OnChangePageAction = (action: PageChangeAction, selectedPage: Int?) -> Unit

class IndexScreenUiState {
    private val _page: MutableState<Page> = mutableStateOf(pages[0])
    private val _paginationAppBarUiState: MutableState<PaginationAppBarUiState> = mutableStateOf(
        PaginationAppBarUiState(
            isPreviousButtonEnabled = false,
            isNextButtonEnabled = true
        )
    )

    private val page: State<Page> = _page
    private val paginationAppBarUiState: State<PaginationAppBarUiState> = _paginationAppBarUiState

    operator fun component1() = page
    operator fun component2() = paginationAppBarUiState
    operator fun component3() = ::onChangePageAction

    private fun onChangePageAction(
        action: PageChangeAction,
        selectedPage: Int? = null
    ) {
        when (action) {
            PageChangeAction.NEXT -> {
                val index = page.value.index + 1
                _page.value = pages[index-1]
                updatePaginationAppBarUiState()
            }

            PageChangeAction.PREVIOUS -> {
                val index = page.value.index - 1
                _page.value = pages[index-1]
                updatePaginationAppBarUiState()
            }

            PageChangeAction.SELECT -> {
                val index = selectedPage ?: page.value.index
                _page.value = pages[index-1]
                updatePaginationAppBarUiState()
            }
        }
    }

    private fun updatePaginationAppBarUiState() {
        when (page.value.index) {
            in 2..<TOTAL_PAGES ->
                _paginationAppBarUiState.value = paginationAppBarUiState.value.copy(
                    isPreviousButtonEnabled = true,
                    isNextButtonEnabled = true
                )
            1 ->
                _paginationAppBarUiState.value = paginationAppBarUiState.value.copy(
                    isPreviousButtonEnabled = false,
                    isNextButtonEnabled = true
                )
            TOTAL_PAGES ->
                _paginationAppBarUiState.value = paginationAppBarUiState.value.copy(
                    isPreviousButtonEnabled = true,
                    isNextButtonEnabled = false
                )
        }
    }
}