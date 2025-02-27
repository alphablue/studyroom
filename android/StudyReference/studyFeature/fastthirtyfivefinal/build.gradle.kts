plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.reference.android.hilt)
    id("kotlinx-serialization") // 이걸 쓸 때 build-logic 에서 정의한 feature 과는 별개로 사용해야된다.
    // 아래처럼 사용하고 싶더라도 .androidApplication 과 .android.hilt 쪽에 정의 해둔 것들의 충돌이 발생해서 불가능
//    alias(libs.plugins.reference.android.feature)
//    id("kotlinx-serialization")
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.example.fastthirtyfivefinal"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.fastthirtyfivefinal"
        minSdk = 29
        targetSdk = 34
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
        compose = true
    }
}

dependencies {

    implementation(project(":studyFeature:fastThirtyFive-domain"))
    implementation(project(":studyFeature:fastThirtyFive-data"))

    // 힐트 설정
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.compose.navigation)

    api(libs.androidx.compose.material)
    api(libs.bundles.compose.material3)

    // icon extended
    implementation(libs.androidx.compose.material.iconsExtended)
    // serialize
    implementation(libs.kotlinx.serialization.json)

    // kotlinx lib
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.dataStore)
    implementation(libs.protobuf.kotlin.lite)

    implementation(libs.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.bundles.use.navigation)

    // test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)
}

// plugin 을 연결 해야 지만 활용이 가능한 영역
// 관련 정보는 : https://github.com/google/protobuf-gradle-plugin#customizing-protobuf-compilation 참조
protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }

                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}