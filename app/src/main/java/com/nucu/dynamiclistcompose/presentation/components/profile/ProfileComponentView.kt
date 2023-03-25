package com.nucu.dynamiclistcompose.presentation.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javi.design.system.theme.DynamicListComposeTheme
import com.javi.design.system.theme.Typography

const val PROFILE_COMPONENT_SCREEN_TAG = "profile-component-screen-tag"

val profileHeight = 250.dp
val profileHeightExpanded = 180.dp

@Composable
fun ProfileComponentScreenView(
    model: ProfileModel,
    isExpandedScreen: Boolean = false
) {
    ProfileComponentView(
        modifier = Modifier
            .testTag(PROFILE_COMPONENT_SCREEN_TAG)
            .height(
                if (isExpandedScreen) profileHeightExpanded else profileHeight
            ),
        model = model
    )
}

@Composable
fun ProfileComponentView(
    modifier: Modifier = Modifier,
    model: ProfileModel
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor(model.color)))
            .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = model.name,
                style = Typography.h3.copy(fontWeight = FontWeight.Black),
                color = MaterialTheme.colors.secondary.copy(alpha = 0.8f)
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = model.shortDescription,
                style = Typography.h5.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colors.secondary.copy(alpha = 0.8f)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(MaterialTheme.colors.secondary.copy(alpha = 0.5f))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = model.country,
                style = Typography.body2,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colors.secondary.copy(alpha = 0.8f)
            )

            Text(
                modifier = Modifier.weight(1f),
                text = model.lifeDate,
                style = Typography.body2,
                textAlign = TextAlign.End,
                color = MaterialTheme.colors.secondary.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
@Preview
fun PreviewProfileComponentView() {
    DynamicListComposeTheme {
        ProfileComponentView(
            modifier = Modifier.height(profileHeight),
            model = ProfileModel(
                name = "Picasso",
                shortDescription = "Modern art",
                lifeDate = "1881 - 1973",
                color = "#DFB799",
                country = "Málaga, Spain"
            )
        )
    }
}