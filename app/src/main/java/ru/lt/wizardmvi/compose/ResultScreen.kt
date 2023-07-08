package ru.lt.wizardmvi.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.lt.wizardmvi.R
import ru.lt.wizardmvi.models.ResultViewModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(viewModel: ResultViewModel = viewModel(), navController: NavController) {
    val firstName by viewModel.firstName.observeAsState("")
    val lastName by viewModel.lastName.observeAsState("")
    val date by viewModel.date.observeAsState("")
    val fullAddress by viewModel.fullAddress.observeAsState("")
    val selectedTags by viewModel.selectedTags.observeAsState(initial = mutableListOf())


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.result)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues).padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(text = stringResource(id = R.string.firstName), fontSize = 18.sp)
            Text(text = firstName, fontSize = 24.sp)
            Text(text = stringResource(id = R.string.lastName), fontSize = 18.sp)
            Text(text = lastName, fontSize = 24.sp)
            Text(text = stringResource(id = R.string.date), fontSize = 18.sp)
            Text(text = date, fontSize = 24.sp)
            Text(text = stringResource(id = R.string.fullAddress), fontSize = 18.sp)
            Text(text = fullAddress, fontSize = 24.sp)
            Spacer(modifier = Modifier.padding(16.dp))
            Text(text = stringResource(id = R.string.tags), fontSize = 18.sp)

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