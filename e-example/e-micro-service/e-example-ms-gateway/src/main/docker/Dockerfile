FROM java:8
VOLUME /tmp/gateway
ADD e-example-ms-gateway-3.0.0-SNAPSHOT.jar e-example-ms-gateway.jar
RUN bash -c 'touch /e-example-ms-gateway.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dfile.encoding=UTF-8","-jar","/e-example-ms-gateway.jar"]