package com.example.imnuricrestine.models

import com.example.imnuricrestine.data.db.entities.Favorite
import java.util.concurrent.CompletableFuture

typealias OnFavoriteAction = (Favorite?) -> CompletableFuture<Void>

data class OnFavoriteActions(
    val addFavorite: OnFavoriteAction,
    val deleteFavorite: OnFavoriteAction
)