#!/bin/bash

coverageExpected="30.0"

coverageResult=$(grep -oE '[0-9.]+%' ./app/build/reports/jacoco/jacocoTestFreeDebugUnitTestReport/html/index.html | head -n1)
coverageResult="${coverageResult//%}"

echo "Coverage expected $coverageExpected. Coverage Result $coverageResult "

# If the coverage requirements was satisfied
if ! echo "$coverageResult $coverageExpected -p" | dc | grep > /dev/null ^-; then
    echo "Coverage goal was satisfied"
		exit 0
	else
    echo "Error: Coverage goal was not satisfied"
		exit 1
fi
