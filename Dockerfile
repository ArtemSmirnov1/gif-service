FROM gradle:7.4.2-jdk11 as builder
VOLUME /tmp
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --debug

FROM openjdk:11
EXPOSE 8080
COPY --from=builder /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]