FROM java:8
VOLUME /tmp/oauth2
ADD e-example-ms-oauth2-3.0.0-SNAPSHOT.jar e-example-ms-oauth2.jar
RUN bash -c 'touch /e-example-ms-oauth2.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dfile.encoding=UTF-8","-jar","/e-example-ms-oauth2.jar"]