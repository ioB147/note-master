plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("com.google.gms.google-services")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}
android {
    namespace = "com.example.notemaster"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.notemaster"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    buildFeatures {
        compose = true
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
    implementation(libs.google.firebase.auth.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    //navigation
    implementation ("androidx.navigation:navigation-compose:2.7.7")

    //room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    //LIfecycle
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    //splashscreen
    implementation("androidx.core:core-splashscreen:1.0.1")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.12")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.12")

    //injection
    implementation ("com.google.dagger:hilt-android:2.49")
    ksp ("com.google.dagger:hilt-android-compiler:2.49")
    ksp("androidx.hilt:hilt-compiler:1.2.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    //coil
    implementation ("io.coil-kt:coil-compose:1.4.0")

    //maps
    implementation("com.google.maps.android:maps-compose:4.3.3")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    //koin
    implementation ("io.insert-koin:koin-android:3.4.0")

    //nabil
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

}