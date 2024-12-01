import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.example.convention.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

/**
 *
 *   kotlin 2.0 버전으로 변경되면서 아래 [1] 방식을 사용
 * */

//tasks.withType<KotlinCompile>().configureEach {
//    kotlinOptions {
//        jvmTarget = JavaVersion.VERSION_17.toString()
//    }
//}

/**
 *  import org.jetbrains.kotlin.config.JvmTarget 이 아닌
 *  import org.jetbrains.kotlin.gradle.dsl.JvmTarget 을 호출해야지만 사용이 가능하다.
 *
 *  org.jetbrains.kotlin.jvm 의 그레이들 설정이 2.0.0 이상인지 확인 필요
 *  jetbrainsKotlinJvm = { id = "org.jetbrains.kotlin.jvm", version.ref = "jetbrainsKotlinJvm" }
 *  * */
// [1]
kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    /**
     * 이 내용은 kotlin 2.0.0 이후 Dsl 방식을 사용하기 위해 필수로 들어가는 부분이다.
     * toml 설정시 필요하고 이후에는 
     * */
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        /**
         * 여기서 설정하는 값들은 이후 toml 에 id 와 똑같이 등록하는 과정이 필요하다.
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

        register("hilt") {
            id = "study.reference.hilt"
            implementationClass = ""
        }
    }
}