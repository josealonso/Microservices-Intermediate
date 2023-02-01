
### PORTS MAPPING

API-Gateway ----------------> Port 9191
Department-Service ---------> Ports: 8080, 8082
Employee-Service -----------> Port 8081
Config-Server --------------> Port 8888
Service Registry -----------> Port 8761
Organization-Service -------> Port 8083
React-Frontend -------------> Port 3000
Zipkin Server ---------> Port 9411

#### Commands to create the services

```
curl -G https://start.spring.io/starter.zip -d dependencies=web,mysql,jpa,lombok -d type=maven-build -d groupId=info.josealonso -d artifactId=employee-service -o employee-service.zip
curl -G https://start.spring.io/starter.zip -d dependencies=web,mysql,jpa,lombok -d type=maven-build -d groupId=info.josealonso -d artifactId=department-service -o department-service.zip
```
