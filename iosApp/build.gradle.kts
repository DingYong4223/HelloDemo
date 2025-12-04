// iOS App build configuration
// This file is used to build the iOS framework from the shared module

plugins {
    // No plugins needed for iOS app configuration
    // The iOS app is built using Xcode
}

// This is a placeholder for iOS app configuration
// The actual iOS app is built using Xcode (iosApp.xcodeproj)
// The shared module is built as a framework using Gradle

tasks.register("buildIOSFramework") {
    description = "Build iOS framework from shared module"
    doLast {
        println("To build iOS framework, run:")
        println("./gradlew :shared:iosX64Binaries")
        println("./gradlew :shared:iosArm64Binaries")
        println("./gradlew :shared:iosSimulatorArm64Binaries")
    }
}

