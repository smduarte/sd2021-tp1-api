# base image
FROM nunopreguica/sd2021tpbase

# working directory inside docker image
WORKDIR /home/sd

# copy the jar created by assembly to the docker image
COPY target/*jar-with-dependencies.jar sd2021.jar

# copy the file of properties to the docker image
COPY trab.props trab.props

# copy the keystore and truststore to the docker image
COPY *.ks /home/sd/

# run Discovery when starting the docker image
CMD ["java", "-cp", "/home/sd/sd2021.jar", \
"sd2021.aula2.server.UserServer"]
