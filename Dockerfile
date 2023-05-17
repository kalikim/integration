# our base build image
FROM maven:3.8.3-openjdk-17 as maven
LABEL authors="anthonyk"
# copy the project files
COPY ./pom.xml ./pom.xml

# build all dependencies
RUN mvn dependency:go-offline -B

# copy your other files
COPY ./src ./src
# build for release
RUN mvn package -DskipTests
# our final base image
FROM openjdk:17-jdk-slim
# set deployment directory
WORKDIR /integration

COPY --from=maven target/integration-0.0.1-SNAPSHOT.jar ./

EXPOSE 8085
CMD ["java", "-jar", "./integration-0.0.1-SNAPSHOT.jar"]

