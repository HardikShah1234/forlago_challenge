package com.challenge.feature_rocketlaunches.ui.rocketdialog

import androidx.lifecycle.SavedStateHandle
import com.leinardi.forlago.core.navigation.ForlagoNavigator
import com.leinardi.forlago.core.navigation.destination.rocketlaunches.RocketDialogDestination
import com.leinardi.forlago.core.navigation.destination.rocketlaunches.RocketLaunchDestination
import com.leinardi.forlago.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

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

@HiltViewModel
class RocketDialogViewModel @Inject constructor(
    private val forlagoNavigator: ForlagoNavigator,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<RocketDialogContract.Event, RocketDialogContract.State, RocketDialogContract.Effect>() {
    override fun provideInitialState(): RocketDialogContract.State = RocketDialogContract.State(
        rocketLaunchId = savedStateHandle.get<String>(RocketDialogDestination.ID_PARAM).orEmpty()
    )

    override fun handleEvent(event: RocketDialogContract.Event) {
        when (event) {
            is RocketDialogContract.Event.OnConfirmButtonClicked -> {
                forlagoNavigator.navigate(RocketLaunchDestination.createRoute(viewState.value.rocketLaunchId))
            }
            is RocketDialogContract.Event.OnDismissButtonClicked -> forlagoNavigator.navigateBack()
        }
    }
}