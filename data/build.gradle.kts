@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.example.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField(
            "String",
            "FREESOUND_API_BASE_URL",
            project.property("freeSoundBaseUrl").toString()
        )
        buildConfigField(
            "String",
            "FREESOUND_API_KEY",
            project.property("freeSoundApiKey").toString()
        )
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures{
        buildConfig = true
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.bundles.retrofit)
    implementation(libs.dagger.hilt)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.bundles.media3)
    implementation(libs.glide)
    kapt(libs.hilt.compiler)
    testImplementation(libs.junit)
    testImplementation(libs.mock)
    testImplementation(libs.core.testing)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}