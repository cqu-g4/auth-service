FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app
COPY . /app/

RUN java --version
RUN ./mvnw clean install -Dmaven.test.skip=true

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

COPY --from=build /app/target/auth-service*.jar /app/
RUN find /app -iname auth-service-\* -exec ln -sf '{}' /app/auth-service.jar \;

RUN ls -l /app

ENV JAVA_OPTS "-Xms512m -Xmx512m"
#ENV APP_ARGS "-Dspring.config.location=/app/conf/application.properties "

HEALTHCHECK CMD nc -z localhost 7090
EXPOSE 7090

CMD java $JAVA_OPTS $APP_ARGS -jar /app/auth-service.jar
