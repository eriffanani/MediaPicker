plugins {
    id("com.android.library")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.erif.mediapicker"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.activity:activity:1.9.0")
    implementation("androidx.window:window:1.3.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.fragment:fragment:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.gridlayout:gridlayout:1.0.0")
    implementation("com.google.android.flexbox:flexbox:3.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Navigation
    val nav = "androidx.navigation:navigation"
    val navVersion = "2.7.7"
    implementation("$nav-fragment:$navVersion")
    implementation("$nav-ui:$navVersion")

    // Image Processing
    val glide = "com.github.bumptech.glide"
    val glideVersion = "4.16.0"
    implementation("$glide:glide:$glideVersion")
    implementation("jp.wasabeef:glide-transformations:4.3.0")
    ksp("$glide:ksp:$glideVersion")

    val media = "androidx.media3:media3"
    val mediaVersion = "1.3.1"
    implementation("$media-exoplayer:$mediaVersion")
    implementation("$media-ui:$mediaVersion")

    // Other
    val other = "com.github.eriffanani"
    implementation("$other:ContentLoader:1.2.0")
    implementation("$other:Snacking2:1.1.3")
    implementation("$other:PreventDoubleClick:1.0.0")
    implementation("$other:QuickState:1.4.5")
    implementation("$other:Toastyle:1.0.0")
    implementation("$other:Countdown:1.4.0")
    // Log
    implementation("com.jakewharton.timber:timber:5.0.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}