package ru.lt.wizardmvi.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.lt.wizardmvi.WizardCache
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    wizardCache: WizardCache
) : ViewModel() {

    val firstName: LiveData<String> get() = _firstName
    private val _firstName = MutableLiveData("")

    val lastName: LiveData<String> get() = _lastName
    private val _lastName = MutableLiveData("")

    val date: LiveData<String> get() = _date
    private val _date = MutableLiveData("")

    val fullAddress: LiveData<String> get() = _fullAddress
    private val _fullAddress = MutableLiveData("")

    val selectedTags: LiveData<MutableList<String>> get() = _selectedTags
    private val _selectedTags = MutableLiveData<MutableList<String>>(mutableListOf())


    init {
        _firstName.value = wizardCache.firstName
        _lastName.value = wizardCache.lastName
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        _date.value = dateFormat.format(wizardCache.date)
        _fullAddress.value ="${wizardCache.country}, ${wizardCache.city}, ${wizardCache.address}"
        _selectedTags.value = wizardCache.selectedTags
    }
}

