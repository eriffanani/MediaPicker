plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    id("maven-publish")
}

/*afterEvaluate {
    configure<PublishingExtension> {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.github.eriffanani"
                artifactId = "media-picker"
                version = "1.0.0"
                afterEvaluate {
                    artifact(tasks.getByName("bundleReleaseAar"))
                }
            }
        }
    }
}*/

android {
    namespace = "com.erif.mediapicker"
    compileSdk = 35

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    buildFeatures {
        dataBinding = true
    }

}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.window)
    implementation(libs.material)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.gridlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.flexbox)
    ksp(libs.databinding.compiler)

    // Navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // Image Processing
    implementation(libs.glide)
    implementation(libs.glide.transformations)
    ksp(libs.glide.ksp)

    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)

    // Other
    implementation(libs.content.loader)
    implementation(libs.snacking2)
    implementation(libs.preventdoubleclick)
    implementation(libs.quickstate)
    implementation(libs.toastyle)
    implementation(libs.countdown)
    // Log
    implementation(libs.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}