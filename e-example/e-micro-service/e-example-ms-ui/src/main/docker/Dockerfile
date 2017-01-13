FROM java:8
VOLUME /tmp/ui
ADD e-example-ms-ui-3.0.0-SNAPSHOT.jar e-example-ms-ui.jar
RUN bash -c 'touch /e-example-ms-ui.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dfile.encoding=UTF-8","-jar","/e-example-ms-ui.jar"]