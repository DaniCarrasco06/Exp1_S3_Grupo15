FROM eclipse-temurin:22-jdk AS buildstage
RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY pom.xml .
COPY src /app/src
COPY Wallet_* /app/wallet/
RUN mvn clean package -DskipTests

FROM eclipse-temurin:22-jdk
COPY --from=buildstage /app/target/*.jar /app/app.jar
COPY --from=buildstage /app/wallet /app/wallet
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]
