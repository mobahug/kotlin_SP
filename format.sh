#!/bin/bash

# Format all Kotlin files in the project
echo "🎨 Formatting Kotlin files with ktlint..."
ktlint --format "**/*.kt"

# Check for any remaining issues
echo "🔍 Checking for lint issues..."
ktlint "**/*.kt"

echo "✅ Formatting complete!"
