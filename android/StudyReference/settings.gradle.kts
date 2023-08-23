pluginManagement {
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
include(":mainCotainer")
include(":codeLabStudyLib")
include(":composeStudyModule")
include(":sampleProject01DataLayer")
include(":sampleProject01DomainLayer")
include(":sampleProject01DiLayer")
