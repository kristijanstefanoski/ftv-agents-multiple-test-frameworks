#!/bin/sh
cd ../../ || exit
sudo docker builder prune
sudo docker-compose build
sudo docker-compose up