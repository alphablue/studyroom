import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.example.convention.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidCompose") {
            id = "reference.android.application.compose"
            implementationClass = "AndroidComposeConventionPlugin" // 파일 경로를 바르게 설정 해줘야됨, ../ 등으로 적절한 위치 uri 를 주자
        }

        register("androidApplication") {
            id = "reference.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "reference.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidLibrary") {
            id = "reference.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}