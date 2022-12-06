#!/bin/bash

if (( $EUID == 0 )); then
    echo "Don't run as root!"
    exit
fi

export TERM=xterm-color
deployingApp="to.science.forms"
# branch=$(git status | grep 'On branch' | cut -d ' ' -f3)
# echo "git's current branch is: "$branch
# if [ ! -z "$1" ]; then
#     branch="$1"
# fi

cd /opt/toscience/src/$deployingApp
# git pull origin $branch
/opt/toscience/activator/activator dist
