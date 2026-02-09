plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    id("com.google.dagger.hilt.android") version "2.59"
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    kotlin("plugin.serialization") version "2.3.0"
}

android {
    namespace = "com.vbshkn.dreamboard"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.vbshkn.dreamboard"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
        freeCompilerArgs.add("-XXLanguage:+ExplicitBackingFields")
        freeCompilerArgs.add("-Xexplicit-backing-fields")
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.appcompat) // AppCompat
    //implementation(libs.insert.koin.koin.android) // Koin
    //implementation(libs.koin.androidx.compose) // Koin for Compose
    implementation(libs.retrofit) // Retrofit
    implementation(libs.converter.gson) // Gson Converter for Retrofit
    implementation(libs.okhttp) // OkHttp
    implementation(libs.okhttp3.logging.interceptor) // Logging for OkHttp
    implementation(libs.coil) // Coil
    implementation(libs.coil.compose) // Coil for Compose
    implementation(libs.coil.network.okhttp) // OkHttp client for Coil
    implementation(libs.androidx.compose.material.icons.extended) // Material Icons
    implementation(libs.androidx.navigation.compose) // Compose Navigation
    implementation(libs.kotlinx.serialization.json) // Serialization
    implementation (libs.androidx.datastore.preferences) // Jetpack DatsStore
    implementation(libs.core.splashscreen) // SplashScreen
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}