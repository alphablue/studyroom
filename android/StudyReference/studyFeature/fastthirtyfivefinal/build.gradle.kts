import org.gradle.internal.extensions.stdlib.capitalized
import org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompileTool

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
    id("kotlin-parcelize") // parcelable 객체를 kotlin-serialization 에서 사용하기 위해 쓰는 것
    alias(libs.plugins.reference.android.room)
}

android {
    namespace = "com.example.fastthirtyfivefinal"
    compileSdk = 35

    /**
     * room 과 protobuf 를 같이 쓰게 될 경우 ksp compiler 에서 annotation 해석에 대해서 오류가 있다고 한다.
     * 이를 해결 하기 위해서 https://github.com/google/dagger/issues/4051#issuecomment-1969345762 에서 제시하는 방법을 사용하였다.
    * */
    androidComponents {
        onVariants(selector().all()) { variant ->
            afterEvaluate {
                project.tasks.getByName("ksp" + variant.name.capitalized() + "Kotlin") {
                    val buildConfigTask = project.tasks.getByName("generate${variant.name.capitalized()}Proto")
                                        as com.google.protobuf.gradle.GenerateProtoTask

                    dependsOn(buildConfigTask)
                    (this as AbstractKotlinCompileTool<*>).setSource(buildConfigTask.outputBaseDir)
                }
            }
        }
    }

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

    // 파이어 베이스 설정 - plugins 설정 이후 사용 가능
    // sha-1 이 필요한 경우가 있는데 이는 ./gradlew signingReport 를 console 을 사용하면 모듈별로 sha-1이 나온다.
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    // 이걸 사용하려면 flavor 설정에서 productFlavors 을 해주면 사용할 수 있다.
//    prodImplementation(libs.firebase.analytics)

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
    implementation(libs.androidx.test.rules)
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