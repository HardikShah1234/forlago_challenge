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

import com.apollographql.apollo.coroutines.toFlow
import com.challenge.feature_rocketlaunches.network.Network
import com.challenge.feature_rocketlaunches.ui.RocketContract.Effect
import com.challenge.feature_rocketlaunches.ui.RocketContract.Event
import com.challenge.feature_rocketlaunches.ui.RocketContract.State
import com.leinardi.forlago.core.navigation.ForlagoNavigator
import com.leinardi.forlago.core.navigation.destination.rocketlaunches.RocketDialogDestination
import com.leinardi.forlago.core.rocketserver.LaunchListQuery
import com.leinardi.forlago.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class RocketLaunchViewModel @Inject constructor(
    private val network: Network,
    private val forlagoNavigator: ForlagoNavigator,
) : BaseViewModel<Event, State, Effect>() {

    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnShowLaunchItemClicked -> forlagoNavigator.navigate(RocketDialogDestination.createRoute(event.id))
            is Event.OnBackButtonClicked -> forlagoNavigator.navigateBack()
        }
    }

    fun getData(): Flow<RocketContract.UiState> {
        return network.apollo()
            .query(LaunchListQuery())
            .watcher()
            .toFlow()
            .map {
                val launchList = it.data?.launchConnection?.launches?.filterNotNull()
                if (launchList == null) {
                    RocketContract.UiState.Error
                } else {
                    RocketContract.UiState.Success(launchList)
                }
            }.catch { emit(RocketContract.UiState.Error) }
    }
}