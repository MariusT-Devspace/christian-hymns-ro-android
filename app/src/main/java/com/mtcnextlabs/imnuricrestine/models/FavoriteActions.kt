package com.mtcnextlabs.imnuricrestine.models

import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite
import java.util.concurrent.CompletableFuture

typealias FavoriteAction = (Favorite?) -> CompletableFuture<Void>

data class FavoriteActions(
    val addFavorite: FavoriteAction,
    val deleteFavorite: FavoriteAction,
    val undoDelete: FavoriteAction
)