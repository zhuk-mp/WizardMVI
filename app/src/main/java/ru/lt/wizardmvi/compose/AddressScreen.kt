package ru.lt.wizardmvi.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.lt.wizardmvi.R
import ru.lt.wizardmvi.ViewAction
import ru.lt.wizardmvi.ViewState
import ru.lt.wizardmvi.models.AddressViewModel
import ru.lt.wizardmvi.models.NavViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(viewModel: AddressViewModel = viewModel(), navViewModel: NavViewModel) {
    val viewState by viewModel.viewState.observeAsState(initial = ViewState())

    var country by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(viewState.country))
    }
    var city by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(viewState.city))
    }

    var address by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(viewState.address))
    }

    LaunchedEffect(Unit) {
        viewModel.dispatch(ViewAction.Now)
    }

    BackHandler(onBack = {
        navViewModel.dispatch(ViewAction.Back)
    })

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.fullAddress)) },
                navigationIcon = {
                    IconButton(onClick = {
                        navViewModel.dispatch(ViewAction.Back)
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    navViewModel.dispatch(ViewAction.Next)
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .navigationBarsPadding(),
                enabled = viewState.isAddressNextButtonEnabled
            ) {
                Text(stringResource(id = R.string.next))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {

            OutlinedTextField(
                value = country,
                onValueChange = {
                    viewModel.dispatch(ViewAction.CountryChanged(it.text))
                    country = it
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(24.dp),
                label = { Text(stringResource(id = R.string.country)) },
                supportingText = { if (viewState.countryError != null) Text(stringResource(id = viewState.countryError!!)) },
                isError = viewState.countryError != null,
            )

            OutlinedTextField(
                value = city,
                onValueChange = {
                    viewModel.dispatch(ViewAction.CityChanged(it.text))
                    city = it
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 24.dp),
                label = { Text(stringResource(id = R.string.city)) },
                supportingText = { if (viewState.cityError != null) Text(stringResource(id = viewState.cityError!!)) },
                isError = viewState.cityError != null
            )

            OutlinedTextField(
                value = address,
                onValueChange = {
                    viewModel.dispatch(ViewAction.AddressChanged(it.text))
                    address = it
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(24.dp),
                label = { Text(stringResource(id = R.string.address)) },
                supportingText = { if (viewState.addressError != null) Text(stringResource(id = viewState.addressError!!)) },
                isError = viewState.addressError != null
            )
        }
    }
}