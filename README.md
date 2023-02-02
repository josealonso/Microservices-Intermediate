
### PORTS MAPPING

API-Gateway ----------------> Port 9191
Department-Service ---------> Ports: 8080, 8082
Employee-Service -----------> Port 8081
Config-Server --------------> Port 8888 (Spring Cloud Config)
Service Registry -----------> Port 8761 (Eureka Server)
Organization-Service -------> Port 8083
React-Frontend -------------> Port 3000
Zipkin Server ---------> Port 9411

#### Commands to create the services

```
curl -G https://start.spring.io/starter.zip -d dependencies=web,mysql,jpa,lombok -d type=maven-build -d groupId=info.josealonso -d artifactId=employee-service -o employee-service.zip
curl -G https://start.spring.io/starter.zip -d dependencies=web,mysql,jpa,lombok -d type=maven-build -d groupId=info.josealonso -d artifactId=department-service -o department-service.zip
```

### Service Registry and Discovery

- We need a mechanism to call other services without hardcoding their hostnames or port numbers.
- Since instances may come up and go anytime, we need some automatic service registration and discovery mechanism.

#### Run multiple instances of the same microservice

To run another instance of *department-service*

BASIC APPROACH

- `cd department-service`
- `java -jar -Dserver.port=8082 target/department-service-0.0.1-SNAPSHOT.jar`

LOAD BALANCER APPROACH

Eureka Server provides the Spring Cloud **load balancer** library.
