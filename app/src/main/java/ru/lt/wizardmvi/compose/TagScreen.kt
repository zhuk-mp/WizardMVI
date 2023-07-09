package ru.lt.wizardmvi.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.lt.wizardmvi.R
import ru.lt.wizardmvi.ViewAction
import ru.lt.wizardmvi.ViewState
import ru.lt.wizardmvi.WizardGesture
import ru.lt.wizardmvi.models.NavViewModel
import ru.lt.wizardmvi.models.TagViewModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TagScreen(viewModel: TagViewModel = viewModel(), navViewModel: NavViewModel) {
    val viewState by viewModel.viewState.observeAsState(initial = ViewState())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.tags)) },
                navigationIcon = {
                    IconButton(onClick = {
                        navViewModel.dispatch(ViewAction.Nav(WizardGesture.AddressScreen))
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    navViewModel.dispatch(ViewAction.Nav(WizardGesture.ResultScreen))
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .navigationBarsPadding(),
                enabled = viewState.isTagNextButtonEnabled
            ) {
                Text(stringResource(id = R.string.next))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val context = LocalContext.current
            val tagsArray = context.resources.getStringArray(R.array.tags)
            val tags = tagsArray.toList()

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                tags.forEach { tag ->
                    TagChip(
                        text = tag,
                        isSelected = viewState.tags.contains(tag),
                        onSelectedChange = { viewModel.dispatch(ViewAction.TagChanged(tag))
                        }
                    )
                }
            }
        }
    }
}

