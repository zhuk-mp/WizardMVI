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
import java.time.Instant
import java.time.Period
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class WizardViewModel @Inject constructor(
    private val wizardCache: WizardCache,
    wizardStats: WizardStats
) : ViewModel() {

    val viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState())
    val navViewState: MutableLiveData<ViewState> = wizardStats.navViewState

    fun dispatch(action: ViewAction) {
        when (action) {
            is ViewAction.FirstNameChanged -> {
                val firstNameError = if (action.firstName.length < 3) R.string.least_characters else null
                wizardCache.firstName = if (firstNameError == null) action.firstName else null
                updateViewState { copy(firstName = action.firstName, firstNameError = firstNameError) }
            }
            is ViewAction.LastNameChanged -> {
                val lastNameError = if (action.lastName.length < 3) R.string.least_characters else null
                wizardCache.lastName = if (lastNameError == null) action.lastName else null
                updateViewState { copy(lastName = action.lastName, lastNameError = lastNameError) }
            }
            is ViewAction.DateChanged -> {
                wizardCache.date = action.date
                updateViewState { copy(date = action.date) }
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
            state.firstNameError == null && state.lastNameError == null &&
                    state.firstName.isNotEmpty() && state.lastName.isNotEmpty() && state.date != null && state.date != 0L
        if (isNextButtonEnabled) {
            val nextScreen = if (navState.isCheckedNav) WizardGesture.TagScreen else WizardGesture.AddressScreen
            navViewState.value = navState.copy(next = nextScreen)
        } else
            navViewState.value = navState.copy(next = WizardGesture.Empty)
        viewState.value = state.copy(isNextButtonEnabled = isNextButtonEnabled, isCheckedNav = navState.isCheckedNav)

    }

    fun validateDateState(utcTimeMillis: Long?) {
        if (utcTimeMillis != null) {
            val now = Instant.now()
            val chosenDate = Instant.ofEpochMilli(utcTimeMillis)
            val periodBetween = Period.between(
                chosenDate.atZone(ZoneId.of("UTC")).toLocalDate(),
                now.atZone(ZoneId.of("UTC")).toLocalDate()
            )
            if (periodBetween.years >= 18) {
                dispatch(ViewAction.DateChanged(utcTimeMillis))
            } else {
                dispatch(ViewAction.DateChanged(null))
            }
        } else
            dispatch(ViewAction.DateChanged(null))

    }

}

