package com.mtcnextlabs.imnuricrestine.models
import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite
import com.mtcnextlabs.imnuricrestine.state.HymnsListItemUiState

data class HymnWithFavorite(
    val hymnListItemUiState: HymnsListItemUiState,
    val favorite: Favorite?
)