package ru.lt.wizardmvi

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WizardCache @Inject constructor() {
    var firstName: String? = null
    var lastName: String? = null
    var date: Long? = null
    var country: String? = null
    var city: String? = null
    var address: String? = null
    var selectedTags: MutableList<String> = mutableListOf()
}