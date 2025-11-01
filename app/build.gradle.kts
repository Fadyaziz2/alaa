plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.compose.compiler)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.alaa.alaagallo"
    compileSdk = 34

    viewBinding {
        enable = true
    }
    buildFeatures {
        dataBinding = true
    }

    defaultConfig {
        applicationId = "com.alaa.alaagallo"
        minSdk = 24
        targetSdk = 34
        versionCode = 5
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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

//    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.viewmodel)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.picasso)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.jsoup)

    //to view photo
//    implementation (libs.photoview)
    dependencies {
        implementation("com.davemorrissey.labs:subsampling-scale-image-view-androidx:3.10.0")
    }
    //coli
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation(libs.androidx.paging.runtime.ktx)

    implementation("com.intuit.sdp:sdp-android:1.1.0")
    implementation("com.intuit.ssp:ssp-android:1.1.0")

    implementation(libs.androidx.compose.navigation)
//hilt android
    implementation(libs.hiltAndroid)
    implementation(libs.hiltAndroidCompose)
    kapt(libs.hiltAndroidCompiler)
//dagger2
    implementation(libs.dagger)

    //okhttp
    implementation(libs.okHttp)

    implementation(project(":domain"))
    implementation(project(":data"))

    implementation ("androidx.compose.material:material-icons-extended")

}