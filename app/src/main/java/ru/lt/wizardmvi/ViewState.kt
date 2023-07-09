package ru.lt.wizardmvi

data class ViewState(
    val firstName: String = "",
    val lastName: String = "",
    val date: Long? = 0L,
    val country: String = "",
    val city: String = "",
    val address: String = "",
    val firstNameError: Int? = null,
    val lastNameError: Int? = null,
    val countryError: Int? = null,
    val cityError: Int? = null,
    val addressError: Int? = null,
    val isNextButtonEnabled: Boolean = false,
    val isAddressNextButtonEnabled: Boolean = false,
    val isTagNextButtonEnabled: Boolean = false,
    val tags: MutableList<String> = mutableListOf(),
    val last: ArrayDeque<WizardGesture> = ArrayDeque(),
    val now: WizardGesture = WizardGesture.Empty
)