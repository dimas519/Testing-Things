FROM ghcr.io/graalvm/native-image-community:21 AS buildjar
WORKDIR /api1

COPY .mvn .mvn 
COPY mvnw pom.xml agent.zip ./
RUN chmod +x mvnw


COPY ./pom.xml .
COPY src ./src
# RUN mvn  clean package -DskipTests --batch-mode
RUN ./mvnw -e --batch-mode -DskipTests=true -DskipTests org.graalvm.buildtools:native-maven-plugin:compile  -Pnative -Pdynatrace-native


# Stage 2: Run
FROM oraclelinux:9-slim
WORKDIR /api1
COPY --from=buildjar /api1/target/api1  ./api1
COPY --from=buildjar /api1/target/dynatrace ./dynatrace


EXPOSE 8081
ENTRYPOINT ["./api1"]