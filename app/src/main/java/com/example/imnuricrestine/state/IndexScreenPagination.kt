package com.example.imnuricrestine.state

import com.example.imnuricrestine.MainActivity
import com.example.imnuricrestine.state.PaginationConfig.PAGE_SIZE
import com.example.imnuricrestine.state.PaginationConfig.TOTAL_PAGES
import kotlin.math.ceil

// Constants and Utility Functions
object PaginationConfig {
    const val PAGE_SIZE: Int = 99
    val TOTAL_PAGES: Int = ceil((765.toDouble() / PAGE_SIZE.toDouble())).toInt()
    val pages: List<Page> = Array(TOTAL_PAGES) { index ->
        Page(index + 1)
    }.toList()
}

private fun getPageStart(index: Int): Int =
    if (index == 1)
        1
    else
        MainActivity.hymns.value!!.find { it.index == ((index - 1) * PAGE_SIZE).toString() }!!.id + (index - 1)

private fun getPageEnd(index: Int, start: Int): Int = when (index) {
    1 -> PAGE_SIZE
    TOTAL_PAGES ->
        MainActivity.hymns.value!![MainActivity.hymns.value!!.size - 1].id.toInt()

    else ->
        MainActivity.hymns.value!!.indexOf(
            MainActivity.hymns.value!!.find { it.index == (start + PAGE_SIZE).toString() }
        ) + 1
}

private fun getPageTitle(start: Int, end: Int): String =
    "${MainActivity.hymns.value!![start - 1].index} - ${MainActivity.hymns.value!![end - 1].index}"

// Data classes, enums and type aliases
data class Page(
    val index: Int,
    val start: Int = getPageStart(index),
    val end: Int = getPageEnd(index, start),
    val title: String = getPageTitle(start, end)
)

enum class PageChangeAction {
    NEXT, PREVIOUS, SELECT
}

typealias OnChangePageAction = (action: PageChangeAction, selectedPage: Int?) -> Unit