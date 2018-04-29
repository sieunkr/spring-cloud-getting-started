작성 중...

# Spring Cloud Sleuth, Zipkin

분산 시스템에서는, 서로 다른 시스템에서 실행되는 많은 서비스들이 하나의 요청에 관련되었기 때문에 로그 추적은 어려워질수 있다. 직접적인 오류 분석을 위해서, 추적ID 로 연결해야 한다. 이 추적ID는 시스템 외부의 요청이 다운 스트림 서비스로 향할 떄 생성이 된다. 그래서 어쩃든 이 Spring Cloud Sleuth 는 추적 ID 구현을 지원하는 라이브러리이다. 이번 글에서는 Spring Cloud Sleuth 아 Zipkin 를 활용하여, 마이크로서비스 아키텍처에서의 로그 분석에 대해서 연구한다. 

- Spring Cloud Sleuth
- Zipkin

## 기본 개념 이해

#### Span
The basic unit of work. 

#### Trace
A set of spans forming a tree-like structure.

#### Logs
Represents an event in time associated with a span.

#### Tags
Key-value pair



## Zipkin 

Zipkin was originally developed at Twitter, based on a concept of a Google paper that described Google’s internally-built distributed app debugger –  [dapper](http://research.google.com/pubs/pub36356.html). It manages both the collection and lookup of this data. To use Zipkin, applications are instrumented to report timing data to it.

If you are troubleshooting latency problems or errors in ecosystem, you can filter or sort all traces based on the application, length of trace, annotation, or timestamp. By analyzing these traces, you can decide which components are not performing as per expectations, and you can fix them.

Internally it has 4 modules –

1.  **Collector**  – Once any component sends the trace data arrives to Zipkin collector daemon, it is validated, stored, and indexed for lookups by the Zipkin collector.
2.  **Storage**  – This module store and index the lookup data in backend.  [Cassandra](https://cassandra.apache.org/),  [ElasticSearch](https://www.elastic.co/)  and  [MySQL](https://howtodoinjava.com/mysql/how-to-installuninstallexecute-mysql-as-windows-service/)  are supported.
3.  **Search**  – This module provides a simple JSON API for finding and retrieving traces stored in backend. The primary consumer of this API is the Web UI.
4.  **Web UI**  – A very nice UI interface for viewing traces.


#### Zipkin 서버 설치
 
 방법1. 간단하게 다운로드 받아서 설치
 
아래와 같이 실행한다.
```java
curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar
```
도커로 설치할 때는 아래와 같이 실행한다.
```
docker run -d -p 9411:9411 openzipkin/zipkin
```

참고로 필자는 도커에 익숙하지 않기 때문에 첫번째 방법으로 설치를 하였다. 설치를 하면 9411 포트가 실행된 것을 확인할 수있다. 

```
netstat -tnlp
```

방법2. @EnableZipkinServer 를 적용한 방법



## Spring Cloud Sleuth

[Sleuth](https://cloud.spring.io/spring-cloud-sleuth/)  is a tool from Spring cloud family. It is used to generate the  _trace id_,  _span id_  and add these information to the service calls in the headers and MDC, so that It can be used by tools like Zipkin and  [ELK](https://howtodoinjava.com/microservices/elk-stack-tutorial-example/)  etc. to store, index and process log files. As it is from spring cloud family, once added to the  `CLASSPATH`, it automatically integrated to the common communication channels like –

-   requests made with the  [RestTemplate](https://howtodoinjava.com/spring/spring-restful/spring-restful-client-resttemplate-example/)  etc.
-   requests that pass through a  [Netflix Zuul](https://howtodoinjava.com/spring/spring-cloud/spring-cloud-api-gateway-zuul/)  microproxy
-   HTTP headers received at  [Spring MVC](https://howtodoinjava.com/spring-mvc-tutorial/)  controllers
-   requests over messaging technologies like Apache Kafka or RabbitMQ etc.

Using Sleuth is very easy. We just need to add it’s started pom in the spring boot project. It will add the Sleuth to project and so in its runtime.

`<dependency>`

`<groupId>org.springframework.cloud</groupId>`

`<artifactId>spring-cloud-starter-sleuth</artifactId>`

`</dependency>`

So far we have integrated Zipkin and Sleuth to microservices and ran Zipkin server. Let’s see how to utilize this setup.

```java
dependencies {  
   compile('org.springframework.cloud:spring-cloud-starter-sleuth')  
   compile('org.springframework.cloud:spring-cloud-starter-zipkin')  
```

```java
spring.application.name=client-app01  
spring.zipkin.baseUrl=http://zipkin:9411/  
spring.sleuth.sampler.probability=1
```
baseUrl 는 zipkin 서버를 지정한다. probability 는 로그 수집의 퍼센트를 설정한다. 1로 하면 아마도, 100프로? 무조건 zipkin 으로 로그를 전송한다. 



