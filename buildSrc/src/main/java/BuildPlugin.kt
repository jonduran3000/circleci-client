import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention

class BuildPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.forEach { plugin ->
            when (plugin) {
                is AppPlugin -> {
                    val extension = target.extensions.getByType(AppExtension::class.java)
                    extension.configure()
                }
                is LibraryPlugin -> {
                    val extension = target.extensions.getByType(LibraryExtension::class.java)
                    extension.configure()
                }
                is JavaPlugin,
                is JavaLibraryPlugin -> {
                    target.convention.getPlugin(JavaPluginConvention::class.java).apply {
                        sourceCompatibility = JavaVersion.VERSION_1_8
                        targetCompatibility = JavaVersion.VERSION_1_8
                    }
                }
            }
        }
        target.configureKotlin()
    }

    private fun AppExtension.configure() {
        setCompileSdkVersion(28)
        defaultConfig {
            setMinSdkVersion(23)
            setTargetSdkVersion(28)
            versionCode = 1
            versionName = "1.0.0"
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        buildTypes {
            getByName("debug") {
                matchingFallbacks = listOf("release")
            }
        }
        lintOptions {
            isCheckReleaseBuilds = false
        }
    }

    private fun LibraryExtension.configure() {
        setCompileSdkVersion(28)
        defaultConfig {
            setMinSdkVersion(23)
            setTargetSdkVersion(28)
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
        }
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        libraryVariants.all {
            generateBuildConfigProvider.configure { isEnabled = false }
        }
        variantFilter {
            ignore = buildType.name != "release"
        }
    }
}