package com.mtcnextlabs.imnuricrestine.ui

import com.mtcnextlabs.imnuricrestine.ui.screens.favorites.FavoritesUiState
import com.mtcnextlabs.imnuricrestine.models.Hymn
import com.mtcnextlabs.imnuricrestine.models.Verse
import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.pagination.Page
import com.mtcnextlabs.imnuricrestine.ui.components.HymnListItemUiState
import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.state.HymnsUiState

object HymnsScreenPreviewData {
    val hymnListItems = listOf(
        HymnListItemUiState(
            id = 1,
            number = "1",
            title = "Plecaţi-vă lui Dumnezeu!",
            isFavorite = false
        ),
        HymnListItemUiState(
            id = 49,
            number = "49",
            title = "Slavă şi glorie lui Dumnezeu! (canon)",
            isFavorite = false
        ),
        HymnListItemUiState(
            id = 61,
            number = "61",
            title = "Eu am un loc ce-mi place mult",
            isFavorite = true
        ),
        HymnListItemUiState(
            id = 121,
            number = "262",
            title = "Să păzesc, Isuse-nvaţă-mă, Legea Ta!",
            isFavorite = false
        ),
        HymnListItemUiState(
            id = 664,
            number = "664A",
            title = "Ţi-aducem, Doamne-acest copil!",
            isFavorite = false
        )
    )

    // Pre-built states
    val hymnListStateSuccess = HymnsUiState.Success(
        hymnListItems, 0, listOf(Page(1, 99, "1 - 99"))
    ) as HymnsUiState
}

object HymnDetailScreenPreviewData {
    val hymns = arrayListOf(
        Hymn(
            14,
            "14",
            "Binecuvântă pe Domnul!",
            arrayListOf(
                Verse(
                    "Binecuvântă pe Domnul, binecuvântă-al Său Nume\n" +
                            "Şi nu uita niciodată cum S-a jertfit pentru lume!\n" +
                            "Slavă, laudă Lui, laudă suflete-ntruna,\n" +
                            "Cântă, suflete-al meu, binecuvântă-L mereu!",
                    "1",
                    true,
                ),
                Verse(
                    "El a plătit pentru-al tău păcat, \n" +
                            "Cu sânge sfânt vina ţi-a spălat,\n" +
                            "Pune-I în cale covor de-osanale \n" +
                            "Şi cântecul inimii tale!",
                    "2",
                    false,
                ),
                Verse(
                    "Te-a-ncununat bunătatea Sa, \n" +
                            "Ţi-a risipit suferinţa grea,\n" +
                            "Pune-I în cale covor de petale \n" +
                            "Din florile dragostei tale!",
                    "3",
                    false,
                ),
                Verse(
                    "Aripi de vultur ţi-a dat să zbori, \n" +
                            "Ţi-a luminat anii trecători,\n" +
                            "Pune-I în cale psaltiri şi chimvale \n" +
                            "Şi cântă-ndurările Sale!",
                    "4",
                    false,
                )
            ),
            false
        ),
        Hymn(
            129,
            "129",
            "Pe o cruce răstignit",
            arrayListOf(
                Verse(
                    "Pe o cruce răstignit,\n" +
                            "Pentru-al lumii greu păcat,\n" +
                            "Domnul nostru-a coborât\n" +
                            "În mormânt, nevinovat.",
                    "1",
                    false,
                ),

            ),
            false
        ),
        Hymn(
            664,
            "664A",
            "Ţi-aducem, Doamne-acest copil!",
            arrayListOf(
                Verse(
                    "Cu binecuvântarea Ta\n" +
                            "Zideşte-ne deplin,\n" +
                            "Să fim, când Tu ne vei chema,\n" +
                            "Cu Tine-n veci! Amin.",
                    "1",
                    false,
                ),
                Verse(
                    "Trimite, Doamne, îngeri buni\n" +
                            "Să-l poarte, iubitor,\n" +
                            "Spre cerul plin de mari minuni\n" +
                            "Să fii al lui Păstor!",
                    "2",
                    false,
                ),
                Verse(
                    "I-ndreaptă ochii către cer\n" +
                            "Să-nvingă lumea rea,\n" +
                            "Să-nveţe-al dragostei mister,\n" +
                            "Pe Tine-a Te urma!",
                    "3",
                    false,
                ),
                Verse(
                    "Păstrează-l Tu, în grija Ta,\n" +
                            "Să crească-n har mereu,\n" +
                            "Curat, cu toată inima\n" +
                            "Iubind pe Dumnezeu!",
                    "4",
                    false,
                ),
                Verse(
                    "Ţi-aducem, Doamne-acest copil\n" +
                            "Să-l binecuvântezi,\n" +
                            "Cu mâna Ta un cer senin\n" +
                            "Deasupra lui s-aşezi!",
                    "5",
                    false,
                )
            ),
            false
        )
    )
}

object FavoritesScreenPreviewData {
    val favoriteListItems = listOf(
        HymnListItemUiState(
            id = 1,
            number = "1",
            title = "Plecaţi-vă lui Dumnezeu!",
            isFavorite = true
        ),
        HymnListItemUiState(
            id = 49,
            number = "49",
            title = "Slavă şi glorie lui Dumnezeu! (canon)",
            isFavorite = true
        ),
        HymnListItemUiState(
            id = 61,
            number = "61",
            title = "Eu am un loc ce-mi place mult",
            isFavorite = true
        ),
        HymnListItemUiState(
            id = 121,
            number = "262",
            title = "Să păzesc, Isuse-nvaţă-mă, Legea Ta!",
            isFavorite = true
        ),
        HymnListItemUiState(
            id = 664,
            number = "664A",
            title = "Ţi-aducem, Doamne-acest copil!",
            isFavorite = true
        )
    )

    // Pre-built states
    val favoritesStateSuccess = FavoritesUiState.Success(
        favoriteListItems
    )
}