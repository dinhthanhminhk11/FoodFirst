import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName
import java.text.SimpleDateFormat
import java.util.Calendar


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.cacheFixPlugin)
    alias(libs.plugins.firebase.crashlytics)
//    alias(libs.plugins.gms.googleServices)
    alias(libs.plugins.android.dagger.hilt)
    alias(libs.plugins.ksp)
    kotlin("kapt")
}

fun getDate(): String {
    val format = "HH\'h\'-dd"
    val current = Calendar.getInstance().time
    return SimpleDateFormat(format).format(current)
}

android {
    namespace = "code.madlife.foodfirstver"
    compileSdk = 34

    defaultConfig {
        applicationId = "code.madlife.foodfirstver"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
        archivesName.set("FoodFirst-($versionCode-$versionName)${getDate()}")

        ndk {
            abiFilters += listOf(
                "x86", "x86_64", "armeabi", "armeabi-v7a",
                "arm64-v8a"
            )
        }
    }

    buildTypes {
        debug {

        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packaging {
        packagingOptions.resources.excludes += setOf(
            // Exclude AndroidX version files
            "META-INF/*.version",
            // Exclude consumer proguard files
            "META-INF/proguard/*",
            // Exclude the Firebase/Fabric/other random properties files
            "/*.properties",
            "fabric/*.properties",
            "META-INF/*.properties",
        )
    }

    kapt {
        correctErrorTypes = true
    }

    flavorDimensions += "environment"
    productFlavors {
        create("Boquan") {
            dimension = "environment"
            manifestPlaceholders["appLabel"] = "Food App BoQuan"
            buildConfigField("String", "BASE_URL", "\"https://reqres.in/api/\"")
            buildConfigField(
                "String",
                "KEY_128",
                "\"Sxqa3KHdPs1uSAjNOSVmcvE/OhUMH5eZXQLtoRSUd6G0a22PPPwS38/F/lryy3Cz\""
            )
            buildConfigField(
                "String",
                "IV_128",
                "\"foRC3P7jiVX9Z4Fgj0nm9QGP1H7eEEj9DW3z7VloN920a22PPPwS38/F/lryy3Cz\""
            )
        }
        create("Madlife") {
            dimension = "environment"
            manifestPlaceholders["appLabel"] = "Food App Madlife"
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://15d2-116-97-243-66.ngrok-free.app/api/v1/\""
            )
            buildConfigField(
                "String",
                "BASE_URL_IMAGE_CATEGORY",
                "\"https://mms.img.susercontent.com/\""
            )
            buildConfigField(
                "String",
                "KEY_128",
                "\"Sxqa3KHdPs1uSAjNOSVmcvE/OhUMH5eZXQLtoRSUd6G0a22PPPwS38/F/lryy3Cz\""
            )
            buildConfigField(
                "String",
                "IV_128",
                "\"foRC3P7jiVX9Z4Fgj0nm9QGP1H7eEEj9DW3z7VloN920a22PPPwS38/F/lryy3Cz\""
            )
        }
    }
    sourceSets {
        getByName("main") {
            jni {
                srcDirs("src\\main\\jniLibs")
            }
        }
    }


}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation("androidx.compose.runtime:runtime:1.5.1")
    // testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso)

    implementation(libs.androidx.activity.activity)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.legacy.support.v4)
    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.dynamic.links)
    // hilt
    implementation(libs.dagger.hilt.library)
    //TODO hilt not yet support KSP, after support, changed to ksp
    kapt(libs.dagger.hilt.compiler)

    // api
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.json)
    implementation(libs.retrofit.converter.scalars)
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging.interceptor)

    implementation(libs.glide)
    implementation(libs.eventbus)
    //ui library
    implementation(libs.shimmer)
    implementation(libs.circleindicator)
    implementation(libs.dotsindicator)
//    implementation(libs.viewPagerIndicator)
    implementation(libs.shortcutBadger)
//    implementation(libs.android.simple.tooltip)
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)
    // socket
    implementation("io.socket:socket.io-client:1.0.0") {
        exclude("org.json", "json")
    }

    //sign in google
//    implementation(libs.play.services.auth)
    //sign in facebook
    implementation(libs.facebook.android.sdk)
    //zalo sdk
    implementation("me.zalo:sdk-core:+")
    implementation("me.zalo:sdk-auth:+")
    implementation("me.zalo:sdk-openapi:+")
    // image picker
//    implementation(libs.android.image.picker)

    // logging
//    implementation(libs.track)
//    implementation(libs.protobuf.lite)
    implementation(libs.timber)
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.release)

    implementation("com.google.android.gms:play-services-location:17.0.0")
    implementation("com.google.maps.android:android-maps-utils-v3:2.3.0")
    implementation("com.airbnb.android:lottie:4.1.0")
    // debugImplementation because LeakCanary should only run in debug builds.
}

