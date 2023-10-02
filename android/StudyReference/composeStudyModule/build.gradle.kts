
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.composestudymodule"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {compose = true}
    composeOptions {
        // https://developer.android.com/jetpack/androidx/releases/compose-kotlin
        // 위 링크에서 코틀린 버전과 컴포즈 버전의 세팅을 맞춰줘야함 toml에서 설정한 코틀린버전 확인
        kotlinCompilerExtensionVersion = "1.8.10"
    }
}

dependencies {
    implementation(project(":sampleProject01DomainLayer"))

    // 아래의 내용을 추가해야지만 toml에 name만 설정된 compose 모듈들을 불러 올 수 있음
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.default.compose.designSystem)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    debugApi(libs.androidx.compose.ui.tooling)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}