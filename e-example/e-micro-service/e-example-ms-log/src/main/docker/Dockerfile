FROM java:8
VOLUME /tmp/log
ADD e-example-ms-log-3.0.0-SNAPSHOT.jar e-example-ms-log.jar
RUN bash -c 'touch /e-example-ms-log.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dfile.encoding=UTF-8","-jar","/e-example-ms-log.jar"]