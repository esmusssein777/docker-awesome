FROM java:8
COPY build/libs/docker-awesome-1.0.0.jar /docker-awesome.jar
RUN bash -c 'touch /docker-awesome.jar'
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/docker-awesome.jar"]
MAINTAINER guangzheng.li
