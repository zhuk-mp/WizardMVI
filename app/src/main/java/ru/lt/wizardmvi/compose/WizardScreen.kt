package ru.lt.wizardmvi.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.lt.wizardmvi.ViewAction
import ru.lt.wizardmvi.ViewState
import ru.lt.wizardmvi.models.WizardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WizardScreen(viewModel: WizardViewModel = viewModel(), navController: NavController) {
    val viewState by viewModel.viewState.observeAsState(initial = ViewState())
    val navigateToAction by viewModel.navigateTo.observeAsState(initial = null)


    var firstName by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(0, 7)))
    }
    var lastName by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(0, 7)))
    }



    LaunchedEffect(navigateToAction) {
        when (navigateToAction?.getContentIfNotHandled()) {
            is ViewAction.NextButtonClicked -> {
                navController.navigate("addressScreen")
            }
            else -> {}
        }
    }


    Column {


        OutlinedTextField(
            value = firstName,
            onValueChange = { viewModel.dispatch(ViewAction.FirstNameChanged(it.text))
                firstName = it },
            modifier = Modifier.fillMaxWidth()
                .padding(24.dp),
            label = { Text("First Name") },
            supportingText =  { if (viewState.firstNameError != null ) Text(viewState.firstNameError!!) } ,
            isError = viewState.firstNameError != null,
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = { viewModel.dispatch(ViewAction.LastNameChanged(it.text))
                lastName = it },
            modifier = Modifier.fillMaxWidth()
                .padding(24.dp),
            label = { Text("Last Name") },
            supportingText =  { if (viewState.lastNameError != null ) Text(viewState.lastNameError!!) } ,
            isError = viewState.lastNameError != null
        )

//        val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input,
//            selectableDates = object : SelectableDates {
//                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
//                    val now = Instant.now()
//                    val chosenDate = Instant.ofEpochMilli(utcTimeMillis)
//                    val periodBetween = Period.between(
//                        chosenDate.atZone(ZoneId.of("UTC")).toLocalDate(),
//                        now.atZone(ZoneId.of("UTC")).toLocalDate()
//                    )
////                    Log.d("log----------------1", utcTimeMillis.toString())
//                    return if (periodBetween.years >= 18){
//                        viewModel.dispatch(ViewAction.DateChanged(utcTimeMillis))
//                        true
//                    } else {
//                        viewModel.dispatch(ViewAction.DateChanged(null))
//                        false
//                    }
//                }
//            }
//        )
        val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

//        Log.d("log----------------2", datePickerState.selectedDateMillis.toString())

        DatePicker(
            state = datePickerState,
            title = null,
            headline = null,
            showModeToggle = false,
            modifier = Modifier.fillMaxWidth().padding(start = 0.dp, end = 0.dp, top = 16.dp, bottom = 16.dp)
        )

        viewModel.validateDateState(datePickerState.selectedDateMillis)

        if (viewState.date == null && datePickerState.selectedDateMillis != null)
            Text(
                text = "18",
                modifier = Modifier.fillMaxWidth(),
                color = Color.Red,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline
            )


        Button(
            onClick = { viewModel.dispatch(ViewAction.NextButtonClicked) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(24.dp),
            enabled = viewState.isNextButtonEnabled) {
            Text("Next")
        }
    }
}

