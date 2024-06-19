plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.project.smartfrigde"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.project.smartfrigde"
        minSdk = 26
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
}

dependencies {
    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation (libs.googleid)
    implementation (libs.play.services.location)
    implementation (libs.play.services.auth)
    implementation (libs.rxandroid)
    implementation (libs.rxjava)
    implementation (libs.rxjava3.retrofit.adapter)
    implementation (libs.security.crypto)
    implementation("com.github.NaikSoftware:StompProtocolAndroid:1.6.6")
    implementation (libs.jjwt.api)
    implementation (libs.guava)
    implementation (libs.jjwt.impl)
    implementation (libs.jjwt.jackson)
    implementation(libs.logging.interceptor)
    implementation (libs.glide)
    implementation (libs.jwtdecode)
    implementation(libs.okhttp)
    implementation (libs.converter.gson)
    implementation (libs.retrofit)
    implementation (libs.appcompat.vlatestversion)

    implementation (libs.circleimageview)
    implementation (libs.circleindicator)
    implementation (libs.readmore.textview)
    implementation(libs.appcompat.v161)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.activity.v190)
    testImplementation(libs.junit)
    implementation (libs.rxjava2.rxjava)
    implementation (libs.rxjava2.rxandroid)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("org.java-websocket:Java-WebSocket:1.3.0")
    implementation(libs.okhttp.v380)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.org.eclipse.paho.client.mqttv3)
    implementation (libs.org.eclipse.paho.android.service)
}