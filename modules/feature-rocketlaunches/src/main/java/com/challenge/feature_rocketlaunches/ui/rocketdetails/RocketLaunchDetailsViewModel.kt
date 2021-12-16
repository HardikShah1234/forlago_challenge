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

import androidx.lifecycle.SavedStateHandle
import com.apollographql.apollo.coroutines.toFlow
import com.challenge.feature_rocketlaunches.network.Network
import com.challenge.feature_rocketlaunches.ui.rocketdetails.RocketLaunchDetailsContract.Effect
import com.challenge.feature_rocketlaunches.ui.rocketdetails.RocketLaunchDetailsContract.Event
import com.challenge.feature_rocketlaunches.ui.rocketdetails.RocketLaunchDetailsContract.State
import com.leinardi.forlago.core.navigation.ForlagoNavigator
import com.leinardi.forlago.core.navigation.destination.rocketlaunches.RocketLaunchDestination
import com.leinardi.forlago.core.rocketserver.LaunchDetailsQuery
import com.leinardi.forlago.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class RocketLaunchDetailsViewModel @Inject constructor(
    private val network: Network,
    private val savedStateHandle: SavedStateHandle,
    private val forlagoNavigator: ForlagoNavigator,
) : BaseViewModel<Event, State, Effect>() {
    override fun provideInitialState() = State(
        rocketLaunchId = savedStateHandle.get<String>(RocketLaunchDestination.ID_PARAM).orEmpty()
    )

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnBackButtonClicked -> forlagoNavigator.navigateBack()
        }
    }

    fun getData(): Flow<RocketLaunchDetailsContract.UiState> {
        return network.apollo()
            .query(LaunchDetailsQuery(viewState.value.rocketLaunchId))
            .watcher()
            .toFlow()
            .map {
                val launchDetails = it.data!!.launch
                if (launchDetails == null) {
                    RocketLaunchDetailsContract.UiState.Error
                } else {
                    RocketLaunchDetailsContract.UiState.Success(launchList = launchDetails)
                }
            }.catch { emit(RocketLaunchDetailsContract.UiState.Error) }
    }
}

