package ru.lt.wizardmvi.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.lt.wizardmvi.WizardGesture
import javax.inject.Inject

@HiltViewModel
class NavViewModel @Inject constructor(
) : ViewModel() {
    val wizardGesture: MutableLiveData<WizardGesture> = MutableLiveData(WizardGesture.WizardScreen)
}

