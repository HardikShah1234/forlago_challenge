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

import com.leinardi.forlago.core.rocketserver.LaunchDetailsQuery
import com.leinardi.forlago.core.rocketserver.LaunchListQuery
import com.leinardi.forlago.core.ui.base.ViewEffect
import com.leinardi.forlago.core.ui.base.ViewEvent
import com.leinardi.forlago.core.ui.base.ViewState
import kotlinx.coroutines.flow.Flow

object RocketLaunchDetailsContract {
    data class State(
        val rocketLaunchId: String = "",
        val isLoading: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnBackButtonClicked : Event()
    }

    sealed class Effect : ViewEffect

    sealed class UiState {
        object loading : UiState()
        object Error : UiState()
        class Success(val launchList: LaunchDetailsQuery.Launch) : UiState()
    }
}