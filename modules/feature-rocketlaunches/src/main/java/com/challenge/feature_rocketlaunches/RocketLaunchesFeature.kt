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

package com.challenge.feature_rocketlaunches

import androidx.compose.runtime.Composable
import com.challenge.feature_rocketlaunches.ui.RocketLaunchSitesScreen
import com.challenge.feature_rocketlaunches.ui.debug.RocketLaunchDebugPage
import com.challenge.feature_rocketlaunches.ui.rocketdetails.RocketLaunchDetailsScreen
import com.challenge.feature_rocketlaunches.ui.rocketdialog.RocketDialogScreen
import com.leinardi.forlago.core.feature.Feature
import com.leinardi.forlago.core.navigation.NavigationDestination
import com.leinardi.forlago.core.navigation.destination.rocketlaunches.RocketDestination
import com.leinardi.forlago.core.navigation.destination.rocketlaunches.RocketDialogDestination
import com.leinardi.forlago.core.navigation.destination.rocketlaunches.RocketLaunchDestination

class RocketLaunchesFeature : Feature(){
    override val id = "rocketlaunches"

    override val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> = mapOf(
        RocketDestination to { RocketLaunchSitesScreen() },
        RocketLaunchDestination to { RocketLaunchDetailsScreen()}
    )

    override val dialogDestinations: Map<NavigationDestination, @Composable () -> Unit> = mapOf(
        RocketDialogDestination to { RocketDialogScreen() },
    )

    override val debugComposable: @Composable () -> Unit = { RocketLaunchDebugPage() }
}