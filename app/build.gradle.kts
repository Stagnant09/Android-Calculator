import java.io.File

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.0" // Use the version compatible with your Kotlin version
}



android {
    namespace = "com.example.calculator"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.calculator"
        minSdk = 34
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters += listOf("arm64-v8a", "armeabi-v7a", "x86_64")
        }

        externalNativeBuild {
            cmake {
                arguments += listOf(
                    "-DANDROID_PAGE_SIZE=16384"
                )
            }
        }

    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx) // Use the latest stable version
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.text)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json) // Or the latest version

    // Retrofit core
    implementation(libs.retrofit)

    // GSON converter (for JSON → Kotlin data classes)
    implementation(libs.converter.gson)

    // OkHttp (Retrofit uses this under the hood)
    implementation(libs.okhttp)

    // Optional: OkHttp logging interceptor (for debugging API calls)
    implementation(libs.logging.interceptor)

    // 2. Material Icons Core (Where Icons.Filled.Check lives) - THIS IS THE LIKELY MISSING PIECE
    implementation(libs.androidx.compose.material.icons.core)

    // 3. Material Icons Extended (For less common icons, often useful to include)
    implementation(libs.androidx.compose.material.icons.extended)

    implementation(libs.exp4j)

    implementation(libs.accompanist.webview)
}



tasks.register("createScreen") {
    val screenName = project.findProperty("screenName")?.toString()
        ?: error("Missing -PscreenName argument, example: ./gradlew createScreen -PscreenName=MyScreen")

    val container = project.findProperty("container")?.toString()
        ?: error("Missing -Pcontainer argument, example: ./gradlew createScreen -PscreenName=MyScreen -Pcontainer=myContainer")


    doLast {
        val capitalized = screenName.replaceFirstChar { it.uppercase() }
        val dirName = screenName.replaceFirstChar { it.lowercase() }

        val baseDir = File(projectDir, "src/main/java/com/example/calculator/ui/screens/$container/$dirName")
        baseDir.mkdirs()

        // 1. Composable
        File(baseDir, "${capitalized}.kt").writeText(
            """
            import androidx.compose.runtime.Composable
            import com.example.calculator.navigation.AppRoute

            @Composable
            fun ${capitalized}(
                viewModel: ${capitalized}ViewModel,
                onNavigate: (AppRoute) -> Unit
            ) {
                // TODO: UI
            }
            """.trimIndent()
        )

        // 2. ViewModel
        File(baseDir, "${capitalized}ViewModel.kt").writeText(
            """
            import com.example.calculator.foundation.CustomViewModel
            
            class ${capitalized}ViewModel :
                CustomViewModel<${capitalized}Contract.State, ${capitalized}Contract.Event, ${capitalized}Contract.Effect>() {

                override suspend fun handleEvent(event: ${capitalized}Contract.Event) {
                    when (event) {
                        else -> {
                        
                        }
                    }
                }
            }
            """.trimIndent()
        )

        // 3. Contract
        File(baseDir, "${capitalized}Contract.kt").writeText(
            """
            import com.example.calculator.foundation.CustomEffect
            import com.example.calculator.foundation.CustomEvent
            import com.example.calculator.foundation.CustomState
            
            sealed interface ${capitalized}Contract {

                data class State(
                    val placeholder: String = ""
                ) : CustomState

                sealed interface Event : CustomEvent {
                    
                }

                sealed interface Effect : CustomEffect {
                    
                }
            }
            """.trimIndent()
        )

        println("Created screen $capitalized at: $baseDir")
    }
}
