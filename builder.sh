#!/bin/bash

# Define color codes
r='\033[0;31m' # red
g='\033[0;32m' # green
y='\033[0;33m' # yellow
nc='\033[0m'   # No Color

arg=$1

container="mobile/backend"

help="
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

function emptyLine() {
    echo -e "\n"
}
#-------

function clean() {
  docker ps -aq | xargs -r docker stop

  warning "Running command remove all containers"
  docker rm -f $(docker -a -q)

  docker volume prune -f
}

# Подтягивает изменения из гита
# Останавливает контейнер, обновляет, подчищает старые образы и перезапускает
function upgrade() {
  workflowID="${1}"
  export IMAGE_BUILD=$workflowID

  info "Running upgrade instance... build=${workflowID}"
#  gitPull

  docker compose -f docker/docker-compose.dev.yml stop backend

  info "Build backend image..."
  build=$(gradle -p backend/ jibDockerBuild -Djib.to.image="${container}:${workflowID}")
  result=$(echo "${build}" | grep -e 'BUILD SUCCESSFUL')

  if [[ $result =~ "BUILD SUCCESSFUL" ]]; then
    info "Clean unused images"
    docker images -a | grep none | awk '{ print $3; }' | xargs docker rmi --force
  else
    error "Failed to build new image!"
  fi

  info "Restart docker container..."
  docker compose -f docker/docker-compose.dev.yml run backend
}

function run() {
    gitPull

    info "Generate protobuf contracts..."
    gradle -p backend generateProto

    info "Stop backend container"
    docker compose -f docker/docker-compose.dev.yml down backend

    info "Build jib docker image"
    build=$(gradle -p backend/ jibDockerBuild -Djib.to.image="${container}:latest")

    result=$(echo "${build}" | grep -e 'BUILD SUCCESSFUL')

#    if [[ $result =~ "BUILD SUCCESSFUL" ]]; then
##      info "Clean unused images"
##      docker images -a | grep none | awk '{ print $3; }' | xargs docker rmi --force
#    else
#      error "Failed to build new image!"
#    fi

    info "Build and restart docker containers..."
    docker compose -f docker/docker-compose.dev.yml build backend
    docker compose -f docker/docker-compose.dev.yml up -d backend
}

function gitPull() {
    info "Upgrade project from repository Github"
    git pull
    emptyLine
}

case $arg in

  '--help' | '-h')
    echo welcome to help!
  ;;

  '--clean' | '-c')
    clean
  ;;

  '--upgrade' | '-u')
    upgrade $2
  ;;

  '--run')
    run
  ;;

  *)
    error "unknown argument"
    echo "$help"
esac
