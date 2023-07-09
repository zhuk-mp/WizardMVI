package ru.lt.wizardmvi.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.lt.wizardmvi.R
import ru.lt.wizardmvi.ViewAction
import ru.lt.wizardmvi.ViewState
import ru.lt.wizardmvi.WizardGesture
import ru.lt.wizardmvi.models.NavViewModel
import ru.lt.wizardmvi.models.WizardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WizardScreen(viewModel: WizardViewModel = viewModel(), navViewModel: NavViewModel) {
    val viewState by viewModel.viewState.observeAsState(initial = ViewState())


    var firstName by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(viewState.firstName))
    }
    var lastName by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(viewState.lastName))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
                title = { Text(stringResource(id = R.string.title)) }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    navViewModel.dispatch(ViewAction.Nav(WizardGesture.AddressScreen))
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .navigationBarsPadding(),
                enabled = viewState.isNextButtonEnabled
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
                value = firstName,
                onValueChange = {
                    viewModel.dispatch(ViewAction.FirstNameChanged(it.text))
                    firstName = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                label = { Text(stringResource(id = R.string.firstName)) },
                supportingText = { if (viewState.firstNameError != null) Text(stringResource(id = viewState.firstNameError!!)) },
                isError = viewState.firstNameError != null,
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    viewModel.dispatch(ViewAction.LastNameChanged(it.text))
                    lastName = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                label = { Text(stringResource(id = R.string.lastName)) },
                supportingText = { if (viewState.lastNameError != null) Text(stringResource(id = viewState.lastNameError!!)) },
                isError = viewState.lastNameError != null
            )
            Text(
                text = stringResource(id = R.string.date),
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp), fontSize = 18.sp
            )
            val initialSelectedDateMillis = if (viewState.date == 0L || viewState.date == null) null else viewState.date
            val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input, initialSelectedDateMillis = initialSelectedDateMillis)

            DatePicker(
                state = datePickerState,
                title = null,
                headline = null,
                showModeToggle = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
            )

            viewModel.validateDateState(datePickerState.selectedDateMillis)

            if (viewState.date == null && datePickerState.selectedDateMillis != null)
                Text(
                    text = stringResource(id = R.string.not18),
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Red,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline
                )
        }
    }
}

