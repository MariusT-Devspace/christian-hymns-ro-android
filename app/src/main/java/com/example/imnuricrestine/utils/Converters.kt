package com.example.imnuricrestine.utils

import com.example.imnuricrestine.MainActivity
import com.example.imnuricrestine.data.db.entities.Favorite
import com.example.imnuricrestine.data.db.models.HymnWithFavoriteStatus
import com.example.imnuricrestine.models.FavoritesListItem
import com.example.imnuricrestine.models.Hymn
import com.example.imnuricrestine.models.Verse

fun HymnWithFavoriteStatus.asHymn(): Hymn {
    val verses = ArrayList<Verse>()
    var verseTagCount = 0

    for (verse in this.hymnWithLyrics.lyrics) {
        val tag: String = if (verse.is_chorus) CHORUS_TAG else (++verseTagCount).toString()
        verses.add(Verse(verse.verse_text, tag, verse.is_chorus))
    }

    return Hymn(
        this.hymnWithLyrics.hymn.id,
        this.hymnWithLyrics.hymn.hymn_index,
        this.hymnWithLyrics.hymn.title, verses,
        this.isFavorite
    )
}

fun Favorite.asFavoritesListItem(): FavoritesListItem {
    return FavoritesListItem(
        id = this.id,
        hymnId = this.hymn_id,
        index = MainActivity.hymns.value!![this.hymn_id.toInt() - 1].index,
        title = MainActivity.hymns.value!![this.hymn_id.toInt() - 1].title
    )
}
