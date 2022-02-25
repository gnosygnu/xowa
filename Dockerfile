FROM java:8

WORKDIR /root

COPY build/bin bin
COPY res/user user

COPY build/xowa_dev.jar xowa_dev.jar

ENTRYPOINT java -jar xowa_dev.jar --app_mode http_server --http_server_port 8080
