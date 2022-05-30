FROM openjdk:18 AS build-stage
WORKDIR /build
COPY .mvn .mvn/
COPY pom.xml .
COPY src src/
COPY mvnw .

RUN chmod +x ./mvnw
RUN ./mvnw package -DskipTests

FROM openjdk:18

WORKDIR /app
COPY --from=build-stage /build/target/frontend-*.jar /app/frontend.jar
EXPOSE 8081
CMD ["java", "-jar", "frontend.jar", "-Djava.net.preferIPv4Stack=true"]