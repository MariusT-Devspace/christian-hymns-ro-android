package com.example.imnuricrestine.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.imnuricrestine.MainActivity
import com.example.imnuricrestine.state.PaginationConfig.PAGE_SIZE
import com.example.imnuricrestine.state.PaginationConfig.TOTAL_PAGES
import com.example.imnuricrestine.state.PaginationConfig.pages
import kotlin.math.ceil

object PaginationConfig {
    const val PAGE_SIZE: Int = 99
    val TOTAL_PAGES: Int = ceil((765.toDouble() / PAGE_SIZE.toDouble())).toInt()
    val pages: Array<Page> = Array(TOTAL_PAGES) { index ->
        Page(index + 1)
    }
}

data class Page(
    val index: Int,
    val start: Int = if (index == 1) 1
    else (
        MainActivity.hymns.value!!.find{ it.index == ((index - 1) * PAGE_SIZE).toString() }!!.id
    ) + (index - 1),
    val end: Int = when(index) {
        1 -> PAGE_SIZE
        TOTAL_PAGES ->
            MainActivity.hymns.value!![MainActivity.hymns.value!!.size - 1].id.toInt()
        else ->
            MainActivity.hymns.value!!.indexOf(
                MainActivity.hymns.value!!.find { it.index == (start + PAGE_SIZE).toString() }
        ) + 1
    },
    val title: String = "${MainActivity.hymns.value!![start - 1].index} - ${MainActivity.hymns.value!![end - 1].index}"
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