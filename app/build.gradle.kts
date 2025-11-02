plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.googleDaggerHilt)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.androidx.navigation.safeargs.kotlin)
}

android {
    namespace = "com.example.nbaapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.nbaapp"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.recyclerview)

    // SplashScreen Native API
    implementation(libs.androidx.core.splashscreen)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // ConstraintLayout
    implementation(libs.androidx.constraintlayout)

    // ProcessLifecycle
    implementation(libs.androidx.lifecycle.process)

    // Startup
    implementation(libs.androidx.startup.runtime)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // WorkManager
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    kapt(libs.androidx.hilt.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.logging.interceptor)

    // Room Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Paging
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.room.paging)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}

kapt {
    correctErrorTypes = true
}