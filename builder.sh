#!/bin/bash

profile=$1
arg=$2

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
  git pull

  cd docker
  docker compose -f docker-compose.${profile}.yml stop backend-$profile

  echo "Build backend image..."
  gradle -p backend/ jibDockerBuild -Djib.to.tags=$profile &> /dev/null
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
