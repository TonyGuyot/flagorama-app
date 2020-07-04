object Versions {
    val sdk = object {
        val min = 16
        val target = 29
        val compile = target
    }

    // Tools & plugins versions
    const val buildTools = "29.0.2"
    const val gradle = "4.0.0"
    const val kotlin = "1.3.72"
    const val grGit = "2.1.0"

    // AndroidX libraries versions
    const val appCompat = "1.1.0"
    const val core = "1.3.0"
    const val constraintLayout = "1.1.3"
    const val lifecycle = "2.2.0"
    const val navigation = "2.3.0"
    const val room = "2.2.5"

    // Other Google libraries versions
    const val material = "1.1.0"
    const val gson = "2.8.5"

    // Third-party libraries versions
    const val coil = "0.11.0"
    const val retrofit = "2.6.0"
    const val okHttp = "3.12.11"
    const val timber = "4.7.1"

    // Test libraries versions
    const val junit = "4.12"
    const val androidJunit = "1.1.1"
    const val espresso = "3.2.0"
}

object Kotlin {
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}

object GradlePlugins {
    const val android = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val grGit = "org.ajoberstar:grgit:${Versions.grGit}"
    const val navigation = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
}

object AndroidX {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.core}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val lifecycleLivedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val navigationFragment = "androidx.navigation:navigation-fragment:${Versions.navigation}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui:${Versions.navigation}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
}

object Libs {
    // Google
    const val material = "com.google.android.material:material:${Versions.material}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    // Square
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    // Coil
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val coilSvg = "io.coil-kt:coil-svg:${Versions.coil}"

    // Jake Wharton
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}

object TestLibs {
    const val junit = "junit:junit:${Versions.junit}"
}

object AndroidTestLibs {
    const val junit = "androidx.test.ext:junit:${Versions.androidJunit}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}