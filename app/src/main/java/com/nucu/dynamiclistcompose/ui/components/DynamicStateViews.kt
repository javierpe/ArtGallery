package com.nucu.dynamiclistcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nucu.dynamiclistcompose.R

@Composable
fun LoaderView() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "loading",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(id = R.string.label_loading),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Composable
fun ErrorView(
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "loading",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(id = R.string.label_error),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Button(onClick = { onRetry.invoke() }) {
                Text(text = stringResource(id = R.string.label_retry))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewErrorView() {
    ErrorView { }
}

@Composable
@Preview(showBackground = true)
fun PreviewLoader() {
    LoaderView()
}