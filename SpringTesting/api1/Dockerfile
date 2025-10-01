FROM maven:3.9-eclipse-temurin-17 AS builderjar
WORKDIR /api1
COPY . .
RUN mvn clean package -DskipTests


FROM alpine/java:17-jre
 
WORKDIR /api1

# copy hasil build
COPY --from=builderjar /api1/target/api1.jar api1.jar

# Expose port default Spring Boot
EXPOSE 8081

# Gunakan JVM mode (bukan native)
ENTRYPOINT ["java","-jar","api1.jar"]
