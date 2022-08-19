package com.nucu.dynamiclistcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nucu.dynamiclistcompose.R

@Composable
fun LoaderView() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader))
        LottieAnimation(
            modifier = Modifier.fillMaxSize(),
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    }
}


@Composable
fun ErrorView(
    exception: Throwable? = null,
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
                text = exception?.message ?: stringResource(id = R.string.label_error),
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