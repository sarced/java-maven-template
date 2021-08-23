FROM gcr.io/distroless/java:11

WORKDIR /app

COPY target/template-*.war app.war

CMD ["-jar", "/app/app.war"]
EXPOSE 8080
