/*
 * Copyright 2021 Roberto Leinardi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.challenge.feature_rocketlaunches.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.challenge.feature_rocketlaunches.R
import com.leinardi.forlago.core.rocketserver.LaunchListQuery
import com.leinardi.forlago.core.ui.component.TopAppBar

@ExperimentalCoilApi
@Composable
fun RocketLaunchSitesScreen(viewModel: RocketLaunchViewModel = hiltViewModel()) {
    RocketLaunchSitesScreen(
        sendEvent = { viewModel.onUiEvent(it) }
    )
}

@ExperimentalCoilApi
@Composable
fun RocketLaunchSitesScreen(
    modifier: Modifier = Modifier,
    sendEvent: (event: RocketContract.Event) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = stringResource(R.string.i18n_rocket_launch_text),
                navigateUp = { sendEvent(RocketContract.Event.OnBackButtonClicked) }
            )
        },
        content = {
            LaunchListContent()
        }
    )

}

@ExperimentalCoilApi
@Composable
fun LaunchListContent(
    viewModel: RocketLaunchViewModel = hiltViewModel()
) {
    val state = remember { viewModel.getData() }.collectAsState(initial = RocketContract.UiState.loading)
    when (val value = state.value) {
        is RocketContract.UiState.Success -> LaunchItem(launchList = value.launchList, sendEvent = { viewModel.onUiEvent(it) })
        is RocketContract.UiState.loading -> Loading()
        is RocketContract.UiState.Error -> Error()
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(modifier = Modifier)
    }
}

@Composable
fun Error(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier,
            text = "Oops something went wrong"
        )
    }
}

@ExperimentalCoilApi
@Composable
fun LaunchItem(launchList: List<LaunchListQuery.Launch>, sendEvent: (event: RocketContract.Event) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(items = launchList) { item ->
            Column(Modifier.clickable(
                onClick = { sendEvent(RocketContract.Event.OnShowLaunchItemClicked.also { it.id = item.id }) }
            )) {
                Row(Modifier.padding(12.dp)) {
                    Column(Modifier.size(50.dp)) {
                        val painter = if (item.mission?.missionPatch.isNullOrBlank()) {
                            painterResource(id = R.drawable.ui_ic_error)
                        } else {
                            rememberImagePainter(
                                data = item.mission?.missionPatch,
                                onExecute = { _, _ -> true },
                                builder = {
                                    crossfade(true)
                                    error(R.drawable.ui_ic_error)
                                }
                            )
                        }
                        Image(
                            painter = painter,
                            contentDescription = "Logo",
                        )
                    }
                    Column(Modifier.padding(start = 8.dp)) {
                        Text(
                            text = item.mission?.name.orEmpty(),
                            style = MaterialTheme.typography.h6
                        )
                        Text(
                            text = item.site.orEmpty(),
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
                Divider()
            }
        }
    }
}

@ExperimentalCoilApi
@Preview
@Composable
fun LaunchListPreview() {
    RocketLaunchSitesScreen()
}