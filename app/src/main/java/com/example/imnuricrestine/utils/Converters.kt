package com.example.imnuricrestine.utils

import com.example.imnuricrestine.MainActivity
import com.example.imnuricrestine.data.db.entities.Favorite
import com.example.imnuricrestine.data.db.entities.HymnWithLyrics
import com.example.imnuricrestine.models.FavoritesListItem
import com.example.imnuricrestine.models.Hymn
import com.example.imnuricrestine.models.Verse

fun HymnWithLyrics.asHymn(): Hymn {
    val verses = ArrayList<Verse>()
    var verseTagCount = 0
    for (verse in this.lyrics) {
        val tag: String = if (verse.is_chorus) CHORUS_TAG else (++verseTagCount).toString()
        verses.add(Verse(verse.verse_text, tag, verse.is_chorus))
    }
    return Hymn(this.hymn.id ,this.hymn.hymn_index, this.hymn.title, verses)
}

//fun Hymn.asHymnsListItemUiState(isBookMarked: Boolean): HymnsListItemUiState {
//    return HymnsListItemUiState(
//        index = this.index,
//        title = this.title,
//        isBookMarked = isBookMarked,
//        onSaveToFavorites = {})
//}

fun Favorite.asFavoritesListItem(): FavoritesListItem {
    return FavoritesListItem(
        id = this.id,
        hymnId = this.hymn_id,
        index = MainActivity.hymns.value!![this.hymn_id.toInt() - 1].index,
        title = MainActivity.hymns.value!![this.hymn_id.toInt() - 1].title
    )
}
