package com.example.backhandler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.findNavController
import com.example.backhandler.ui.theme.BackHandlerTheme
import kotlinx.serialization.Serializable

class FragmentB : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            BackHandlerTheme {

                val navController = rememberNavController()


                /**
                 * Example 1
                 *
                 * There are two screens within FragmentB: B and C
                 *
                 * The goal regarding navigation would be for the user to navigate from
                 * MainFragment -> FragmentB (ScreenB) -> FragmentB (ScreenC)
                 *
                 * Once the user is on ScreenC, tapping hardware back is expected to take them
                 * back to ScreenB and then back to MainFragment
                 *
                 * In this example if the user does not background the app, we actually get the desired
                 * behavior by default. If the user backgrounds the app while on ScreenC and then
                 * taps taps hardware back, they are instead taken all the way back to MainFragment
                 * because the MainActivity OnBackPressedCallback is triggered which navigates back
                 * using the Fragment NavController rather than the compose NavController.
                 *
                 * If we try to manually implement the behavior using this BackHandler you can see the
                 * same results
                 */
//                BackHandler {
//                    if (!navController.popBackStack()) {
//                        findNavController().popBackStack()
//                    }
//                }

                /**
                 * Using this workaround, we can achieve the desired behavior
                 */
//                BackHandlerWorkaround {
//                    if (!navController.popBackStack()) {
//                        findNavController().popBackStack()
//                    }
//                }
                NavHost(
                    navController = navController,
                    startDestination = ScreenB
                ) {
                    composable<ScreenB> {
                        ScreenB(
                            navigateToScreenC = { navController.navigate(ScreenC) },
                        )
                    }

                    composable<ScreenC> {
                        ScreenC()
                    }
                }

                /**
                 * Example 2
                 *
                 * Although in example 1 the default navigation behavior between compose and Fragments
                 * is the desired behavior, this shows that there may be a case where we do NEED
                 * manually back handling. Without a NavHost and compose navigation, the BackHandler
                 * is still not properly called after backgrounding the app. This indicates the issue
                 * is not unique to NavHost
                 */
//                var showScreenC: Boolean by remember { mutableStateOf(false) }
//                ScreenB(
//                    navigateToScreenC = { showScreenC = true },
//                )
//                if (showScreenC) {
//                    BackHandler { showScreenC = false }
//                /**
//                 * Using this workaround, we can achieve the desired behavior
//                 */
//                    //BackHandlerWorkaround { showScreenC = false }
//                    ScreenC()
//                }
            }
        }
    }
}

@Serializable
data object ScreenB

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenB(
    navigateToScreenC: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FragmentB ScreenB") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            TextButton(
                onClick = navigateToScreenC
            ) {
                Text("Go to ScreenC")
            }
        }
    }
}

@Serializable
data object ScreenC

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenC() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FragmentB ScreenC") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
        }
    }
}