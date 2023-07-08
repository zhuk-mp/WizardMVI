package ru.lt.wizardmvi

sealed class ViewAction {
    data class FirstNameChanged(val firstName: String) : ViewAction()
    data class LastNameChanged(val lastName: String) : ViewAction()
    data class DateChanged(val date: Long?) : ViewAction()
    data class CountryChanged(val country: String) : ViewAction()
    data class CityChanged(val city: String) : ViewAction()
    data class AddressChanged(val address: String) : ViewAction()
    data class TagChanged(val tag: String) : ViewAction()
    data class TagDeselected(val tag: String) : ViewAction()
    object NextButtonClicked : ViewAction()
    object AddressNextButtonClicked : ViewAction()
    object TagNextButtonClicked : ViewAction()
}