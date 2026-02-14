package com.kenotracker.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kenotracker.presentation.ui.screens.MainScreen
import com.kenotracker.presentation.ui.screens.InputScreen
import com.kenotracker.presentation.ui.screens.PredictScreen
import com.kenotracker.presentation.ui.screens.StatsScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomNavigation
import androidx.compose.material3.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kenotracker.presentation.ui.theme.KenoPrimary
import com.kenotracker.presentation.ui.theme.KenoText
import com.kenotracker.presentation.ui.theme.KenoTextSecondary
import com.kenotracker.presentation.ui.theme.KenoBackground

@Composable
fun KenoTrackerApp() {
    var selectedTab by remember { mutableIntStateOf(0) }
    
    val tabs = listOf("Play", "Input", "Predict", "Stats")
    
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = KenoBackground
            ) {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        label = { Text(title) },
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                0 -> MainScreen()
                1 -> InputScreen()
                2 -> PredictScreen()
                3 -> StatsScreen()
            }
        }
    }
}
