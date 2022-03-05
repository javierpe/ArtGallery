package com.nucu.dynamiclistcompose.components.oneclick

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.R

private const val MAX_ELEMENTS = 3

@Composable
fun OneClickReorderComponentView(
    data: List<OneClickModel>,
    date: String,
    onClick: (OneClickModel) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(text = "Component header", modifier = Modifier.padding(start = 16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
        ) {
            items(items = data) {
                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 16.dp
                ) {
                    Column(
                        modifier = Modifier
                            .clickable { onClick.invoke(it) }
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(text = date)

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            val size = 36.dp
                            Image(modifier = Modifier.size(size), painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "")
                            Image(modifier = Modifier.size(size), painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "")
                            Image(modifier = Modifier.size(size), painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "")

                            if (data.count() > MAX_ELEMENTS) {
                                Box(
                                    modifier = Modifier.clip(RoundedCornerShape(16.dp))
                                ) {
                                    Text(text = "+${data.count() - MAX_ELEMENTS}")
                                }
                            } else {
                                Image(modifier = Modifier.size(size), painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewOneClickReorderComponentView() {
    OneClickReorderComponentView(
        data = listOf(OneClickModel("")),
        date = "30 de Oct"
    ) { }
}