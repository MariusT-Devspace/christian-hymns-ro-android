package com.example.imnuricrestine.state

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.imnuricrestine.models.Hymn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

typealias UpdateHymnsListItemUiState = (Int, Boolean, FavoriteAction, String) -> Unit

class HymnsListViewModel(val hymns: LiveData<ArrayList<Hymn>>) : ViewModel() {
    private var hymnUiStateList = mutableStateListOf<HymnsListItemUiState>()
    private val _hymnUiStateListFlow = MutableStateFlow(hymnUiStateList)

    val hymnUiStateListFlow: StateFlow<List<HymnsListItemUiState>> get() = _hymnUiStateListFlow

    private val _hymnsListItems = hymns.value!!.map { hymn ->
        HymnsListItemUiState(
            hymn.id.toInt(),
            hymn.index,
            hymn.title,
            hymn.isFavorite,
            if (hymn.isFavorite) FavoriteAction.DELETE_FAVORITE
            else FavoriteAction.ADD_FAVORITE,
            if (hymn.isFavorite) FavoriteIconName.SAVED.name
            else FavoriteIconName.NOT_SAVED.name
        )
    }

    fun updateItem(
        id: Int,
        isBookMarked: Boolean,
        onFavoriteAction: FavoriteAction,
        icon: String
    ) {
        hymnUiStateList[id] = hymnUiStateList[id].copy(
            index = hymnUiStateList[id].index,
            isBookMarked = isBookMarked,
            onFavoriteAction = onFavoriteAction,
            icon = icon
        )
    }
    init {
        hymnUiStateList.apply { addAll(_hymnsListItems) }
    }
}
