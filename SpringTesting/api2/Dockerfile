FROM maven:3.9-eclipse-temurin-17 AS builderjar
WORKDIR /api2
COPY . .
RUN mvn clean package -DskipTests


FROM alpine/java:17-jre
 
WORKDIR /api2

# copy hasil build
COPY --from=builderjar /api2/target/api2.jar api2.jar

# Expose port default Spring Boot
EXPOSE 8092

# Gunakan JVM mode (bukan native)
ENTRYPOINT ["java","-jar","api2.jar"]
