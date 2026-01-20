package com.mtcnextlabs.imnuricrestine.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation
import com.mtcnextlabs.imnuricrestine.data.db.entities.HymnEntity
import com.mtcnextlabs.imnuricrestine.data.db.entities.LyricsEntity

data class HymnDetailWithFavorite(
    @Embedded
    val hymnEntity: HymnEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "hymn_id"
    )
    val lyrics: List<LyricsEntity>,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)