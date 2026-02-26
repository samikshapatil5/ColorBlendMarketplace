pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "colorblendmarketplace"

include(":app") // Main app module
include(":app:admin")
include(":app:ordermanage")
include(":app:visiualzing")
include(":app:interiordesign")
include(":app:painter")

