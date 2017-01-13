FROM java:8
VOLUME /tmp/db
ADD e-example-ms-db-3.0.0-SNAPSHOT.jar e-example-ms-db.jar
RUN bash -c 'touch /e-example-ms-db.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dfile.encoding=UTF-8","-jar","/e-example-ms-db.jar"]