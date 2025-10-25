FROM ghcr.io/graalvm/native-image-community:21 AS buildjar
WORKDIR /apigw

COPY .mvn .mvn 
COPY mvnw pom.xml agent.zip ./
RUN chmod +x mvnw


COPY ./pom.xml .
COPY src ./src
# RUN mvn  clean package -DskipTests --batch-mode
RUN ./mvnw -e --batch-mode -DskipTests=true -Pnative -Pdynatrace-native native:compile

# Stage 2: Run
FROM oraclelinux:9-slim
WORKDIR /apigw
COPY --from=buildjar /apigw/target/apigateway  ./apigw
COPY --from=buildjar /apigw/target/dynatrace ./dynatrace
EXPOSE 8080
ENTRYPOINT ["./apigw"]