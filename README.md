Message Board REST Service
==========================

Simple REST service implemented with Java, Spring 4, Jersey and Jetty. Post messages to message board and list current messages with two different versions (without or with URL).

Setting the project
-------------------

  - Build: `mvn clean install`
  - Run: `mvn jetty:run` or run *JettyStarter.java* in your IDE
  - Packaging: *msgboard/target/msgboard-1.0-SNAPSHOT.war*

Design and implementation
-------------------------

#### Notes
- Project should be divided to multiple modules when code size increases
- Database persistence is not implemented but can be easily added
- REST API versioning was done using HTTP Accept headers to specify which response version is requested and received
- Integration test is only minimal and should be refactored
- Proper logging and error handling was left out of this scope


#### API
- List messages: GET http://localhost:8080/api/messages/list  
- Find by id: GET http://localhost:8080/api/messages/find/{id}
- Create message: POST http://localhost:8080/api/messages/create

Examples
--------
List messages v1:   
`curl -H "Accept: application/vnd.msgboard-v1+json" http://localhost:8080/api/messages/list`

List messages v2:   
`curl -H "Accept: application/vnd.msgboard-v2+json" http://localhost:8080/api/messages/list`

List messages v2 in XML format:   
`curl -H "Accept: application/xml" http://localhost:8080/api/messages/list`

Create message v2:   
`curl -X POST -H 'Content-type:application/json' -H 'Accept:application/json' http://localhost:8080/api/messages/create --data '{"title":"Header","content":"some content","sender":"john doe","url":"http://nitorcreations.com"}'`

Create message with invalid url v2:   
`curl -X POST -H 'Content-type:application/json' -H 'Accept:application/json' http://localhost:8080/api/messages/create --data '{"title":"Header","content":"some content","sender":"john doe","url":"invalidurl"}'`
