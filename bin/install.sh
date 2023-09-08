#!/bin/bash

if (( $EUID == 0 )); then
    echo "Don't run as root!"
    exit
fi

actDir=$(pwd)
deployingApp="to.science.forms"
toscienceDir="/opt/toscience"
deployDir="$toscienceDir/git/$deployingApp"
tmpDir="$toscienceDir/tmp"
targetDir="$toscienceDir/apps"
linkDir="toscience-forms"
fileName="zettel-1.0-SNAPSHOT.zip"
folderName="zettel-1.0-SNAPSHOT"
newInstallDir="$linkDir-$(date +'%Y%m%d%H%M%S')"
confDir="/etc/toscience/forms"

cp $deployDir/target/universal/$fileName $tmpDir

cd $tmpDir
unzip $fileName

mv $tmpDir/$folderName $targetDir/$newInstallDir
rm $tmpDir/$fileName
mkdir $targetDir/$newInstallDir/logs

OLDDIR=`readlink $toscienceDir/$linkDir`
if [ -L $toscienceDir/$linkDir ]; then
        rm $toscienceDir/$linkDir
fi
ln -sf $targetDir/$newInstallDir $toscienceDir/$linkDir
rm -r  $targetDir/$newInstallDir/conf
ln -sf $confDir $targetDir/$newInstallDir/conf
cd $actDir

echo ""
echo "Neue Binärversion verfügbar unter $targetDir/$newInstallDir."
echo "Port ist fest eingestellet auf: 9003"
echo "Zum Umschalten auf die neue Version:"
echo "systemctl stop $linkDir.service"
echo "evtl. den noch laufenden Prozess töten:"
echo "ps -eaf | grep $linkDir | more"
echo "kill `cat $OLDDIR/RUNNING_PID`"
echo "systemctl start $linkDir.service"
echo "Das Log unter $targetDir/$newInstallDir/logs/application.log beobachten"
