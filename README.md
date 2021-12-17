# Rocket Details App

<img src="/art/play_store_feature_graphic.png" width="300" align="right" hspace="0" />

![CI](https://github.com/leinardi/Forlago/workflows/CI/badge.svg)  ![License](https://img.shields.io/github/license/leinardi/Forlago.svg) ![Language](https://img.shields.io/github/languages/top/leinardi/Forlago?color=blue&logo=kotlin)

A Github template (Forlago) that lets you create an enterprise grade **Android / Compose Only / Single Activity / Multi Module** application project.

## How App Look like 👣

First Screen               |  Screen with Dialog
:-------------------------:|:-------------------------:
<img width="367" alt="Screenshot 2021-12-17 at 13 14 19" src="https://user-images.githubusercontent.com/47896958/146544895-ee8235a4-6bde-4e12-ab0f-ccb1df8b4ca1.png">         |  <img width="366" alt="Screenshot 2021-12-17 at 13 14 40" src="https://user-images.githubusercontent.com/47896958/146545015-819b3f8c-5703-4c91-b7ac-93c46e609143.png">




Detail Screen               |  Debug Screen
:-------------------------:|:-------------------------:
<img width="368" alt="Screenshot 2021-12-17 at 13 15 03" src="https://user-images.githubusercontent.com/47896958/146545346-f611a6f4-5ead-4b69-8895-5e651a72780b.png">         |  <img width="367" alt="Screenshot 2021-12-17 at 13 15 41" src="https://user-images.githubusercontent.com/47896958/146546403-87887912-11cf-4324-9746-1fcebe0885f1.png">


#### Debug Screen with url

<img width="367" alt="Screenshot 2021-12-17 at 13 15 54" src="https://user-images.githubusercontent.com/47896958/146546634-36a63b28-19ad-4cb6-a916-7b294c169d9b.png">

## Features 🎨

- 100% Kotlin-only Android code
- Compose only - Single Activity application
- Multi Module project structure
- CI Setup with GitHub Actions
- Dependency versions managed via `libs.versions.toml`
- Currently working **Rocket Launch Module with Graphql query**

## Gradle Setup 🐘

TBD

## Static Analysis 🔍

TBD

## CI ⚙️

This template is using [**GitHub Actions**](https://github.com/leinardi/Forlago/actions) as CI. You don't need to setup any external service and you
should have a running CI once you start using this template.

There are currently the following workflows available:

- [CI](.github/workflows/ci.yml) - Will be triggered on every new PR or every new commit pushed to the `master` branch.

## Release

### Generating new keystores

#### Generating debug keystore

```
keytool -genkeypair -dname "cn=First Last Name, ou=Mobile, o=My Company, c=US" -alias androiddebugkey -keypass android -keystore release/app-debug.jks -storepass android -keyalg RSA -sigalg SHA256withRSA -keysize 2048 -validity 9999
```

#### Generating release keystore

```
keytool -genkeypair -dname "cn=First Last Name, ou=Mobile, o=My Company, c=US" -alias release -keypass <STRONG PASSWORD GOES HERE> -keystore release/app-release.jks -storepass <STRONG PASSWORD GOES HERE> -keyalg RSA -sigalg SHA256withRSA -keysize 2048 -validity 9999
```

Add the release keystore and the encrypt secret passwords to
the [GitHub Actions secrets](https://docs.github.com/en/actions/security-guides/encrypted-secrets) as `RELEASE_KEYSTORE_PWD` and `ENCRYPT_KEY`.

### Publishing on the Play Store

#### Prerequisites

To publish your app on the Play Store you need to create a service account with access to the Play Developer API and generate a new `play-account.gpg`
file. You can find instructions on how to generate a new service account [here](https://github.com/Triple-T/gradle-play-publisher#service-account).
Once you have the JSON key, place it in `release/play-account.json`.

#### Managing Play Store metadata

Gradle Play Publisher (GPP) can do anything from building, uploading, and then promoting your App Bundle or APK to publishing app listings and other
metadata. Have a look at the folder `app/src/main/play` and the
official [GPP documentation](https://github.com/Triple-T/gradle-play-publisher#managing-play-store-metadata)
to learn more about managing Play Store metadata.

#### Publishing

Once everything is properly configured, you can run the [release.yml](.github/workflows/release.yml) workflow to create a new release and upload the
bundle to the Play Store.

### Enabling Firebase Crashlytics

The first step is to [register your new app with Firebase](https://firebase.google.com/docs/android/setup?hl=en) (don't forget to add [both release and
debug packages](https://firebase.googleblog.com/2016/08/organizing-your-firebase-enabled-android-app-builds.html) to the same Firebase project).
After that, you should put the `google-services.json`, containing both release and debug applications, inside the `app/` directory.
Now you have to [Enable Crashlytics in the Firebase console](https://firebase.google.com/docs/crashlytics/get-started?hl=en&platform=android#enable-in-console)
and, finally, open the Debug menu and trigger a crash using the Force Crash button.

### Encrypting your secrets

Once you have these 3 files in the following paths:

- `release/app-release.jks`
- `release/play-account.json`
- `app/google-services.json`

you can use the `release/encrypt-secrets.sh` to safely encrypt them (it's recommended to use a very strong passphrase).

## Contributing 🤝

Feel free to open a issue or submit a pull request for any bugs/improvements.

## Licenses

```
Copyright 2021 Roberto Leinardi.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
```

# WIP

```
adb shell am start -d "forlago://bar/FooText" -a android.intent.action.VIEW
adb shell am start -d "forlago://debug" -a android.intent.action.VIEW
```

Inspired by the following open source projects:

- https://github.com/FunkyMuse/Aurora
- https://github.com/dbaelz/PnPStats/
- https://github.com/catalinghita8/android-compose-mvvm-foodies
- https://proandroiddev.com/mvi-architecture-with-kotlin-flows-and-channels-d36820b2028d
- https://github.com/chrisbanes/tivi/
- https://github.com/alorma/Compose-Settings
- https://github.com/SamYStudiO/Beaver
- https://github.com/mohodroid/android-custom-account-manager

privacy policy link
