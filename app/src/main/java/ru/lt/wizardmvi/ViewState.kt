package ru.lt.wizardmvi

data class ViewState(
    val firstName: String = "",
    val lastName: String = "",
    val date: Long? = 0L,
    val country: String = "",
    val city: String = "",
    val address: String = "",
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val countryError: String? = null,
    val cityError: String? = null,
    val addressError: String? = null,
    val isNextButtonEnabled: Boolean = false,
    val isAddressNextButtonEnabled: Boolean = false,
    val isTagNextButtonEnabled: Boolean = false,
    val tags: MutableList<String> = mutableListOf(),
)