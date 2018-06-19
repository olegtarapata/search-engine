# search-engine
Simple search engine

To run service or client Java 8 or above is required

To build project maven is also required

How to build and run:
```
git clone git@github.com:olegtarapata/search-engine.git
cd search engine

# build project
mvn clean package

# run server, default port is 8080
java -jar service/target/service-1.0-SNAPSHOT.jar

# run server with custom port
java -Dserver.port=<port> -jar service/target/service-1.0-SNAPSHOT.jar

# run client, client connects to localhot:8080
java -jar service/target/service-1.0-SNAPSHOT.jar

# run client with custom host and port
java -jar service/target/service-1.0-SNAPSHOT.jar <host> <port>

```
