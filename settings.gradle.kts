pluginManagement {
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}
plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    flatDir {
      dirs("libs")
    }
  }
}

//includeBuild("/home/tom/Desktop/myprojects/android/ScreenEasy") {
//  dependencySubstitution {
//    substitute(module("io.github.torrydo:screen-easy")).using(project(":screenez"))
//  }
//}

rootProject.name = "Floating Countdown Timer"
include(":app")
 