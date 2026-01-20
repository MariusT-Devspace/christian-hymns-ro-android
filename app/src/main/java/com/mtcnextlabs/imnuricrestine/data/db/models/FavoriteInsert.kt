package com.mtcnextlabs.imnuricrestine.data.db.models

import androidx.room.ColumnInfo

data class FavoriteInsert(
    @ColumnInfo(name = "hymn_id")
    val hymnId: Int
)
