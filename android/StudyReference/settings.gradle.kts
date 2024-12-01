pluginManagement {
    includeBuild("build-logic") // settings.gradle 을 쓰는 곳이 있다면 따로 구분해서 넣어야 되는 부분으로 생각됨
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "StudyReference"
include(":uiChallengeXmlView")
include(":uichallengecompose")
include(":architectmodule")

include(":studyFeature:challengeUI")
include(":studyFeature:CommonUI")
include(":toyappsentrypoints")
include(":studyFeature:fastthirtyfivefinal")
include(":studyFeature:fastThirtyFive-data")
include(":studyFeature:fastThirtyFive-domain")
include(":studyFeature:fastThirtyFive-di")
