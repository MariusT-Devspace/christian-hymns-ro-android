package com.mtcnextlabs.imnuricrestine.data.db.models;

import androidx.room.Embedded;

public class HymnWithFavoriteStatus {
    @Embedded public HymnWithLyrics hymnWithLyrics;
    public boolean isFavorite;
}
