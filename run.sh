#!/bin/bash

# Set up Java environment
export JAVA_HOME=/opt/homebrew/opt/openjdk/libexec/openjdk.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH

echo "🔨 Compiling Kotlin code..."
kotlinc Main.kt -include-runtime -d Main.jar

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo "🚀 Running Task Tracker..."
    echo ""
    java -jar Main.jar
else
    echo "❌ Compilation failed!"
fi
