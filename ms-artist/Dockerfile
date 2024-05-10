FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

LABEL dev.ime.ms-artist.version="1.0"
LABEL dev.ime.ms-artist.maintainer="IvanM"
LABEL dev.ime.ms-artist.description="Just a simple dockerfile"

COPY ./target/*.jar ./app.jar

ENTRYPOINT [ "java", "-jar", "./app.jar" ]