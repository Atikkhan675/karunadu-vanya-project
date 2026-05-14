package com.karunada.karunaduuvanya.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.karunada.karunaduuvanya.ui.screens.*
import com.karunada.karunaduuvanya.data.model.Wildlife
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import com.google.gson.Gson

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val gson = Gson()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(onLoginSuccess = {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }
        composable("home") {
            HomeScreen(
                onNavigateToWiki = { navController.navigate("wiki") },
                onNavigateToAlerts = { navController.navigate("alerts") },
                onNavigateToGuide = { navController.navigate("guide") },
                onNavigateToSounds = { navController.navigate("sounds") },
                onNavigateToGallery = { navController.navigate("gallery") }
            )
        }
        composable("wiki") { 
            WikiScreen(onWildlifeClick = { wildlife ->
                val json = gson.toJson(wildlife)
                val encodedJson = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
                navController.navigate("wildlife_detail/$encodedJson")
            }) 
        }
        composable(
            "wildlife_detail/{wildlifeJson}",
            arguments = listOf(navArgument("wildlifeJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedJson = backStackEntry.arguments?.getString("wildlifeJson") ?: ""
            val json = URLDecoder.decode(encodedJson, StandardCharsets.UTF_8.toString())
            val wildlife = gson.fromJson(json, Wildlife::class.java)
            WildlifeDetailScreen(wildlife = wildlife, onBack = { navController.popBackStack() })
        }
        composable("alerts") { AlertScreen() }
        composable("guide") { GuideScreen() }
        composable("sounds") { SoundsScreen() }
        composable("gallery") { GalleryScreen() }
    }
}
