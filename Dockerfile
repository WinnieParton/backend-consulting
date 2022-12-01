FROM maven:3-openjdk-18

WORKDIR /backend_consulting
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run