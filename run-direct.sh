#!/bin/bash

# Set up Java environment  
export JAVA_HOME=/opt/homebrew/opt/openjdk/libexec/openjdk.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH

echo "🚀 Running Kotlin script directly..."
kotlin Main.kt
