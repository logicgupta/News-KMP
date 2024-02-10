<h1 align="center">News-KMP</h1>
<p align="center">
  🗡️ News-KMP is a Kotlin Multiplatform app for Android and IOS!
</p>
<p align="center">
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a>
</p>


## 🛠 How to run?
- Clone the repository
- Add **API_KEY** in build.gradle in :shared:core module 

- And that's it!! You can run the app now.

**NOTE:** You can register your app on [newsapi dashboard](https://www.newsapi.ai/dashboard) & generate above api key.

## Tech stack & Open-source libraries

- **Minimum SDK level**
  - Android 24
  - iOS 15  
  
- **Language**
   - Android + Shared - [Kotlin](https://kotlinlang.org/)
   - IOS - [Swift](https://github.com/apple/swift)

- **UI Framework**
   - Android - [Jetpack Compose](https://developer.android.com/jetpack/compose)
   - IOS - [SwiftUI](https://developer.apple.com/xcode/swiftui/)
  
- **Architecture**
  - [MVVM Architecture](https://developer.android.com/topic/architecture) (Model - View - ViewModel)
  - [Repository Pattern](https://proandroiddev.com/the-real-repository-pattern-in-android-efba8662b754)
    
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [Ktor](https://github.com/ktorio/ktor/tree/main): HTTP Client Franework for REST Apis.
- [Kotlinx.serialization](https://github.com/square/moshi/): A modern JSON serialization library for Kotlin.
- [Kamel](https://github.com/Kamel-Media/Kamel): Loading and caching images images from network (Android and IOS).
- [Kermit](https://github.com/touchlab/Kermit): Multiplatform centralized logging utility.
- [Moko](https://github.com/icerockdev/moko): Multiplatform library for managing resourses and MVVM 
- [BuildKonfig](https://github.com/yshrsmz/BuildKonfig): BuildConfig for Kotlin Multiplatform Project + Product Flavour in Shared Module
- [Koin](https://github.com/InsertKoinIO/koin) : A pragmatic lightweight dependency injection framework for Kotlin & Kotlin Multiplatform
- [Jetbrains Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform) A declarative framework for sharing UIs across multiple platforms with Kotlin

----

## Modularization

**Spotift-KMP** adopted modularization strategies below:

- **Reusability**: Modulizing reusable codes properly enable opportunities for code sharing and limits code accessibility in other modules at the same time.
- **Parallel Building**: Each module can be run in parallel and it reduces the build time.
- **Strict visibility control**: Modules restrict to expose dedicated components and access to other layers, so it prevents they're being used outside the module
- **Decentralized focusing**: Each developer team can assign their dedicated module and they can focus on their own modules.

**NOTE**: The same modularization strategies are used for [shared](https://github.com/logicgupta/News-KMP/tree/main/shared) module as well

For more information, check out the [Guide to app modularization](https://developer.android.com/topic/modularization).

## TODO
- Details Screen of News post Article
- Test cases for the common code


## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/logicgupta/News-KMP/stargazers)__ for this repository. :star: <br>
Also, __[follow me](https://github.com/logicgupta)__ on GitHub for my next creations! 🤩
