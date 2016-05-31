#!/bin/sh

CONFIGURATION=$1 #should be Debug or Release

FMU_NAME="mokaC1"


rm fmu_build/binaries/win64/*.dll
rm *.fmu
 
cp ../$CONFIGURATION/libfmuwrapper.dll fmu_build/binaries/win64/$FMU_NAME.dll

cd fmu_build


rm -rf resources/rcp/jre



rm resources/logs.zip
zip ../$FMU_NAME.fmu  -r *
cd ..

