package com.mtcnextlabs.imnuricrestine.state

import kotlin.math.ceil

object PaginationConfig {
    const val pageSize: Int = 99
    val totalPages: Int = ceil((765.toDouble() / pageSize.toDouble())).toInt()
    fun List<HymnsListItemUiState>.getPages(): List<Page> =
        Array(totalPages) { index ->
            Page(this, pageSize, totalPages, index + 1)
        }.toList()
}

// Constants, types and Utility Functions
private fun List<HymnsListItemUiState>.getPageStart(index: Int, pageSize: Int): Int =
    if (index == 1)
        1
    else
        this.find { it.index == ((index - 1) * pageSize).toString() }!!.id + (index - 1)

private fun List<HymnsListItemUiState>.getPageEnd(
    index: Int,
    start: Int,
    pageSize: Int,
    totalPages: Int
): Int = when (index) {
        1 -> pageSize
        totalPages ->
            this[this.size - 1].id

        else ->
            this.indexOf(
                this.find { it.index == (start + pageSize).toString() }
            ) + 1
    }

private fun List<HymnsListItemUiState>.getPageTitle(
    start: Int,
    end: Int
): String =
    "${this[start - 1].index} - ${this[end - 1].index}"

// Data classes, enums and type aliases
data class Page(
    // Parameters to provide
    private val hymnsListItems: List<HymnsListItemUiState>,
    private val pageSize: Int,
    private val totalPages: Int,
    val index: Int,
    // Default parameters
    val start: Int = hymnsListItems.getPageStart(index, pageSize),
    val end: Int = hymnsListItems.getPageEnd(index, start, pageSize, totalPages),
    val title: String = hymnsListItems.getPageTitle(start, end)
)

enum class PageChangeAction {
    NEXT, PREVIOUS, SELECT
}

typealias OnChangePageAction = (action: PageChangeAction, selectedPage: Int?) -> Unit