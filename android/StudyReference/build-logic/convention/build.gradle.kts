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
        /**
         * 여기서 설정하는 값들은 이후 toml 에 id 를 기준으로 등록하는 과정이 필요하다.
         * 등록하면 일반 적인 libs 호출 하는 방법과 같이 사용이 가능하다.
         * */
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