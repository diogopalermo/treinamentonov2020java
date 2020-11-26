FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN

COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java","-jar","/tmp/target/trainingNov2020-0.0.1.jar"]
