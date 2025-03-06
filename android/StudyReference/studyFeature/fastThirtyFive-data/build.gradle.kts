plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.reference.android.hilt)
    alias(libs.plugins.reference.android.room)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.fastthirtyfive_data"
    compileSdk = 35

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(project(":studyFeature:fastThirtyFive-domain"))

    ksp(libs.hilt.compiler)

    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material.google)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // serialize
    implementation(libs.kotlinx.serialization.json)
}