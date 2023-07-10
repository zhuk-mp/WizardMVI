package ru.lt.wizardmvi.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.lt.wizardmvi.R

@Composable
fun ButtonNext( onClick: () -> Unit, enabled: Boolean){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .navigationBarsPadding(),
        enabled = enabled
    ) {
        Text(stringResource(id = R.string.next))
    }
}