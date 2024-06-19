#!/bin/bash

profile=$1
arg=$2

container="mobile/backend"

help="
Use profile:
  --dev
  --prod

Parameters:

  --help(-h)             :print help message
  --clean(-c)            :completely deletes instance including DB
  --upgrade(-u)          :upgrade version backend application

  --run                  :build backend image and startup application
"

function clean() {
  echo "clean instance for profile: ${profile}"

}

function upgrade() {
  echo "upgrade instance profile: ${profile}"
#  git pull

  docker compose -f docker/docker-compose."${profile}".yml stop backend-"$profile"

  echo "Build backend image..."
  build=$(gradle -p backend/ jibDockerBuild -Djib.to.image="${container}:${profile}")
  result=$(echo "${build}" | grep -e 'BUILD SUCCESSFUL')

  if [[ $result =~ "BUILD SUCCESSFUL" ]]; then
      echo "rmi <none> images"
      docker images -a | grep none | awk '{ print $3; }' | xargs docker rmi --force
  fi
}

case $profile in
  '--dev')
    echo 'profile active: [develop]'
    profile='dev'
  ;;

  '--prod')
    echo 'profile active: [production]'
    profile='prod'
  ;;

  *)
    echo '[ERROR] please, set profile --develop or --production'
    exit
esac

case $arg in

  '--help' | '-h')
    echo welcome to help!
  ;;

  '--clean' | '-c')
    echo welcome to clean!
  ;;

  '--upgrade' | '-u')
    echo welcome to upgrade!
    upgrade
  ;;

  '--run')
    echo welcome to run!
  ;;

  *)
    echo '[ERROR] unknown argument'
    echo "$help"
esac
