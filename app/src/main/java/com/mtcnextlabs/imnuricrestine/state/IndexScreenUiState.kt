package com.mtcnextlabs.imnuricrestine.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import com.mtcnextlabs.imnuricrestine.MainActivity.Companion.indexScreenPages
import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite
import com.mtcnextlabs.imnuricrestine.models.Hymn
import com.mtcnextlabs.imnuricrestine.models.OnFavoriteActions
import com.mtcnextlabs.imnuricrestine.state.PaginationConfig.totalPages
// State holder saver
fun indexScreenUiStateSaver(
    hymns: List<Hymn>,
    onFavoriteActions: OnFavoriteActions,
    showSnackbar: (String) -> Unit
) = Saver<IndexScreenUiState, Map<String, Any>>(
    // Save the state
    save = { state ->
        mapOf(
            "currentPageIndex" to state.page.value.number
        )
    },
    // Restore the state
    restore = { restoredState ->
        IndexScreenUiState(
            hymns,
            onFavoriteActions,
            showSnackbar
        ).apply {
            val pageIndex = restoredState["currentPageIndex"] as Int
            onChangePageAction(null, pageIndex)
        }
    }
)

// State holder class
class IndexScreenUiState(
    val hymns: List<Hymn>,
    val onFavoriteActions: OnFavoriteActions,
    val showSnackbar: (String) -> Unit
) {
    private val _hymns = mutableStateOf(hymns)

    private val _page: MutableState<Page> = mutableStateOf(
        indexScreenPages[0]
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
                    hymn.isFavorite,
                    if (hymn.isFavorite) FavoriteAction.DELETE_FAVORITE
                    else FavoriteAction.ADD_FAVORITE,
                    if (hymn.isFavorite) FavoriteIconName.SAVED.name
                    else FavoriteIconName.NOT_SAVED.name
                )
            }
    }

    // Configure destructuring
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
                _page.value = indexScreenPages[index-1]
                updatePaginationAppBarUiState()
            }

            PageChangeAction.PREVIOUS -> {
                val index = page.value.number - 1
                _page.value = indexScreenPages[index-1]
                updatePaginationAppBarUiState()
            }
            else -> {
                val index = selectedPage ?: page.value.number
                _page.value = indexScreenPages[index-1]
                updatePaginationAppBarUiState()
            }
        }
    }

    fun updatePageItems(hymns: List<Hymn>){
        _hymns.value = hymns
    }

    fun addFavorite(favorite: Favorite) {
        onFavoriteActions.addFavorite(favorite)
            .thenRun{
                showSnackbar(
                    "Imnul \"${hymns[favorite.hymn_id - 1].index}. ${hymns[favorite.hymn_id - 1].title}\" salvat la favorite"
                )
            }
    }

    fun deleteFavorite(favorite: Favorite) {
        onFavoriteActions.deleteFavorite(favorite)
            .thenRun {
                showSnackbar("Imnul \"${hymns[favorite.hymn_id].index}. ${hymns[favorite.hymn_id].title}\" șters de la favorite")
            }
    }
}