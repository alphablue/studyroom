plugins {
    alias(libs.plugins.reference.android.application)
    alias(libs.plugins.reference.android.application.compose)
}

android {
    namespace = "com.analyticspractice.architectmodule"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.analyticspractice.architectmodule"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material3)
    implementation(libs.bundles.default.compose.designSystem)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}