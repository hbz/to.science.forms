#!/bin/bash

if (( $EUID == 0 )); then
    echo "Don't run as root!"
    exit
fi

actDir=$(pwd)
deployingApp="to.science.forms"
toscienceDir="/opt/regal"
deployDir="$toscienceDir/git/$deployingApp"
tmpDir="$toscienceDir/regal-tmp"
targetDir="$toscienceDir/apps"
linkDir="to.science.forms"
fileName="zettel-1.0-SNAPSHOT.zip"
folderName="zettel-1.0-SNAPSHOT"
newInstallDir="$linkDir-$(date +'%Y-%m-%d-T-%H-%M-%S')"
confDir="/etc/to.science/forms.conf"

cp $deployDir/target/universal/$fileName $tmpDir

cd $tmpDir
unzip $fileName

mv $tmpDir/$folderName $targetDir/$newInstallDir
rm $tmpDir/$fileName
mkdir $targetDir/$newInstallDir/logs

OLDDIR=`readlink $targetDir/$linkDir`
if [ -L $targetDir/$linkDir ]; then
        rm $targetDir/$linkDir
fi
ln -sf $targetDir/$newInstallDir $targetDir/$linkDir
rm -r  $targetDir/$newInstallDir/conf
ln -sf $confDir $targetDir/$newInstallDir/conf
cd $actDir

echo ""
echo "Neue Binärversion verfügbar unter $targetDir/$newInstallDir."
#echo "Port ist fest eingestellet auf: $PLAYPORT"
echo "Zum Umschalten auf die neue Version:"
echo "sudo service $deployingApp stop"
echo "evtl. den noch laufenden Prozess töten:"
echo "ps -eaf | grep $deployingApp | more"
echo "kill `cat $OLDDIR/RUNNING_PID`"
echo "sudo service $deployingApp start"
echo "Das Log unter $targetDir/$newInstallDir/logs/application.log beobachten"
