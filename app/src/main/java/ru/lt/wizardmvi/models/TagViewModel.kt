package ru.lt.wizardmvi.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.lt.wizardmvi.ViewAction
import ru.lt.wizardmvi.ViewState
import ru.lt.wizardmvi.WizardCache
import ru.lt.wizardmvi.WizardGesture
import ru.lt.wizardmvi.WizardStats
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    private val wizardCache: WizardCache,
    wizardStats: WizardStats
) : ViewModel() {

    val viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState())
    val navViewState: MutableLiveData<ViewState> = wizardStats.navViewState
    val selectedTags: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())

    fun dispatch(action: ViewAction) {
        when (action) {
            is ViewAction.TagChanged -> {
                selectedTags.value?.let {toggleTag(action.tag, !it.contains(action.tag))}
            }
            is ViewAction.Now -> {
                updateNextButtonState()
            }
            else -> {}
        }
        updateNextButtonState()
    }

    private fun toggleTag(tag: String, add: Boolean) {
        val currentTags = selectedTags.value ?: mutableListOf()
        if (!add) {
            currentTags.remove(tag)
        } else {
            currentTags.add(tag)
        }
        selectedTags.value = currentTags
        wizardCache.selectedTags = currentTags
        updateViewState { copy(tags = currentTags.toMutableList()) }
    }



    private fun updateViewState(block: ViewState.() -> ViewState) {
        val oldState = viewState.value!!
        val newState = block(oldState)
        viewState.value = newState
    }

    private fun updateNextButtonState() {
        val state = viewState.value!!
        val navState = navViewState.value!!
        val isTagNextButtonEnabled = selectedTags.value?.isNotEmpty() == true
        if (isTagNextButtonEnabled) {
            val nextScreen = if (navState.isCheckedNav) WizardGesture.AddressScreen else WizardGesture.ResultScreen
            navViewState.value = navState.copy(next = nextScreen)
        } else
            navViewState.value = navState.copy(next = WizardGesture.Empty)
        viewState.value = state.copy(isTagNextButtonEnabled = isTagNextButtonEnabled)
    }


}