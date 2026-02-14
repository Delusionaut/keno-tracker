package com.kenotracker.presentation.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import com.kenotracker.presentation.ui.theme.KenoBackground
import com.kenotracker.presentation.ui.theme.KenoPrimary
import com.kenotracker.presentation.ui.theme.KenoSurface
import com.kenotracker.presentation.ui.theme.KenoText
import com.kenotracker.presentation.ui.theme.KenoTextSecondary
import com.kenotracker.presentation.viewmodel.KenoViewModel

sealed class BottomNavItem(
    val title: String,
    val icon: ImageVector
) {
    data object Input : BottomNavItem("Log Draw", Icons.Default.TouchApp)
    data object Predict : BottomNavItem("Predict", Icons.Default.Add)
    data object Stats : BottomNavItem("Stats", Icons.Default.Analytics)
}

@Composable
fun MainScreen(
    viewModel: KenoViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val navItems = listOf(
        BottomNavItem.Input,
        BottomNavItem.Predict,
        BottomNavItem.Stats
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = KenoSurface
            ) {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = KenoPrimary,
                            selectedTextColor = KenoPrimary,
                            unselectedIconColor = KenoTextSecondary,
                            unselectedTextColor = KenoTextSecondary,
                            indicatorColor = KenoBackground
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        when (selectedTab) {
            0 -> InputScreen(viewModel = viewModel)
            1 -> PredictScreen(
                viewModel = viewModel,
                onPlayNumbers = { numbers ->
                    viewModel.setNumbersForInput(numbers)
                    selectedTab = 0 // Switch to input tab
                }
            )
            2 -> StatsScreen(viewModel = viewModel)
        }
    }
}
