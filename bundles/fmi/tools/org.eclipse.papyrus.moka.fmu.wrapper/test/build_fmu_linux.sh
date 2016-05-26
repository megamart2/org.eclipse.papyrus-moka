#!/bin/sh

CONFIGURATION=$1 #should be Debug or Release

FMU_NAME="mokaC1"


rm fmu_build/binaries/linux64/*.so
rm *.fmu
 
cp ../$CONFIGURATION/libfmuwrapper.so fmu_build/binaries/linux64/$FMU_NAME.so

cd fmu_build

#32 bits
#mv resources/linux.gtk.x86/rcp resources
#rmdir resources/linux.gtk.x86

rm -rf resources/rcp/jre

#32 bits
#cp -R ~/Prog/jre_1.8.0_compact2_x86 resources/rcp/jre

#64bits
#cp -R ~/Prog/jre_1.8.0_compact2_x86_64 resources/rcp/jre

rm resources/logs.zip
zip ../$FMU_NAME.fmu  -r *
cd ..

