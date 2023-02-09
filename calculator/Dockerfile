FROM gradle:7.6.0-jdk11-alpine AS gradle

COPY ./src src/
COPY ./settings.gradle settings.gradle
COPY ./build.gradle build.gradle

RUN gradle clean build

FROM amazoncorretto:11-alpine-jdk

RUN mkdir /opt/app
WORKDIR /opt/app

# Copy built spring application
COPY --from=gradle /home/gradle/build/libs/calculator-0.0.1-SNAPSHOT.jar ./calculator.jar

# Download the agent - it should be copied to separate image and used with Docker's "FROM" command
RUN wget --header "Authorization: Basic ZGV2b3BzOnMyMDE1bA==" http://nex.sealights.co:8081/repository/sealights-internal/io/sealights/on-premise/agents/java-agent-bootstrapper-ftv/4.0.2216/java-agent-bootstrapper-ftv-4.0.2216.jar
RUN mv java-agent-bootstrapper-ftv-4.0.2216.jar sl-cd-agent.jar

# Copy run script
COPY --chmod=777 scripts/run_app.sh run_app.sh

EXPOSE 8080

WORKDIR /opt/app
CMD ["/opt/app/run_app.sh", "calculator.jar"]