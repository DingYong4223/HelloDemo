#!/bin/bash

# Build iOS Framework Script
# This script builds the Shared framework for iOS

set -e

echo "Building iOS Framework..."
echo "========================"

# Build for different iOS architectures
echo "Building for iOS x64 (Simulator)..."
./gradlew :shared:iosX64Binaries

echo "Building for iOS ARM64 (Device)..."
./gradlew :shared:iosArm64Binaries

echo "Building for iOS Simulator ARM64..."
./gradlew :shared:iosSimulatorArm64Binaries

echo ""
echo "iOS Framework build completed!"
echo "========================"
echo ""
echo "Next steps:"
echo "1. Open iosApp/iosApp.xcodeproj in Xcode"
echo "2. Link the Shared.framework to the iosApp target"
echo "3. Build and run the iOS app"

