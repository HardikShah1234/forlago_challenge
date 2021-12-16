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

import com.leinardi.forlago.core.rocketserver.LaunchListQuery
import com.leinardi.forlago.core.ui.base.ViewEffect
import com.leinardi.forlago.core.ui.base.ViewEvent
import com.leinardi.forlago.core.ui.base.ViewState
import kotlinx.coroutines.flow.Flow

object RocketContract {
    data class State(
        val isLoading: Boolean = false,
        val Error: String = "went wrong",
        val size: Int = 0,
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnBackButtonClicked: Event()
        object OnShowLaunchItemClicked : Event() {
            var id: String = ""
        }
    }

    sealed class Effect : ViewEffect

    sealed class UiState {
        object loading : UiState()
        object Error : UiState()
        class Success(val launchList: List<LaunchListQuery.Launch>) : UiState()
    }
}


