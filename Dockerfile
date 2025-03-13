FROM maven:3.6-openjdk-17 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package


FROM openjdk:17
RUN microdnf install -y curl unzip \
    && curl -s https://bin.equinox.io/c/4VmDzA7iaHb/ngrok-stable-linux-amd64.zip -o ngrok.zip \
    && unzip ngrok.zip \
    && mv ngrok /usr/local/bin/ \
    && rm ngrok.zip
	
RUN	ngrok authtoken 2hjzY2DbFBLQVbLeLsD6tJkshji_7vKfprY1XnrdnSj8VaRRc

COPY --from=build /app/target/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]