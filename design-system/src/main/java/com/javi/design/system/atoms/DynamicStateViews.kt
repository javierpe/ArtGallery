package com.javi.design.system.atoms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.javi.design.system.R

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
    modifier: Modifier = Modifier,
    exception: Throwable? = null,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = exception?.toString() ?: stringResource(id = R.string.label_error),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Black
        )

        ButtonComponentView(title = stringResource(id = R.string.label_retry)) {
            onRetry()
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