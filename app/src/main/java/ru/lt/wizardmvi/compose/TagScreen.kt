package ru.lt.wizardmvi.compose

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.lt.wizardmvi.ViewAction
import ru.lt.wizardmvi.ViewState
import ru.lt.wizardmvi.models.TagViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagScreen(viewModel: TagViewModel = viewModel(), navController: NavController) {
    val viewState by viewModel.viewState.observeAsState(initial = ViewState())
    val navigateToAction by viewModel.navigateTo.observeAsState(initial = null)


    LaunchedEffect(navigateToAction) {
        when (navigateToAction?.getContentIfNotHandled()) {
            is ViewAction.TagNextButtonClicked -> {
                navController.navigate("resultScreen")
            }
            else -> {}
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val tags = listOf("Android", "Kotlin", "Fragment", "Tag", "Cloud")

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            tags.forEach { tag ->
                TagChip(
                    text = tag,
                    isSelected = viewState.tags.contains(tag),
                    onSelectedChange = { isSelected ->
                        if (isSelected) {
                            viewModel.dispatch(ViewAction.TagChanged(tag))
                        } else {
                            viewModel.dispatch(ViewAction.TagDeselected(tag))
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.dispatch(ViewAction.TagNextButtonClicked) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(24.dp),
            enabled = viewState.isTagNextButtonEnabled
        ) {
            Text("Next")
        }
    }
}

