plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // El plugin kotlin.compose NO se necesita aquí si ya usas kotlin.android y composeBom
    // alias(libs.plugins.kotlin.compose) // <--- ¡Esta línea sigue comentada/eliminada!
    // KSP se aplica directamente aquí con su versión explícita para evitar problemas de resolución.
    id("com.google.devtools.ksp") version "1.9.23-1.0.20" // <-- ¡Versión explícita de KSP!
}

android {
    namespace = "com.example.examen_dispmoviles"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.examen_dispmoviles"
        minSdk = 21
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        // ¡Esta es la línea FINALMENTE corregida para alinear el compilador de Compose con Kotlin 1.9.23!
        kotlinCompilerExtensionVersion = "1.5.12" // Asegura esta versión para Kotlin 1.9.23
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose BOM: ¡Fundamental para gestionar versiones de Compose!
    // Recuerda que en libs.versions.toml debe ser "2024.06.00" para esta compatibilidad.
    implementation(platform(libs.androidx.compose.bom))

    // Dependencias de Compose UI (todas referenciadas desde libs.versions.toml)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Dependencias para Navigation Compose (referenciada desde libs.versions.toml)
    implementation(libs.androidx.navigation.compose)

    // Dependencias para ViewModel Compose (referenciada desde libs.versions.toml)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Dependencias para Room (Base de Datos Local) (referenciadas desde libs.versions.toml)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler.ksp)

    // Dependencias para testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom)) // BOM también para tests
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Dependencias de depuración (tooling para preview en Android Studio)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}