package ru.lt.wizardmvi

import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WizardStats @Inject constructor() {
    val navViewState: MutableLiveData<ViewState> = MutableLiveData(ViewState())
}