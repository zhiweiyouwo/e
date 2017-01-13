FROM java:8
VOLUME /tmp/single
ADD e-example-single-3.0.0-SNAPSHOT.jar e-example-single.jar
RUN bash -c 'touch /e-example-single.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dfile.encoding=UTF-8","-jar","/e-example-single.jar"]