package ru.lt.wizardmvi.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.lt.wizardmvi.R
import ru.lt.wizardmvi.ViewAction
import ru.lt.wizardmvi.ViewState
import ru.lt.wizardmvi.WizardCache
import ru.lt.wizardmvi.WizardGesture
import ru.lt.wizardmvi.WizardStats
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    val wizardCache: WizardCache,
    wizardStats: WizardStats
) : ViewModel() {

    val viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState())
    val navViewState: MutableLiveData<ViewState> = wizardStats.navViewState

    fun dispatch(action: ViewAction) {
        when (action) {
            is ViewAction.CountryChanged -> {
                val countryError = if (action.country.length < 3) R.string.least_characters else null
                wizardCache.country = if (countryError == null) action.country else null
                updateViewState { copy(country = action.country, countryError = countryError) }
            }
            is ViewAction.CityChanged -> {
                val cityError = if (action.city.length < 3) R.string.least_characters else null
                wizardCache.city = if (cityError == null) action.city else null
                updateViewState { copy(city = action.city, cityError = cityError) }
            }
            is ViewAction.AddressChanged -> {
                val addressError = if (action.address.length < 3) R.string.least_characters else null
                wizardCache.address = if (addressError == null) action.address else null
                updateViewState { copy(address = action.address, addressError = addressError) }
            }
            is ViewAction.Now -> {
                updateNextButtonState()
            }
            else -> {}
        }
        updateNextButtonState()
    }

    private fun updateViewState(block: ViewState.() -> ViewState) {
        val oldState = viewState.value!!
        val newState = block(oldState)
        viewState.value = newState
    }

    private fun updateNextButtonState() {
        val state = viewState.value!!
        val navState = navViewState.value!!
        val isNextButtonEnabled =
        state.countryError == null && state.cityError == null && state.addressError == null &&
                state.country.isNotEmpty() && state.city.isNotEmpty() && state.address.isNotEmpty()
        if (isNextButtonEnabled) {
            val nextScreen = if (navState.isCheckedNav) WizardGesture.ResultScreen else WizardGesture.TagScreen
            navViewState.value = navState.copy(next = nextScreen)
        } else
            navViewState.value = navState.copy(next = WizardGesture.Empty)
        viewState.value = state.copy(isNextButtonEnabled = isNextButtonEnabled)
    }
}