# Java Server

An HTTP Server, written in Java, that handles most standard HTTP Requests.

##### Dependencies

- Java 8
- JUnit 4
- Maven

##### Acceptance Tests

[Cob Spec] (https://github.com/8thlight/cob_spec)

##### To Run The Server:

- Include the server in existing projects with Clojars

[![Clojars Project](https://img.shields.io/clojars/v/javaserver.svg)](https://clojars.org/javaserver)

- Or use maven to install dependencies and generate a jar file
```
$ mvn clean install
```

- Then start the server from the root directory by running the jar file
```
$ java -jar target/javaserver-1.0-SNAPSHOT.jar
```
- To specify a public directory from which to serve files, you can use the "-D" flag followed by the name of an existing directory
```
$ java -jar target/javaserver-1.0-SNAPSHOT.jar -D path/to/some_directory/
```
- To specify the port on which to run the server, use the "-P" flag followed by a valid port number
```
java -jar target/javaserver-1.0-SNAPSHOT.jar -P 9090
```
