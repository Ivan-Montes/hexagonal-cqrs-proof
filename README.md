# hexagonal-cqrs-proof

Microservices in a multimodule Maven project with Hexagonal Architecture, CQRS, and Kafka for synchronizing databases

**Components**
- [Kafka](https://kafka.apache.org/) [9092] + [9093] 
- [Kafka-UI](https://docs.kafka-ui.provectus.io/) [8080]
- REST API ms-artist with [OpenApi Swagger](https://swagger.io/) and two separate DB for R/RW actions [8081]
- REST API ms-media with [OpenApi Swagger](https://swagger.io/) and two separate DB for R/RW actions [8082]
- [Redis](https://redis.io/) DB in every microservice work as caches to store ID references for other REST APIs

```mermaid
graph RL

subgraph ms-artist
  direction LR
  subgraph ms-artist-ms
   A{{ms-artist}}
  end
  subgraph ms-artist-db
  direction LR
   A1[(NoSql Read Db)]
   A2[(Sql Write Db)]
   A3[(Redis Backup Db)]
   end
end

  Kafka(((Kafka)))
  KafkaUI(KafkaUI)

subgraph ms-media
  direction RL
  subgraph ms-media-ms
   B{{ms-media}}
  end
  subgraph ms-media-db
  direction RL
   B1[(NoSql Read Db)]
   B2[(Sql Write Db)]
   B3[(Redis Backup Db)] 
  end
end  
  
  ms-media-db <--> ms-media-ms
  ms-artist-db <--> ms-artist-ms
  ms-artist -->|Publish| Kafka
  Kafka -->|Subscriber| ms-artist
  ms-media -->|Publish| Kafka
  Kafka -->|Subscriber| ms-media
  KafkaUI <--> Kafka
  B1 <-.SYNCHRO.-> B2
  A1 <-.SYNCHRO.-> A2
```

## Table of contents

- [Installation](#installation)
- [Usage](#usage)
- [It's not a bug, it's a feature](#features)
- [Maintainers](#maintainers)
- [License](#license)


## Installation

1. First of all clone or download the project.

1. Inside the main folder, you could find two docker-compose yaml files.

1. From there use the command line to start the project in dev or production mode

```
    **Generate .jar**
    mvn clean package
    
    **Developing mode**  
    docker-compose -f docker-compose-dev.yml up -d

    **Production mode**
    docker-compose -f docker-compose-prod.yml up -d
```
      
The dev environment is ready for using with your IDE. The microservice attempts to communicate with Kafka using the local host. In production, it uses the archive Dockerfile to build an image of the project, so you wont need the IDE.
   
4. You could stop the project and free resources with any of these orders

```
    **Developing mode**
    docker-compose -f docker-compose-dev.yml down --rmi local -v
      
    **Production mode**
    docker-compose -f docker-compose-prod.yml down --rmi local -v  
```
   
## Usage

First of all, please visit the REST API documentation. Replace ${port} for the suitable microservice port:

    http://localhost:${port}/swagger-ui/index.html
    
[Kafka-UI](https://docs.kafka-ui.provectus.io/) allow you to check your [Kafka](https://kafka.apache.org/) server using a practical dashboard, so visit the following url:

    http://localhost:8080
    

## Features

#### :white_check_mark: Unit testing for business logic classes

#### :white_check_mark: Include two docker-compose yaml files for easy change of environment

#### :white_check_mark: Hexagonal Architecture following Clean Architecture principle

#### :white_check_mark: CQRS pattern with independent databases for Read or RW actions

#### :white_check_mark: DB synchronization by Publisher-Subscriber pattern

#### :white_check_mark: Redis DB in every microservice work as caches to store ID references for other REST APIs


## Maintainers

Just me, [Iván](https://github.com/Ivan-Montes) :sweat_smile:


## License

[GPLv3 license](https://choosealicense.com/licenses/gpl-3.0/)


---


[![Java](https://badgen.net/static/JavaSE/17/orange)](https://www.java.com/es/)
[![Maven](https://badgen.net/badge/icon/maven?icon=maven&label&color=red)](https://https://maven.apache.org/)
[![Spring](https://img.shields.io/badge/spring-blue?logo=Spring&logoColor=white)](https://spring.io)
[![GitHub](https://badgen.net/badge/icon/github?icon=github&label)](https://github.com)
[![Eclipse](https://badgen.net/badge/icon/eclipse?icon=eclipse&label)](https://https://eclipse.org/)
[![SonarQube](https://badgen.net/badge/icon/sonarqube?icon=sonarqube&label&color=purple)](https://www.sonarsource.com/products/sonarqube/downloads/)
[![Docker](https://badgen.net/badge/icon/docker?icon=docker&label)](https://www.docker.com/)
[![Kafka](https://badgen.net/static/Apache/Kafka/cyan)](https://kafka.apache.org/)
[![GPLv3 license](https://badgen.net/static/License/GPLv3/blue)](https://choosealicense.com/licenses/gpl-3.0/)