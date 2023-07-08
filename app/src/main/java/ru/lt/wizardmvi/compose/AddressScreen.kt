package ru.lt.wizardmvi.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.lt.wizardmvi.ViewAction
import ru.lt.wizardmvi.ViewState
import ru.lt.wizardmvi.models.AddressViewModel

@Composable
fun AddressScreen(viewModel: AddressViewModel = viewModel(), navController: NavController) {
    val viewState by viewModel.viewState.observeAsState(initial = ViewState())
    val navigateToAction by viewModel.navigateTo.observeAsState(initial = null)


    var country by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(0, 7)))
    }
    var city by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(0, 7)))
    }

    var address by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(0, 7)))
    }

    LaunchedEffect(navigateToAction) {
        when (navigateToAction?.getContentIfNotHandled()) {
            is ViewAction.AddressNextButtonClicked -> {
                navController.navigate("tagScreen")
            }
            else -> {}
        }
    }


    Column {


        OutlinedTextField(
            value = country,
            onValueChange = { viewModel.dispatch(ViewAction.CountryChanged(it.text))
                country = it },
            modifier = Modifier.fillMaxWidth()
                .padding(24.dp),
            label = { Text("country") },
            supportingText =  { if (viewState.countryError != null ) Text(viewState.countryError!!) } ,
            isError = viewState.countryError != null,
        )

        OutlinedTextField(
            value = city,
            onValueChange = { viewModel.dispatch(ViewAction.CityChanged(it.text))
                city = it },
            modifier = Modifier.fillMaxWidth()
                .padding(24.dp),
            label = { Text("city") },
            supportingText =  { if (viewState.cityError != null ) Text(viewState.cityError!!) } ,
            isError = viewState.cityError != null
        )

        OutlinedTextField(
            value = address,
            onValueChange = { viewModel.dispatch(ViewAction.AddressChanged(it.text))
                address = it },
            modifier = Modifier.fillMaxWidth()
                .padding(24.dp),
            label = { Text("address") },
            supportingText =  { if (viewState.addressError != null ) Text(viewState.addressError!!) } ,
            isError = viewState.addressError != null
        )


        Button(
            onClick = { viewModel.dispatch(ViewAction.AddressNextButtonClicked) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(24.dp),
            enabled = viewState.isAddressNextButtonEnabled) {
            Text("Next")
        }
    }
}