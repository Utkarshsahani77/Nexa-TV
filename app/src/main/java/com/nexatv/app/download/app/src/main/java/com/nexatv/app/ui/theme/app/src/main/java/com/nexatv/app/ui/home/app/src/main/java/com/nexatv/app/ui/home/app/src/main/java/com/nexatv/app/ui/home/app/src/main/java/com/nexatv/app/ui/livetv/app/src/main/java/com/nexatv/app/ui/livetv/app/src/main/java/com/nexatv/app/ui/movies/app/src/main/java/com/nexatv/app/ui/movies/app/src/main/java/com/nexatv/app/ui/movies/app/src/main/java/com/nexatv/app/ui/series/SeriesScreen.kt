package com.nexatv.app.ui.series

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexatv.app.data.ResultState
import com.nexatv.app.ui.home.PosterCard

@Composable
fun SeriesScreen(
    viewModel: SeriesViewModel,
    onOpenDetails: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadSeries()
    }

    when (val s = state) {
        is ResultState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ResultState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(s.message)
            }
        }

        is ResultState.Success -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(s.data) { item ->
                    PosterCard(
                        item = item,
                        onClick = {
                            onOpenDetails(item.id)
                        }
                    )
                }
            }
        }
    }
}
