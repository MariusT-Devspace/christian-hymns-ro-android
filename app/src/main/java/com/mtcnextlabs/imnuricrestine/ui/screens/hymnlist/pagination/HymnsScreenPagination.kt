package com.mtcnextlabs.imnuricrestine.ui.screens.hymnlist.pagination

sealed interface PaginationAction {
    val currentRange: String
    val targetRange: String
    val method: String

    data class Next(
        override val currentRange: String,
        override val targetRange: String
    ) : PaginationAction {
        override val method = "next button"
    }

    data class Previous(
        override val currentRange: String,
        override val targetRange: String
    ) : PaginationAction {
        override val method = "previous button"
    }

    data class JumpToPage(
        val pageIndex: Int,
        override val currentRange: String,
        override val targetRange: String
    ) : PaginationAction {
        override val method = "jump to page button"
    }
}

typealias OnChangePageAction = (PaginationAction) -> Unit
