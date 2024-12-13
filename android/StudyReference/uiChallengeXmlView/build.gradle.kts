plugins {
    alias(libs.plugins.reference.android.application)
}

android {
    namespace = "com.seojinlee.studyreference"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.seojinlee.studyreference"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    dataBinding {
        enable = true
    }
}

dependencies {
    // 모듈을 활용 하려면 아래의 내용을 추가해 줘야함
//    implementation(project(":codeLabStudyLib"))

    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material.google)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity.ktx)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

android.sourceSets.all {
    java.srcDir("src/$name/kotlin")
}