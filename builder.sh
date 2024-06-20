#!/bin/bash

# Define color codes
r='\033[0;31m' # red
g='\033[0;32m' # green
y='\033[0;33m' # yellow
nc='\033[0m'   # No Color

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

#-------Logger
function info() {
    echo -e "${g}[$(date '+%Y-%m-%d %H:%M:%S')][        ][INFO]: ${1}${nc}"
}

function warning() {
    echo -e "${y}[$(date '+%Y-%m-%d %H:%M:%S')][        ][WARNING]: ${1}${nc}"
}

function error() {
    echo -e "${r}[$(date '+%Y-%m-%d %H:%M:%S')][        ][ERROR]: ${1}${nc}"
}
#-------

function clean() {
  echo "clean instance for profile: ${profile}"

}

# Подтягивает изменения из гита
# Останавливает контейнер, обновляет, подчищает старые образы и перезапускает
function upgrade() {
  workflowID="${1}"
  info "Upgrade instance profile: ${profile}, build=${workflowID}"
#  git pull

  docker compose -f docker/docker-compose."${profile}".yml stop backend-"${profile}"

  info "Build backend image..."
  build=$(gradle -p backend/ jibDockerBuild -Djib.to.image="${container}:${profile}")
  result=$(echo "${build}" | grep -e 'BUILD SUCCESSFUL')

  if [[ $result =~ "BUILD SUCCESSFUL" ]]; then
    info "Clean unused images"
    docker images -a | grep none | awk '{ print $3; }' | xargs docker rmi --force
  else
    error "Failed to build new image!"
  fi

  info "Restart docker container..."
  docker compose -f docker/docker-compose."${profile}".yml run backend-"${profile}"
}

case $profile in
  '--dev')
    info "profile active: [develop]"
    profile='dev'
  ;;

  '--prod')
    info "profile active: [production]"
    profile='prod'
  ;;

  *)
    error "please, set profile --develop or --production"
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
    upgrade $3
  ;;

  '--run')
    echo welcome to run!
  ;;

  *)
    error "unknown argument"
    echo "$help"
esac
