# install the image of the Maven – JDK version.
FROM maven:3.8.3-openjdk-17
# path of the working directory.
WORKDIR **/ebnelhaythem
# copy all the files inside the project directory to the container.
COPY . .
# execute a command-line inside the container: mvn clean install to install the dependencies in pom.xml.
RUN mvn clean install
# run script mvn spring-boot:run after the image is built.
CMD mvn spring-boot:run