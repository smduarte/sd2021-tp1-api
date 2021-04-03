#!/bin/bash

[ ! "$(docker network ls | grep sdnet )" ] && \
	docker network create --driver=bridge --subnet=172.20.0.0/16 sdnet


if [  $# -le 1 ] 
then 
		echo "usage: $0 -image <img> [ -test <num> ] [ -log OFF|ALL|FINE ] [ -sleep <seconds> ]"
		exit 1
fi 

# get the latest version
docker pull nunopreguica/sd2021-tester-tp1

# execute the client with the given command line parameters
docker run --rm --network=sdnet -it -v /var/run/docker.sock:/var/run/docker.sock nunopreguica/sd2021-tester-tp1 $*

