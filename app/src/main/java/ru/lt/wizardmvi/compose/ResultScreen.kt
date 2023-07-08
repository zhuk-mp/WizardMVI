package ru.lt.wizardmvi.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.lt.wizardmvi.ViewAction
import ru.lt.wizardmvi.models.ResultViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ResultScreen(viewModel: ResultViewModel = viewModel()) {
    val firstName by viewModel.firstName.observeAsState("")
    val lastName by viewModel.lastName.observeAsState("")
    val date by viewModel.date.observeAsState("")
    val fullAddress by viewModel.fullAddress.observeAsState("")
    val selectedTags by viewModel.selectedTags.observeAsState(initial = mutableListOf())


    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(text = "First Name", fontSize = 18.sp)
        Text(text = firstName, fontSize = 24.sp)
//        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "Last Name", fontSize = 18.sp)
        Text(text = lastName, fontSize = 24.sp)
//        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "date", fontSize = 18.sp)
        Text(text = date, fontSize = 24.sp)
//        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "Full Address", fontSize = 18.sp)
        Text(text = fullAddress, fontSize = 24.sp)
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "Интересы", fontSize = 18.sp)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                selectedTags.forEach { tag ->
                    TagChip(
                        text = tag,
                        isSelected = true,
                        onSelectedChange = { }
                    )
                }
            }
        }

    }




}