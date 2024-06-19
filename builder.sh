#!/bin/bash

profile=$1
arg=$2

help="
Use profile:
  --develop
  --production

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
}

case $profile in
  '--develop')
    echo 'profile active: [develop]'
    profile='develop'
  ;;

  '--production')
    echo 'profile active: [production]'
    profile='production'
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
  ;;

  '--run')
    echo welcome to run!
  ;;

  *)
    echo '[ERROR] unknown argument'
    echo "$help"
esac
