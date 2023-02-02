FROM openjdk:11-jre-slim

ENV APP_HOME=/backend/

WORKDIR $APP_HOME

CMD rm *

COPY /build/libs/backend-0.0.1-SNAPSHOT.jar .

CMD java -jar backend-0.0.1-SNAPSHOT.jar

EXPOSE 8080