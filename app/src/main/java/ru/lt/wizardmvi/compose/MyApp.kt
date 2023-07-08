package ru.lt.wizardmvi.compose

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.lt.wizardmvi.models.AddressViewModel
import ru.lt.wizardmvi.models.ResultViewModel
import ru.lt.wizardmvi.models.TagViewModel
import ru.lt.wizardmvi.models.WizardViewModel
import ru.lt.wizardmvi.ui.theme.WizardMVITheme

@Composable
fun MyApp() {
    WizardMVITheme {
        val navController = rememberNavController()

        NavHost(navController, startDestination = "wizardScreen") {
            composable("wizardScreen") {
                val wizardViewModel: WizardViewModel = hiltViewModel()
                WizardScreen(wizardViewModel, navController)
            }
            composable("addressScreen") {
                val addressScreenModel: AddressViewModel = hiltViewModel()
                AddressScreen(addressScreenModel, navController)
            }
            composable("tagScreen") {
                val tagScreenModel: TagViewModel = hiltViewModel()
                TagScreen(tagScreenModel, navController)
            }
            composable("resultScreen") {
                val resultScreenModel: ResultViewModel = hiltViewModel()
                ResultScreen(resultScreenModel)
            }
        }
    }
}