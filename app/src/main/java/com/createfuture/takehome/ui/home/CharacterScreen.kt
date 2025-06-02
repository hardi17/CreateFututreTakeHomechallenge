package com.createfuture.takehome.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.createfuture.takehome.R
import com.createfuture.takehome.data.models.ApiCharacter
import com.createfuture.takehome.ui.commonui.ErrorView
import com.createfuture.takehome.ui.commonui.LoadingView
import com.createfuture.takehome.utils.Uistate

@Preview
@Composable
fun CharacterScreen(
    viewModel: CharacterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val query by viewModel.characterName.collectAsStateWithLifecycle()
    val filterList by viewModel.filterCharacterList.collectAsStateWithLifecycle()

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
            ) {
                Column(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(Color.White),
                        value = query,
                        onValueChange = { viewModel.filterCharacterListOnSearch(it) },
                        label = { Text("Search", color = Color.White) },
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .background(color = Color.Transparent, shape = RectangleShape)
                    )
                    CharacterContent(filterList, uiState)
                }
            }
        }
    )
}

@Composable
fun CharacterContent(filterList: List<ApiCharacter>, uiState: Uistate<List<ApiCharacter>>) {
    when (uiState) {
        is Uistate.Loading -> {
            LoadingView()
        }

        is Uistate.Success -> {
            if (uiState.result.isNotEmpty()) {
                if (filterList.isNotEmpty()) {
                    CharacterListView(filterList)
                } else {
                    ErrorView(stringResource(R.string.no_data_found))
                }
            }
        }

        is Uistate.Error -> {
            ErrorView(uiState.message.toString())
        }
    }
}

@Preview
@Composable
fun CharacterListView(charList: List<ApiCharacter>) {
    LazyColumn {
        items(charList) { item ->
            CharacterItem(item)
        }
    }
}

@Preview
@Composable
fun CharacterItem(it: ApiCharacter) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 4.dp, bottom = 4.dp)
    ) {
        CharacterDetailSection(it, Modifier.weight(1f))
        SeasonsDetailSection(it, Modifier.weight(1f))
    }
    Divider(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 4.dp, bottom = 4.dp),
        color = Color.Gray,
        thickness = 1.dp
    )

}

@Preview
@Composable
fun SeasonsDetailSection(it: ApiCharacter, modifier: Modifier) {
    Column(
        modifier = modifier.padding(4.dp)
    ) {
        Text(
            text = stringResource(R.string.Seasons),
            textAlign = TextAlign.End,
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = getSeasons(it.tvSeries),
            textAlign = TextAlign.End,
            color = Color.Gray,
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun CharacterDetailSection(it: ApiCharacter, modifier: Modifier) {
    Column(
        modifier = modifier.padding(4.dp)
    ) {
        Text(
            text = it.name,
            textAlign = TextAlign.Start,
            color = Color.White,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Row {
            Text(
                text = stringResource(R.string.culture),
                color = Color.White,
                fontSize = 15.sp
            )
            Text(
                text = it.culture.ifBlank { stringResource(R.string.notAvailable) },
                color = Color.Gray,
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 3.dp)
            )
        }
        Row {
            Text(
                text = stringResource(R.string.born),
                color = Color.White,
                fontSize = 15.sp
            )
            Text(
                text = it.born,
                color = Color.Gray,
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 3.dp)
            )
        }
        Row {
            Text(
                text = stringResource(R.string.died),
                color = Color.White,
                fontSize = 15.sp
            )
            Text(
                text = it.died.ifBlank { stringResource(R.string.stillALive) },
                color = Color.Gray,
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 3.dp)
            )
        }
    }
}

fun getSeasons(tvSeries: List<String>): String {
    return tvSeries.joinToString(", ") {
        when (it) {
            "Season 1" -> "I "
            "Season 2" -> "II, "
            "Season 3" -> "III, "
            "Season 4" -> "IV, "
            "Season 5" -> "V, "
            "Season 6" -> "VI, "
            "Season 7" -> "VII, "
            "Season 8" -> "VIII"
            else -> ""
        }
    }.trim().trimEnd(',')
}






