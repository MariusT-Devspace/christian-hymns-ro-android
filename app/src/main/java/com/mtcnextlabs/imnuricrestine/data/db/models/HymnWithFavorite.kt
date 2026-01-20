package com.mtcnextlabs.imnuricrestine.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.mtcnextlabs.imnuricrestine.data.db.entities.HymnEntity

data class HymnWithFavorite(
    @Embedded
    val hymnEntity: HymnEntity,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)
