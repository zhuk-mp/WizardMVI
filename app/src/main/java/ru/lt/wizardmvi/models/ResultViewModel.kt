package ru.lt.wizardmvi.models

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.lt.wizardmvi.WizardCache
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    wizardCache: WizardCache
) : ViewModel() {

//    val selectedTags: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())

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
        _fullAddress.value = wizardCache.country + ", " + wizardCache.city + ", " + wizardCache.address
        _selectedTags.value = wizardCache.selectedTags
    }
}

