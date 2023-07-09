package ru.lt.wizardmvi.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.lt.wizardmvi.WizardGesture
import ru.lt.wizardmvi.models.AddressViewModel
import ru.lt.wizardmvi.models.NavViewModel
import ru.lt.wizardmvi.models.ResultViewModel
import ru.lt.wizardmvi.models.TagViewModel
import ru.lt.wizardmvi.models.WizardViewModel
import ru.lt.wizardmvi.ui.theme.WizardMVITheme

@Composable
fun MyApp(navViewModel: NavViewModel = viewModel()) {
    WizardMVITheme {
        val wizardGesture by navViewModel.wizardGesture.observeAsState()

        when (wizardGesture) {
            WizardGesture.WizardScreen-> {
                val wizardViewModel: WizardViewModel = hiltViewModel()
                WizardScreen(wizardViewModel, navViewModel)
            }
            WizardGesture.AddressScreen-> {
                val addressScreenModel: AddressViewModel = hiltViewModel()
                AddressScreen(addressScreenModel, navViewModel)
            }
            WizardGesture.TagScreen-> {
                val tagScreenModel: TagViewModel = hiltViewModel()
                TagScreen(tagScreenModel, navViewModel)
            }
            WizardGesture.ResultScreen-> {
                val resultScreenModel: ResultViewModel = hiltViewModel()
                ResultScreen(resultScreenModel, navViewModel)
            }
            else -> {}
        }
    }
}