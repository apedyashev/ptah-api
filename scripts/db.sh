#!/bin/sh
docker rm ptah-postgres
docker run --name ptah-postgres -v $PWD/../data:/var/lib/postgresql/data -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres:alpine 
