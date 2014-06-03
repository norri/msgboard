Message Board REST Service
==========================

Simple REST service made with Java, Spring, Jersey and Jetty


Setting the project
-------------------

Maven build: <code>mvn clean install</code>

Starting Jetty in IDE: <code>run JettyStarter.java</code>

Start Jetty with maven: <code>mvn jetty:run</code>

WAR-file can be found: <code>msgboard/target/msgboard-1.0-SNAPSHOT.war</code>

Design and implementation notes
-------------------------------

- Project was divided into two layers, service layer and REST layer.
- Rest layer implements restful service by using Jersey framework
- Service layer contains all domain logic and database persistence(in-memory H2 into this case)
- Underlying database is made changeable with Spring injections f.ex. H2 for dev and test context and Postgres for production context
- Note that proper dev,test,prod context's were not implemented for this exercise
- REST API versioning was done using HTTP Accept headers to specify which response version is requested and received.
- Versioning related code duplication was reduced by subclassing resource beans(also Jackson annotations @JsonIgnore and @JsonProperty could be used)
- Bonus task was not implemented because Jersey doesn't support JAX-WS and time was running out to take Apache CFX to rescue.

Examples
--------

List messages v1:
curl -H "Accept: application/vnd.messageboard-v1+json" http://localhost:8080/messages

List message v2:
curl -H "Accept: application/vnd.messageboard-v2+json" http://localhost:8080/messages

List message v2 in XML format:
curl -H "Accept: application/vnd.messageboard-v2+xml" http://localhost:8080/messages

Create message v1:
curl -X POST -H 'Content-type:application/vnd.messageboard-v1+json' -H 'Accept:application/vnd.messageboard-v1+json' http://localhost:8080/messages --data '{"title":"new message","content":"some content","sender":"joe doe"}'

Create message v2:
curl -X POST -H 'Content-type:application/vnd.messageboard-v2+json' -H 'Accept:application/vnd.messageboard-v2+json' http://localhost:8080/messages --data '{"title":"new message","content":"some content","sender":"joe doe","url":"http://nitorcreations.fi"}'

Create message with invalid url:
curl -X POST -H 'Content-type:application/vnd.messageboard-v2+json' -H 'Accept:application/vnd.messageboard-v2+json' http://localhost:8080/messages --data '{"title":"new message","content":"content goes here","sender":"the sender","url":"invalidurl"}'


----
Post stuff (message) to server with curl

curl -H "Content-Type: application/json" -X POST -d '{"title":"my title", "content":"some content here", "sender": "me", "url": "http://foobar.com"}' http://localhost:8080/api/messages

Get stuff (messages) from server with curl

Boardy message v1 (only json supported): curl -H "Accept: application/json" -X GET http://localhost:8080/api/v1/messages

Boardy message v2 (json and xml supported) curl -H "Accept: application/json" -X GET http://localhost:8080/api/v2/messages

or

curl -H "Accept: application/xml" -X GET http://localhost:8080/api/v2/messages