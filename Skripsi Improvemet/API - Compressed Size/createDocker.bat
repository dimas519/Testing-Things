docker network create skripsi_network

docker-compose -f .docker/docker-compose.yml up -d

docker network connect skripsi_network skripsi-db

docker network connect skripsi_network skripsi-phpmyadmin

@REM #sleep untuk memastikan mysql sudah start
timeout 30 >null 

docker exec -i skripsi-db mysql -uroot -proot --database="skripsi" < "Template/databaseCreate.sql"

docker build --tag ghcr.io/dimas519/skripsi_api:min-bullseye . -f .docker/Dockerfile





@REM docker container create --name apimain --network skripsi_network -p  5000:5000 main-api:1.0.0

@REM docker container start apimain

docker run -d --name apimain -p 5000:5000 -e DBPort=9928 -e DBName=skripsi -e DBiPAddress=localhost -e DBusername=skripsiDimas -e DBpassword=skripsi123 -e ApiPAddress=0.0.0.0 -e ApiPort=5000 -e ApiReload=False -e ApiWorker=50 -e skripsi-api=True dimas519/skripsi_api:min-bullseye 