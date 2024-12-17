plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)
  alias(libs.plugins.ksp)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.compose.compiler)
}

android {
  namespace = "xyz.tberghuis.floatingtimer"
  compileSdk = 35

  defaultConfig {
    applicationId = "xyz.tberghuis.floatingtimer"
    minSdk = 26
    targetSdk = 34
    versionCode = 72
    versionName = "1.40.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      buildConfigField("Boolean", "DEFAULT_PURCHASED", "false")
    }
    debug {
      buildConfigField("Boolean", "DEFAULT_PURCHASED", "false")
      // for taking screenshots, use airplane mode
      // buildConfigField("Boolean", "DEFAULT_PURCHASED", "true")
    }
    create("unlockTmp") {
      initWith(getByName("debug"))
      buildConfigField("Boolean", "DEFAULT_PURCHASED", "true")
      signingConfig = signingConfigs.getByName("debug")
    }
  }


  // todo read
  // https://stefma.medium.com/sourcecompatibility-targetcompatibility-and-jvm-toolchains-in-gradle-explained-d2c17c8cff7c
  // use sdkman to manage java versions
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.navigation.compose)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)

  androidTestImplementation(libs.androidx.junit.ktx)

  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)

  implementation(libs.accompanist.permissions)

  implementation(libs.room.runtime)
  implementation(libs.room.ktx)
  ksp(libs.room.compiler)

  implementation(libs.androidx.compose.material.iconsExtended)
  implementation(libs.androidx.dataStore.preferences)

  implementation(libs.ktor.client.core)
  implementation(libs.ktor.client.content.negotiation)
  implementation(libs.ktor.serialization.kotlinx.json)
  implementation(libs.ktor.client.okhttp)

  implementation(libs.billing.ktx)

  implementation(libs.color.picker)
  implementation(libs.colormath)
  implementation(libs.lifecycle.service)
  implementation(libs.screen.easy)

  androidTestImplementation(libs.test.rules)


  // https://www.youtube.com/watch?v=meRGGTFZ9Kc
//    constraints.implementation "androidx.fragment:fragment:1.6.2"
}

ksp {
  arg("room.schemaLocation", "$projectDir/schemas")
}