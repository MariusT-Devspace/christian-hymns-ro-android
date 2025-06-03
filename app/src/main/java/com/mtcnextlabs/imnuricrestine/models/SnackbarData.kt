package com.mtcnextlabs.imnuricrestine.models

data class SnackbarData(
    val hymnWithFavorite: HymnWithFavorite?,
    val favoritesListItem: FavoritesListItem?,
    val undoAction: Boolean,
    val message: String) {

    constructor(message: String): this(null, null, false, message)

    constructor(
        hymnWithFavorite: HymnWithFavorite?,
        undoAction: Boolean,
        message: String
    ) : this(
        hymnWithFavorite,
        null,
        false,
        message
    )

    constructor(
        favoritesListItem: FavoritesListItem?,
        undoAction: Boolean,
        message: String
    ) : this(
        null,
        favoritesListItem,
        undoAction,
        message
    )
}
