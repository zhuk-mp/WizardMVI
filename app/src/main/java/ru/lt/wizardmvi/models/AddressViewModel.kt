package ru.lt.wizardmvi.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.lt.wizardmvi.OneTimeEvent
import ru.lt.wizardmvi.ViewAction
import ru.lt.wizardmvi.ViewState
import ru.lt.wizardmvi.WizardCache
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {

    val viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState())
    val navigateTo: MutableLiveData<OneTimeEvent<ViewAction>> = MutableLiveData()



    fun dispatch(action: ViewAction) {
        when (action) {
            is ViewAction.CountryChanged -> {
                val countryError = if (action.country.length < 3) "Name must have at least 3 characters" else null
                wizardCache.country = if (countryError == null) action.country else null
                updateViewState { copy(country = action.country, countryError = countryError) }
            }
            is ViewAction.CityChanged -> {
                val cityError = if (action.city.length < 3) "Surname must have at least 3 characters" else null
                wizardCache.city = if (cityError == null) action.city else null
                updateViewState { copy(city = action.city, cityError = cityError) }
            }
            is ViewAction.AddressChanged -> {
                val addressError = if (action.address.length < 3) "Surname must have at least 3 characters" else null
                wizardCache.address = if (addressError == null) action.address else null
                updateViewState { copy(address = action.address, addressError = addressError) }
            }


            is ViewAction.AddressNextButtonClicked -> {
                navigateTo.value = OneTimeEvent(ViewAction.AddressNextButtonClicked)
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
        viewState.value = state.copy(isAddressNextButtonEnabled =
        state.countryError == null && state.cityError == null && state.addressError == null &&
                state.country.isNotEmpty() && state.city.isNotEmpty() && state.address.isNotEmpty()
        )
    }
}