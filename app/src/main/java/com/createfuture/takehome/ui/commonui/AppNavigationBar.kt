package com.createfuture.takehome.ui.commonui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.createfuture.takehome.data.models.ApiCharacter
import com.createfuture.takehome.ui.detail.CharacterDetailScreen
import com.createfuture.takehome.ui.home.CharacterScreen

@Composable
fun AppNavigationBar() {

    val navHostController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues: PaddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = Route.CharacterScreen.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Route.CharacterScreen.name){
                CharacterScreen(
                    onCharacterClick = {apiCharacter ->
                        navHostController.currentBackStackEntry?.savedStateHandle?.set("apiCharacter", apiCharacter)
                        navHostController.navigate(Route.CharacterDetailScreen.name)
                    }
                )
            }

            composable(Route.CharacterDetailScreen.name){
                val character = navHostController.previousBackStackEntry?.savedStateHandle?.get<ApiCharacter>("apiCharacter")
                character?.let { details -> CharacterDetailScreen(details) }
            }

        }

    }
}