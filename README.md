# Java Server

An HTTP Server, written in Java, that handles most standard HTTP Requests.

##### Dependencies

- Java 8
- JUnit 4
- Maven

##### Acceptance Tests

[Cob Spec] (https://github.com/8thlight/cob_spec)

##### To Run The Server:

- Use maven to install dependencies and generate a jar file using
```
mvn clean install
```

- Start the server from the root directory by running the jar file, with a command like the following (replacing the jar file path with your own) :
```
java -jar target/javaserver-1.0-SNAPSHOT.jar
```
- To specify a public directory from which to serve files, you can use the "-D" flag followed by the name of an existing directory
```
java -jar target/javaserver-1.0-SNAPSHOT.jar -D some_directory/
```
- To specify the port on which the server will run, use the "-P" flag followed by the port number
```
java -jar target/javaserver-1.0-SNAPSHOT.jar -P 9090
```
