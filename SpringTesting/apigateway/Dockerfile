FROM maven:3.9-eclipse-temurin-17 AS builderjar
WORKDIR /apigw
COPY . .
RUN mvn clean package -DskipTests


FROM alpine/java:17-jre
 
WORKDIR /apigw

# copy hasil build
COPY --from=builderjar /apigw/target/apigw.jar apigw.jar

# Expose port default Spring Boot
EXPOSE 8080

# Gunakan JVM mode (bukan native)
ENTRYPOINT ["java","-jar","apigw.jar"]
