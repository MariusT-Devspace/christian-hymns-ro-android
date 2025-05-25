package com.mtcnextlabs.imnuricrestine.state

import com.mtcnextlabs.imnuricrestine.models.Hymn
import kotlin.math.ceil

object PaginationConfig {
    const val pageSize: Int = 99
    val totalPages: Int = ceil((765.toDouble() / pageSize.toDouble())).toInt()
    fun List<Hymn>.getPages(): List<Page> =
        Array(totalPages) { index ->
            Page(
                index + 1,
                this.getPageStart(index + 1, pageSize),
                this.getPageEnd(index + 1, this.getPageStart(index + 1, pageSize), pageSize, totalPages),
                this.getPageTitle(
                    this.getPageStart(index + 1, pageSize),
                    this.getPageEnd(index + 1, this.getPageStart(index + 1, pageSize), pageSize, totalPages)
                )
            )
        }.toList()
}

// Constants, types and Utility Functions
private fun List<Hymn>.getPageStart(index: Int, pageSize: Int): Int =
    if (index == 1)
        1
    else
        this.find { it.index == ((index - 1) * pageSize).toString() }!!.id + (index - 1)

private fun List<Hymn>.getPageEnd(
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

private fun List<Hymn>.getPageTitle(
    start: Int,
    end: Int
): String =
    "${this[start - 1].index} - ${this[end - 1].index}"

// Data classes, enums and type aliases
data class Page(
    val number: Int,
    val start: Int,
    val end: Int,
    val title: String
)

enum class PageChangeAction {
    NEXT, PREVIOUS, SELECT
}

typealias OnChangePageAction = (action: PageChangeAction, selectedPage: Int?) -> Unit