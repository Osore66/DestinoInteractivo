plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt") // Añade esta línea
}

android {
    namespace = "com.example.destinointeractivo"
    compileSdk = 35 // ¡HEMOS CAMBIADO ESTE VALOR A 35!

    defaultConfig {
        applicationId = "com.example.destinointeractivo"
        minSdk = 24
        targetSdk = 34 // Puedes mantener targetSdk en 34 o subirlo a 35 si quieres.
        // Para este error, solo necesitas cambiar compileSdk.
        versionCode = 5
        versionName = "1.1"

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
    }
    composeOptions {
        // Asegúrate de que esta versión sea compatible con tu compileSdk 35 y las dependencias de Compose.
        // Si tienes problemas después, podrías necesitar actualizarla a una más reciente.
        kotlinCompilerExtensionVersion = "1.5.1"
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
    implementation(libs.androidx.lifecycle.process)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    val nav_version = "2.8.0"

    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1") // Esta es una de las que lo pedía.
    // Aunque con el compileSdk en 35,
    // puede que quieras actualizarla a la 2.6.x o 2.7.x
    // para aprovechar las últimas mejoras.
    implementation ("androidx.compose.runtime:runtime-livedata:1.4.0")

    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1") // Para usar Coroutines con Room


}

kapt {
    correctErrorTypes = true
}