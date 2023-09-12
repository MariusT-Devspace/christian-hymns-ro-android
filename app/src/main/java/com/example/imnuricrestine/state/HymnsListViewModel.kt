package com.example.imnuricrestine.state

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.imnuricrestine.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HymnsListViewModel : ViewModel() {
    private var hymnUiStateList = mutableStateListOf<HymnsListItemUiState>()
    private val _hymnUiStateListFlow = MutableStateFlow(hymnUiStateList)

    val hymnUiStateListFlow: StateFlow<List<HymnsListItemUiState>> get() = _hymnUiStateListFlow

    private val _hymnsListItems = MainActivity.hymns.value!!.map { hymn ->
//            var isBookMarked = false
//            if (favorites.value.any { favorite -> favorite.hymn_id == hymn.id })
//                isBookMarked = true
        //hymn.asHymnsListItemUiState(isBookMarked)
        HymnsListItemUiState(
            hymn.index,
            hymn.title,
            false,
            FavoriteAction.ADD_FAVORITE,
            FavoriteIcon.SAVED.icon.imageVector.name
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
            title = hymnUiStateList[id].title,
            isBookMarked = isBookMarked,
            onFavoriteAction = onFavoriteAction,
            icon = icon
        )
    }
    init {
        hymnUiStateList.apply { addAll(_hymnsListItems) }
    }
}
