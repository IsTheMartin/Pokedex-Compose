import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    object Android {
        const val coreKtx = "androidx.core:core-ktx:${Versions.androidCoreKtx}"
        const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleKtx}"
    }

    object Coil {
        const val core = "io.coil-kt:coil:${Versions.coil}"
        const val compose = "io.coil-kt:coil-compose:${Versions.coil}"
    }

    object Compose {
        const val activity = "androidx.activity:activity-compose:${Versions.compose}"
        const val core = "androidx.compose.ui:ui"
        const val debugTooling = "androidx.compose.ui:ui-tooling"
        const val debugManifest = "androidx.compose.ui:ui-test-manifest"
        const val graphics = "androidx.compose.ui:ui-graphics"
        const val jUnit4 = "androidx.compose.ui:ui-test-junit4"
        const val material3 = "androidx.compose.material3:material3"
        const val tooling = "androidx.compose.ui:ui-tooling-preview"
    }

    object Espresso {
        const val core = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }

    object Gson {
        const val core = "com.google.code.gson:gson:${Versions.gson}"
    }

    object Hilt {
        const val daggerCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
        const val daggerCore = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val compiler = "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}"
        const val navigationCompose = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"
    }

    object JUnit {
        const val core = "junit:junit:${Versions.jUnit}"
        const val ext = "androidx.test.ext:junit:${Versions.jUnitExt}"
    }

    object Pinterest {
        val ktlint = "com.pinterest:ktlint:${Versions.ktlint}"
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp}"
        const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    }

    object Room {
        const val compiler = "androidx.room:room-compiler:${Versions.room}"
        const val core = "androidx.room:room-runtime:${Versions.room}"
        const val extension = "androidx.room:room-ktx:${Versions.room}"
        const val paging = "androidx.room:room-paging:${Versions.room}"
        const val test = "androidx.room:room-testing:${Versions.room}"
    }

    val appLibraries = arrayListOf(
        Android.coreKtx,
        Android.lifecycleKtx,
        Coil.compose,
        Coil.core,
        Compose.activity,
        Compose.core,
        Compose.graphics,
        Compose.material3,
        Compose.tooling,
        Gson.core,
        Hilt.daggerCore,
        Hilt.navigationCompose,
        Retrofit.core,
        Retrofit.gsonConverter,
        Retrofit.loggingInterceptor,
        Retrofit.okHttp,
        Room.core,
        Room.extension,
        Room.paging
    )

    val kaptLibraries = arrayListOf(
        Hilt.compiler,
        Hilt.daggerCompiler,
        Room.compiler,
    )

    val testLibraries = arrayListOf(
        JUnit.core,
        Retrofit.mockWebServer,
        Room.test,
    )

    val androidTestLibraries = arrayListOf(
        Compose.jUnit4,
        Espresso.core,
        JUnit.ext,
    )

    val debugLibraries = arrayListOf(
        Compose.debugManifest,
        Compose.debugTooling,
    )
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.debugImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("debugImplementation", dependency)
    }
}