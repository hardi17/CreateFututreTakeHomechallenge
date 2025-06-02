package com.createfuture.takehome.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.createfuture.takehome.R
import com.createfuture.takehome.data.models.ApiCharacter

@Composable
fun CharacterDetailScreen(
    character: ApiCharacter
) {
    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .paint(
                        painter = painterResource(id = R.drawable.img_characters),
                        contentScale = ContentScale.FillBounds
                    )
            )
            CharacterDetail(character, paddingValues)
        }
    )

}

@Composable
fun CharacterDetail(character: ApiCharacter, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = character.name,
            fontSize = 30.sp,
            color = Color.White,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterHorizontally)
        )

        HorizontalDivider(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 4.dp, bottom = 4.dp),
            color = Color.DarkGray,
            thickness = 2.dp
        )
        SingleValueComposeView(stringResource(R.string.gender), character.gender)
        SingleValueComposeView(
            stringResource(R.string.culture),
            character.culture.ifBlank { stringResource(R.string.notAvailable) })
        SingleValueComposeView(stringResource(R.string.born), character.born)
        SingleValueComposeView(
            stringResource(R.string.died),
            character.died.ifBlank { stringResource(R.string.stillALive) })

        ListItemComposeView(stringResource(R.string.aliases), character.aliases)
        ListItemComposeView(stringResource(R.string.tvseries), character.tvSeries)
        ListItemComposeView(stringResource(R.string.playedby), character.playedBy)
    }
}

@Composable
fun ListItemComposeView(title: String, items: List<String>) {
    Row {
        Text(
            text = title,
            textAlign = TextAlign.Start,
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = FontFamily.Default,
            modifier = Modifier
                .padding(10.dp)
        )
        Column(modifier = Modifier.padding(10.dp)) {
            items.forEach {
                Text(
                    text = it,
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily.Default
                )
            }
        }
    }

}

@Composable
fun SingleValueComposeView(title: String, value: String) {
    Row {
        Text(
            text = title,
            textAlign = TextAlign.Start,
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = FontFamily.Default,
            modifier = Modifier
                .padding(10.dp)
        )
        Text(
            text = value,
            textAlign = TextAlign.Start,
            fontSize = 20.sp,
            color = Color.Gray,
            fontFamily = FontFamily.Default,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}
