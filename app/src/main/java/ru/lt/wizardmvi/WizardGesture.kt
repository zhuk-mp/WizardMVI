package ru.lt.wizardmvi

sealed class WizardGesture {
    object WizardScreen : WizardGesture()
    object AddressScreen : WizardGesture()
    object TagScreen : WizardGesture()
    object ResultScreen : WizardGesture()
}