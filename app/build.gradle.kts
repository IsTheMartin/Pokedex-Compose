import org.jetbrains.kotlin.cli.jvm.main

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "me.ismartin.pokedexcompose"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "me.ismartin.pokedexcompose"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.kotlinCompilerExtension
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(Dependencies.appLibraries)
    kapt(Dependencies.kaptLibraries)
    testImplementation(Dependencies.testLibraries)
    androidTestImplementation(Dependencies.androidTestLibraries)
    debugImplementation(Dependencies.debugLibraries)

    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
}

kapt {
    correctErrorTypes = true
}

setupKtlint()

fun Project.setupKtlint() {
    val ktlintConfiguration by configurations.creating {
        attributes {
            attribute(
                Bundling.BUNDLING_ATTRIBUTE,
                objects.named(Bundling::class.java, Bundling.EXTERNAL)
            )
        }
    }
    dependencies {
        ktlintConfiguration(Dependencies.Pinterest.ktlint)
    }

    val directoriesWithSource = listOf(
        "**/*.kt",
        "!build/**/*.kt",
        "!*/build/**/*.kt"
    )

    tasks.register("ktlint", JavaExec::class) {
        group = JavaBasePlugin.VERIFICATION_GROUP
        val cliArgs = project.findProject("ktlint_args") as? String
        val ktlintArgs = cliArgs?.split(" ") ?: (listOf(
            "-v",
            "--reporter=plain?group_by_file",
            "--reporter=checkstyle,output=$buildDir/ktlint.xml"
        ) + directoriesWithSource)
        description = "Check Kotlin code style"
        classpath = ktlintConfiguration
        mainClass.set("com.pinterest.ktlint.Main")
        args = ktlintArgs
        jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")
    }

    tasks.register("ktlintFormat", JavaExec::class) {
        group = "formatting"
        description = "Fix Kotlin code style deviations"
        classpath = ktlintConfiguration
        mainClass.set("com.pinterest.ktlint.Main")
        args = listOf("-F") + directoriesWithSource
        jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")
    }
}