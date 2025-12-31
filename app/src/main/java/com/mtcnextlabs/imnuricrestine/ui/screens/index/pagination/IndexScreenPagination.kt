package com.mtcnextlabs.imnuricrestine.ui.screens.index.pagination

import com.mtcnextlabs.imnuricrestine.models.Hymn
import com.mtcnextlabs.imnuricrestine.ui.screens.index.pagination.PaginationConfig.PAGE_SIZE
import com.mtcnextlabs.imnuricrestine.ui.screens.index.pagination.PaginationConfig.totalPages
import kotlin.collections.indexOf
import kotlin.math.ceil

sealed interface PaginationAction {
    data object Next : PaginationAction
    data object Previous : PaginationAction
    data class JumpToPage(val pageIndex: Int) : PaginationAction
}

typealias OnChangePageAction = (PaginationAction) -> Unit

object PaginationConfig {
    const val PAGE_SIZE: Int = 99
    val totalPages: Int = ceil((765.toDouble() / PAGE_SIZE.toDouble())).toInt()
    fun List<Hymn>.getPages(): List<Page> =
        Array(totalPages) { index ->
            Page(
                this.getPageStart(index),
                this.getPageEnd(index, this.getPageStart(index)),
                this.getPageTitle(
                    this.getPageStart(index),
                    this.getPageEnd(index, this.getPageStart(index))
                )
            )
        }.toList()
}

// Constants, types and Utility Functions
private fun List<Hymn>.getPageStart(index: Int): Int =
    if (index == 0)
        0
    else
        this.indexOf(
            this.find { it.index == ((index) * PAGE_SIZE).toString() }
        ) + (index)

private fun List<Hymn>.getPageEnd(
    pageIndex: Int,
    start: Int,
): Int = when (pageIndex) {
        0 -> PAGE_SIZE - 1

        totalPages - 1 ->
            this.size - 1

        else ->
            this.indexOf(
                this.find { it.index == (start + 1 + PAGE_SIZE).toString() }
            )
    }

private fun List<Hymn>.getPageTitle(
    start: Int,
    end: Int
): String =
    "${this[start].index} - ${this[end].index}"