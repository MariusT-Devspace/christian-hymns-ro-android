package com.mtcnextlabs.imnuricrestine.ui.screens.hymns.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logHymnsPagination
import com.mtcnextlabs.imnuricrestine.data.hymns.HymnRepository
import com.mtcnextlabs.imnuricrestine.ui.FavoritesActionHelper
import com.mtcnextlabs.imnuricrestine.ui.components.HymnListItemUiState
import com.mtcnextlabs.imnuricrestine.ui.screens.favorites.FavoritesEvent
import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.pagination.Page
import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.pagination.PaginationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HymnListViewModel @Inject constructor(
    hymnRepository: HymnRepository,
    private val favoritesHelper: FavoritesActionHelper
) : ViewModel() {
    private val _eventFlow = Channel<FavoritesEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private val pageSize = 100
    private val _currentPageIndex = MutableStateFlow(0)

    val uiState: StateFlow<HymnsUiState> = combine(
        hymnRepository.hymns.asFlow(),
        _currentPageIndex
    ) { hymns, pageIndex ->
        val groupedHymns = hymns.groupBy { hymn ->
            val numericValue = hymn.number.filter { it.isDigit() }.toIntOrNull() ?: 0
            if (numericValue == 0) 0 else (numericValue / pageSize)
        }
        val sortedBuckets = groupedHymns.keys.sorted()

        val pages = sortedBuckets.map { bucketIndex ->
            val hymnsInBucket = groupedHymns[bucketIndex]!!
            val start = hymnsInBucket.first().number.toInt()
            val end = hymnsInBucket.last().number.toInt()

            Page(
                start = start,
                end = end,
                title = "$start - $end"
            )
        }

        val currentBucketItems = if (pages.isNotEmpty()) {
            val bucketKey = sortedBuckets[pageIndex]
            groupedHymns[bucketKey] ?: emptyList()
        } else {
            emptyList()
        }

        val pageItems = currentBucketItems.map {
            HymnListItemUiState(it.id, it.number, it.title, it.isFavorite)
        }

        HymnsUiState.Success(
            pageItems = pageItems,
            currentPage = pageIndex,
            pages = pages,
        )
    }.flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HymnsUiState.Loading
        )

    fun changePage(action: PaginationAction) {
        val current = _currentPageIndex.value

        when (action) {
            is PaginationAction.Next -> {
                logHymnsPagination(action.method, action.currentRange, action.targetRange)
                _currentPageIndex.value = current + 1
            }
            is PaginationAction.Previous -> {
                logHymnsPagination(action.method, action.currentRange, action.targetRange)
                if (current > 0) _currentPageIndex.value = current - 1
            }
            is PaginationAction.JumpToPage -> {
                logHymnsPagination(action.method, action.currentRange, action.targetRange)
                _currentPageIndex.value = action.pageIndex
            }
        }
    }

    fun toggleFavorite(hymn: HymnListItemUiState) {
        viewModelScope.launch {
            favoritesHelper.toggleFavorite(hymn) { event ->
                _eventFlow.send(event)
            }
        }
    }

    fun undoDelete() {
        viewModelScope.launch {
            favoritesHelper.undoDelete()
        }
    }
}