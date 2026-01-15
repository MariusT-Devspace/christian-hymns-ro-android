package com.mtcnextlabs.imnuricrestine.ui.navigation

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logNavigateToHymnDetails
import com.mtcnextlabs.imnuricrestine.ui.navigation.NavigationActions.onGoBack
import com.mtcnextlabs.imnuricrestine.ui.screens.favorites.FavoritesScreen
import com.mtcnextlabs.imnuricrestine.ui.screens.hymndetail.HymnDetailScreen
import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.HymnsScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object NavigationActions {
    lateinit var onGoBack : () -> Unit
}

@Composable
fun Navigation(
    backStack: NavBackStack<NavKey>,
    padding: PaddingValues,
    indexListState: LazyListState,
    favoritesListState: LazyListState,
    snackbarHostState: SnackbarHostState
) {
    val activity = LocalActivity.current as? ComponentActivity

    NavDisplay(
        entryDecorators = listOf(
            // Default decorators for managing scenes and saving state
            rememberSaveableStateHolderNavEntryDecorator(),
            // View model store decorator
            rememberViewModelStoreNavEntryDecorator()
        ),
        backStack = backStack,
        onBack = {
            onGoBack()
        },
        entryProvider = entryProvider {
            entry<Screen.Hymns> { HymnsScreen(
                listState = indexListState,
                snackbarHostState = snackbarHostState
            ) { id, title ->
                    val encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString())
                    backStack.add(Screen.HymnDetail(id, encodedTitle))
                    logNavigateToHymnDetails(id, "hymns screen")
            } }
            entry<Screen.Favorites> { FavoritesScreen(
                contentPadding = padding,
                listState = favoritesListState,
                snackbarHostState = snackbarHostState
            ) { hymnId, title ->
                val encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString())
                backStack.add(Screen.HymnDetail(hymnId, encodedTitle))
                logNavigateToHymnDetails(hymnId, "favorites screen")
            } }
            entry<Screen.HymnDetail> { key -> HymnDetailScreen(
                key.id,
                initialTitle = key.hymnTitle
            ) {
                onGoBack()
            } }
        },

    )

    onGoBack = {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        } else {
            activity?.finish()
        }
    }
}