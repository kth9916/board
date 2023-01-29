FROM openjdk:11
#VOLUME /tmp
#EXPOSE 8080
ARG JAR_FILE=*.jar
#COPY ${JAR_FILE} app.jar
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080
#ENV TZ=asia/Seoul
#RUN apt-get install -y tzdata