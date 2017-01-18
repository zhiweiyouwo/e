FROM java:8
VOLUME /tmp/upm
ADD e-example-ms-upm-3.0.0-SNAPSHOT.jar e-example-ms-upm.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/e-example-ms-upm.jar"]