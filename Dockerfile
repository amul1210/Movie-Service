FROM openjdk:10



ADD ./target/movie-service-0.0.1-SNAPSHOT.jar   /usr/src/movie-service.0.0.1-SNAPSHOT.jar

EXPOSE 8080

WORKDIR usr/src

ENTRYPOINT ["java", "-jar","movie-service.0.0.1-SNAPSHOT.jar"]


#steps to run
#start local mongo service
#package the application
#stop local mongo service
#sudo docker run -d --name mongo_db -e MONGO_INITDB_DATABASE=movie-service  --publish 27017:27017 mongo
#sudo docker rmi movie-service
#sudo docker build -t movie-service
#sudo docker run --name spring-app --network=host -p 8080:8080 movie-service
