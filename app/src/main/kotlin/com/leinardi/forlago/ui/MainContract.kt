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

package com.leinardi.forlago.ui

import android.content.Intent
import com.leinardi.forlago.core.navigation.destination.foo.FooDestination
import com.leinardi.forlago.core.navigation.destination.rocketlaunches.RocketDestination
import com.leinardi.forlago.core.ui.base.ViewEffect
import com.leinardi.forlago.core.ui.base.ViewEvent
import com.leinardi.forlago.core.ui.base.ViewState

object MainContract {
    data class State(
        val startDestination: String = RocketDestination.createRoute(),
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnIntentReceived(val intent: Intent) : Event()
    }

    sealed class Effect : ViewEffect
}
