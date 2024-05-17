plugins {
    id("com.android.application")
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.nativeninjas.prod1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nativeninjas.prod1"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    // Allow references to generated code
    kapt {
        correctErrorTypes = true
        useBuildCache = true // Optional: enable build cache for kapt
        javacOptions {
            // Specify the Java version here
            option("target", "1.8")
        }
    }
    hilt {
        enableAggregatingTask = true
        enableTransformForLocalTests = true
    }

    // Habilitar la función de BuildConfig personalizado
    buildFeatures {
        buildConfig = true
        compose = true
        viewBinding = true
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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.play.services.location)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation ("io.reactivex.rxjava3:rxjava:3.0.13")

    // Retrofit: Adaptador para integrar Retrofit con RxJava 3
    implementation ("com.squareup.retrofit2:adapter-rxjava3:2.9.0")

    // Retrofit: Convertidor para Gson, para manejar la serialización y deserialización de datos JSON
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Firebase BOM (Bill of Materials): Plataforma para asegurar que todas las dependencias de Firebase estén en la misma versión
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))

    // Firebase Analytics: Módulo para recopilar y visualizar datos de análisis sobre el uso de la aplicación
    implementation("com.google.firebase:firebase-analytics")

    // Firebase Firestore: Módulo para interactuar con la base de datos Firestore de Firebase
    implementation ("com.google.firebase:firebase-firestore:23.0.3")

    // Servicios Google Maps
    implementation ("com.google.android.gms:play-services-maps:18.0.0")

    // Moshi
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation ("com.squareup.moshi:moshi:1.12.0")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //Corrutinas
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.46.1")
    kapt("com.google.dagger:hilt-android-compiler:2.46.1")
}