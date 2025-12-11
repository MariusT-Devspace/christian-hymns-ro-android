package com.mtcnextlabs.imnuricrestine.models

import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite
import com.mtcnextlabs.imnuricrestine.ui.screens.index.state.HymnsListItemUiState

data class HymnWithFavorite(
    val hymnListItemUiState: HymnsListItemUiState,
    val favorite: Favorite?
)