import com.android.build.api.variant.BuildConfigField
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

val localProperties = gradleLocalProperties(File(rootDir.path + "/app/"), providers)
val backendUrl = localProperties.getProperty("BACKEND_SERVER_URL")
val glideClientId = localProperties.getProperty("GLIDE_CLIENT_ID")

android {
    namespace = "com.glideapi.glide_quickstart"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.glideapi.glide_quickstart"
        minSdk = (rootProject.extra["defaultMinSdkVersion"] as Int)
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BACKEND_SERVER_URL", backendUrl)
            buildConfigField("String", "GLIDE_CLIENT_ID", glideClientId)
        }
        debug {
            buildConfigField("String", "BACKEND_SERVER_URL", backendUrl)
            buildConfigField("String", "GLIDE_CLIENT_ID", glideClientId)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
//    androidComponents {
//        onVariants {
//            it.buildConfigFields.put(
//                "BACKEND_SERVER_URL",
//                BuildConfigField(
//                    "STRING",
//                    backendUrl.toString(),
//                    "BACKEND_SERVER_URL"
//                )
//            )
//            it.buildConfigFields.put(
//                "GLIDE_CLIENT_ID",
//                BuildConfigField(
//                    "STRING",
//                    glideClientId.toString(),
//                    "GLIDE_CLIENT_ID"
//                )
//            )
//        }
//    }
    buildTypes {
        release {
            // These values are defined only for the release build, which
            // is typically used for full builds and continuous builds.
            buildConfigField("String", "BACKEND_SERVER_URL", "\"${backendUrl}\"")
            buildConfigField("String", "GLIDE_CLIENT_ID", "\"${glideClientId}\"")
        }
        debug {
            // Use static values for incremental builds to ensure that
            // resource files and BuildConfig aren't rebuilt with each run.
            // If these rebuild dynamically, they can interfere with
            // Apply Changes as well as Gradle UP-TO-DATE checks.
            buildConfigField("String", "BACKEND_SERVER_URL", "\"${backendUrl}\"")
            buildConfigField("String", "GLIDE_CLIENT_ID", "\"${glideClientId}\"")
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.glide.sdk.android)
    implementation(libs.okhttp3.okhttp)

}