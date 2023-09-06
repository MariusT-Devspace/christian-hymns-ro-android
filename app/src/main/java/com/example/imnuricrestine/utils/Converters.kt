package com.example.imnuricrestine.utils

import com.example.imnuricrestine.MainActivity
import com.example.imnuricrestine.data.db.entities.Favorite
import com.example.imnuricrestine.data.db.entities.HymnWithLyrics
import com.example.imnuricrestine.models.Hymn
import com.example.imnuricrestine.models.HymnsListItem
import com.example.imnuricrestine.models.Verse

fun HymnWithLyrics.asHymn(): Hymn {
    val verses = ArrayList<Verse>()
    var verseTagCount = 0
    for (verse in this.lyrics) {
        var tag: String? = ""
        tag = if (verse.is_chorus) CHORUS_TAG else (++verseTagCount).toString()
        verses.add(Verse(verse.verse_text, tag))
    }
    return Hymn(this.hymn.hymn_index, this.hymn.title, verses)
}

fun Hymn.asHymnsListItem(): HymnsListItem {
    return HymnsListItem(index = this.index, title = this.title)
}

fun Favorite.asHymnsListItem(): HymnsListItem {
    return HymnsListItem(
        index = MainActivity.hymns.value!![this.hymn_id.toInt()].index,
        title = MainActivity.hymns.value!![this.hymn_id.toInt()].title
    )
}
