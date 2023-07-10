package ru.lt.wizardmvi.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.lt.wizardmvi.ViewAction
import ru.lt.wizardmvi.ViewState
import ru.lt.wizardmvi.WizardGesture
import ru.lt.wizardmvi.WizardStats
import javax.inject.Inject

@HiltViewModel
class NavViewModel @Inject constructor(
    wizardStats: WizardStats
) : ViewModel() {
    val viewState: MutableLiveData<ViewState> = wizardStats.navViewState

    private val mainScreen = WizardGesture.WizardScreen

    private val startScreen = WizardGesture.WizardScreen
    init {
        updateViewState { copy(now = startScreen) }
    }

    val wizardGesture: MutableLiveData<WizardGesture> = MutableLiveData(startScreen)

    fun dispatch(action: ViewAction) {
        when (action) {
            is ViewAction.NextChecked -> {
                updateViewState { copy(isCheckedNav = action.isCheckedNav) }
            }
            is ViewAction.Back -> {
                val last = if (viewState.value!!.last.isEmpty())
                    mainScreen
                else
                    viewState.value!!.last.removeLast()

                wizardGesture.value = last
                updateViewState { copy(last = viewState.value!!.last, now = last) }

            }
            is ViewAction.Next -> {
                val screen = viewState.value!!.next
                wizardGesture.value = screen
                viewState.value!!.last.addLast(viewState.value!!.now)
                updateViewState { copy(last = viewState.value!!.last, now = screen) }
            }
            else -> {}
        }
    }

    private fun updateViewState(block: ViewState.() -> ViewState) {
        val oldState = viewState.value!!
        val newState = block(oldState)
        viewState.value = newState
    }
}

