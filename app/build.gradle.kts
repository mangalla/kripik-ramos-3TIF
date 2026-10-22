plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.ramkeripik"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.ramkeripik"
        minSdk = 24
        // 36, bukan 37: target 37 (Android 17) memblokir akses "local network" secara default,
        // yang membuat WebView error net::ERR_ACCESS_DENIED di emulator
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
