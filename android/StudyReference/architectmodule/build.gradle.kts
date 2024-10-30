
/*
* 이렇게 호출 하기 위해서는 build-logic 에서 등록한 register 의 클래스 정보와 함께
* 각 연결된 클래스에 plugin 연결이 필요하다. 여기서는 아래의 코드가 포함되어야지 스크립트 빌드가 된다.
* apply(plugin = "com.android.application")
* apply(plugin = "org.jetbrains.kotlin.plugin.compose")
* */
plugins {
    alias(libs.plugins.reference.android.application)
    alias(libs.plugins.reference.android.application.compose)
}

android {
    namespace = "com.study.architectmodule"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.study.architectmodule"
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
    implementation(project(":studyFeature:CommonUI"))

    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material3)
    implementation(libs.bundles.default.compose.designSystem)
    implementation(libs.androidx.compose.animation)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}