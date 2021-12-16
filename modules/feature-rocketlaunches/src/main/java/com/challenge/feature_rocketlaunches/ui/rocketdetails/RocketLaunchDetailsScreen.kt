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
package com.challenge.feature_rocketlaunches.ui.rocketdetails

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.challenge.feature_rocketlaunches.R
import com.leinardi.forlago.core.rocketserver.LaunchDetailsQuery
import com.leinardi.forlago.core.ui.component.TopAppBar

@ExperimentalCoilApi
@Composable
fun RocketLaunchDetailsScreen(viewModel: RocketLaunchDetailsViewModel = hiltViewModel()) {
    RocketLaunchDetailsScreen(
        sendEvent = { viewModel.onUiEvent(it) }
    )
}

@ExperimentalCoilApi
@Composable
fun RocketLaunchDetailsScreen(
    modifier: Modifier = Modifier,
    sendEvent: (event: RocketLaunchDetailsContract.Event) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = stringResource(R.string.i18n_rocket_launch_details_text),
                navigateUp = { sendEvent(RocketLaunchDetailsContract.Event.OnBackButtonClicked) },
            )
        },
        content = {
            LaunchDetailsContent()
        }
    )
}

@ExperimentalCoilApi
@Composable
fun LaunchDetailsContent(viewModel: RocketLaunchDetailsViewModel = hiltViewModel()) {
    val state = remember {
        viewModel.getData()
    }.collectAsState(initial = RocketLaunchDetailsContract.UiState.loading)

    when (val value = state.value) {
        is RocketLaunchDetailsContract.UiState.Success -> ImageCardData(launchDetails = value.launchList)
        is RocketLaunchDetailsContract.UiState.loading -> Loading()
        is RocketLaunchDetailsContract.UiState.Error -> Error()
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
fun ImageCardData(launchDetails: LaunchDetailsQuery.Launch) {
    val scrollState = rememberScrollState()
    val site = launchDetails.site.orEmpty()
    val missionName = launchDetails.mission?.name.orEmpty()
    val missionPatch = launchDetails.mission?.missionPatch.orEmpty()
    val rocketName = launchDetails.rocket?.name.orEmpty()
    val rocketType = launchDetails.rocket?.type.orEmpty()
    val isBooked = launchDetails.isBooked

    Column(
        Modifier
            .padding(16.dp)
            .verticalScroll(scrollState)) {
        val painter = if (missionPatch.isBlank()) {
            painterResource(id = R.drawable.ui_ic_error)
        } else {
            rememberImagePainter(
                data = missionPatch,
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
            Modifier.size(300.dp)
        )

        Text(modifier = Modifier.padding(top = 20.dp), text = "Mission", style = MaterialTheme.typography.h6)
        Text(text = missionName, style = MaterialTheme.typography.h5)

        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Text(text = "Rocket", style = MaterialTheme.typography.h6)
        Text(text = "$rocketName ($rocketType)", style = MaterialTheme.typography.h5)

        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.ui_ic_outline_location_on),
                contentDescription = "Location"
            )
            Text(modifier = Modifier.padding(start = 8.dp), text = "Site $site", style = MaterialTheme.typography.h5)
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            val icon = if (isBooked) R.drawable.ui_ic_baseline_star else R.drawable.ui_ic_baseline_star_border
            val label = if (isBooked) "Yes" else "No"
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = icon),
                contentDescription = "Booked"
            )
            Text(modifier = Modifier.padding(start = 8.dp), text = "Booked $label", style = MaterialTheme.typography.h5)
        }
    }
}

@ExperimentalCoilApi
@Preview
@Composable
fun LaunchDetails() {
    RocketLaunchDetailsScreen()
}