FROM java:8
VOLUME /tmp/discovery
ADD e-example-ms-discovery-3.0.0-SNAPSHOT.jar e-example-ms-discovery.jar
RUN bash -c 'touch /e-example-ms-discovery.jar'
ENTRYPOINT ["java","-Dfile.encoding=UTF-8","-jar","/e-example-ms-discovery.jar"]