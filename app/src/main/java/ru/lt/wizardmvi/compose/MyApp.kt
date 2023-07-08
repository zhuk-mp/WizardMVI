package ru.lt.wizardmvi.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.lt.wizardmvi.R
import ru.lt.wizardmvi.models.AddressViewModel
import ru.lt.wizardmvi.models.ResultViewModel
import ru.lt.wizardmvi.models.TagViewModel
import ru.lt.wizardmvi.models.WizardViewModel
import ru.lt.wizardmvi.ui.theme.WizardMVITheme

@Composable
fun MyApp() {
    WizardMVITheme {
        val navController = rememberNavController()
        val routeWizardScreen = stringResource(id = R.string.wizardScreen)
        val routeAddressScreen = stringResource(id = R.string.addressScreen)
        val routeTagScreen = stringResource(id = R.string.tagScreen)
        val routeResultScreen = stringResource(id = R.string.resultScreen)

        NavHost(navController, startDestination = routeWizardScreen) {
            composable(routeWizardScreen) {
                val wizardViewModel: WizardViewModel = hiltViewModel()
                WizardScreen(wizardViewModel, navController)
            }
            composable(routeAddressScreen) {
                val addressScreenModel: AddressViewModel = hiltViewModel()
                AddressScreen(addressScreenModel, navController)
            }
            composable(routeTagScreen) {
                val tagScreenModel: TagViewModel = hiltViewModel()
                TagScreen(tagScreenModel, navController)
            }
            composable(routeResultScreen) {
                val resultScreenModel: ResultViewModel = hiltViewModel()
                ResultScreen(resultScreenModel, navController)
            }
        }
    }
}