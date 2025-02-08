plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.botaoap.appnews"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.botaoap.appnews"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    android {
        viewBinding {
            enable = true
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.facebook.shimmer)

    implementation(libs.github.bumptech.glide)
    annotationProcessor(libs.github.bumptech.glide.compiler)

    implementation(libs.squareup.retrofit2)
    implementation(libs.squareup.retrofit2.converter)

    implementation(libs.squareup.okhttp3)

    implementation(libs.io.insert.koin)

    testImplementation(libs.org.mockito.core)
    testImplementation(libs.org.mockito.kotlin)

    testImplementation(libs.org.jetbrains.kotlinx)

    testImplementation(libs.androidx.arch.core)

    testImplementation(libs.com.squareup.okhttp3.test)

    testImplementation(libs.io.mockk)
}