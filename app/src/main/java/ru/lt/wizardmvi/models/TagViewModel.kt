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
class TagViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {

    val viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState())
    val navigateTo: MutableLiveData<OneTimeEvent<ViewAction>> = MutableLiveData()
    val selectedTags: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())

    fun dispatch(action: ViewAction) {
        when (action) {
            is ViewAction.TagChanged -> {
                toggleTag(action.tag, true)
            }
            is ViewAction.TagDeselected -> {
                toggleTag(action.tag, false)
            }

            is ViewAction.TagNextButtonClicked -> {
                navigateTo.value = OneTimeEvent(ViewAction.TagNextButtonClicked)
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
        viewState.value = state.copy(isTagNextButtonEnabled = selectedTags.value?.isNotEmpty() == true)
    }


}