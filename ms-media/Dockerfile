FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

LABEL dev.ime.ms-media.version="1.0"
LABEL dev.ime.ms-media.maintainer="IvanM"
LABEL dev.ime.ms-media.description="Just a simple dockerfile"

COPY ./target/*.jar ./app.jar

ENTRYPOINT [ "java", "-jar", "./app.jar" ]