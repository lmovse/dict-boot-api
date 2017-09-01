FROM openjdk:8-jdk-alpine
MAINTAINER lmovse@qq.com
VOLUME /tmp
WORKDIR /dict
ADD target/dict-boot-api-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]