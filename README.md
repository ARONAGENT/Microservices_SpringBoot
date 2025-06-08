# 🛒 E-Commerce Microservices Platform

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.x-blue.svg)](https://spring.io/projects/spring-cloud)
[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)

A comprehensive **E-Commerce Microservices Platform** built using **Spring Boot** and the **Spring Cloud ecosystem**. This project demonstrates how to architect a scalable, maintainable, and distributed microservice-based system with production-ready features including service discovery, API gateway, distributed tracing, and centralized logging.

## 🏗️ Architecture Overview

This microservices platform follows the **distributed architecture pattern** with the following core components:

![image](https://github.com/user-attachments/assets/363444ac-3585-483f-bfaf-7da4b8d851d6)


## ✨ Features

### Core Microservices Features
- 🔍 **Service Discovery** with Netflix Eureka
- 🌐 **API Gateway** with Spring Cloud Gateway
- 🔄 **Inter-service Communication** using OpenFeign
- 🛡️ **Circuit Breaker Pattern** with Resilience4J
- ⚖️ **Load Balancing** and **Rate Limiting**

### Advanced Features
- 🔐 **JWT Authentication** at Gateway level
- 🔧 **Centralized Configuration** with Spring Cloud Config
- 📊 **Distributed Tracing** with Zipkin & Micrometer
- 📝 **Centralized Logging** with ELK Stack
- 🔄 **Dynamic Configuration Refresh**
- 🌍 **Multi-Environment Support** (dev, test, prod)

### Business Operations
- 🛍️ **Order Management** (Create, Update, Cancel)
- 📦 **Inventory Management** (Stock tracking, Updates)
- 👤 **User Authentication** and Authorization
- 📱 **Real-time Stock Updates**

## 🛠️ Technologies Used

| Category | Technology | Purpose |
|----------|------------|---------|
| **Framework** | Spring Boot 3.x | Microservice foundation |
| **Cloud** | Spring Cloud 2023.x | Microservice patterns |
| **Service Discovery** | Netflix Eureka | Service registration & discovery |
| **API Gateway** | Spring Cloud Gateway | Request routing & filtering |
| **Communication** | OpenFeign | Declarative REST clients |
| **Resilience** | Resilience4J | Circuit breaker, retry, rate limiter |
| **Configuration** | Spring Cloud Config | Centralized configuration |
| **Security** | Spring Security + JWT | Authentication & authorization |
| **Monitoring** | Micrometer + Zipkin | Distributed tracing |
| **Logging** | ELK Stack | Centralized logging |
| **Database** | H2/MySQL | Data persistence |
| **Build Tool** | Maven | Dependency management |

## 🚀 Getting Started

### Prerequisites
- ☕ **Java 17+**
- 📦 **Maven 3.8+**
- 🐳 **ELK Stack Setup** 
- 🔍 **Zipkin Server**

### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/ARONAGENT/Microservices_SpringBoot_E-Commerce.git
   cd ecommerce-microservices
   ```

2. **Start Infrastructure Services**
   ```bash
   # Start Eureka Server
   cd Eureka_Sevice
   mvn spring-boot:run
   
   # Start Config Server
   cd ../config-server
   mvn spring-boot:run
   
   # Start API Gateway
   cd ../apiGatewayService
   mvn spring-boot:run
   ```

3. **Start Business Services**
   ```bash
   # Start Inventory Service
   cd inventoryService
   mvn spring-boot:run
   
   # Start Order Service
   cd ../orderService
   mvn spring-boot:run
   ```

4. **Start Monitoring (Optional)**
   ```bash
   # Start Zipkin
      java -jar zipkin-server-3.5.1-exec.jar

   # Start ELK Stack
   for Elastic -> run elastic.bat
   for Kibana -> run kibana.bat
   for Logstash -> run logstash -f logstash.conf
   
   ```

### Service URLs
- 🌐 **API Gateway**: http://localhost:8081
- 🔍 **Eureka Dashboard**: http://localhost:8761
- 📊 **Zipkin UI**: http://localhost:9411
- 📈 **Kibana Dashboard**: http://localhost:5601

## 📦 Services

### 1. Eureka Server (`eureka-server`)
- **Port**: 8761
- **Purpose**: Service registry and discovery
- **Features**: Service health checks, load balancing

### 2. API Gateway (`api-gateway`)
- **Port**: 8081
- **Purpose**: Single entry point for all requests
- **Features**: 
  - Request routing
  - Authentication filters
  - Rate limiting
  - Request/Response logging

### 3. Config Server (`config-server`)
- **Port**: 8080
- **Purpose**: Centralized configuration management
- **Features**: 
  - Git-based configuration
  - Environment-specific configs
  - Dynamic refresh

### 4. Order Service (`order-service`)
- **Port**: 9020
- **Purpose**: Order management operations
- **Features**:
  - Create/Cancel orders
  - Integration with Inventory service
  - Circuit breaker implementation

### 5. Inventory Service (`inventory-service`)
- **Port**: 9010
- **Purpose**: Product inventory management
- **Features**:
  - Stock management
  - Product catalog
  - Real-time updates

## 🔧 Configuration

### Environment Profiles
The application supports multiple environments:

- **Development** (`dev`): Local development with H2 database
- **Testing** (`test`): Integration testing environment
- **Production** (`prod`): Production-ready configuration

### Configuration Files Structure


![image](https://github.com/user-attachments/assets/00802096-4016-432b-86df-cfbcd2cfe5f7)


### Key Configuration Properties
```yaml
# Gateway Routes
spring:
  cloud:
    gateway:
      routes:
        - id: orderService
          uri: lb://ORDERSERVICE
          predicates:
            - Path=/api/v1/orders/**
          filters:
            - AddRequestHeader=X-Request-Id, Rohan
            - StripPrefix=2
            - name: LoggingOrdersFilter
            - name: Authentication
        - id: inventoryService
          uri: lb://INVENTORYSERVICE
          predicates:
            - Path=/api/v1/inventory/**
          filters:
            - AddRequestHeader=X-Request-Id, Rohan
            - StripPrefix=2
            - name: Authentication
```

## 📊 Monitoring & Observability

### Distributed Tracing
- **Zipkin** integration for request tracing
- **Micrometer** for metrics collection
- Trace correlation across services

### Centralized Logging
- **Elasticsearch** for log storage
- **Logstash** for log processing
- **Kibana** for log visualization

### Health Checks
- Actuator endpoints for service health
- Custom health indicators
- Service dependency monitoring

## 🔐 Security

### JWT Authentication
- Token-based authentication at API Gateway
- Stateless session management
- Role-based access control

### Gateway Filters
```java
@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final JwtService jwtService;

    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService=jwtService;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authenticationHeader=exchange.getRequest().getHeaders()
                    .getFirst("Authorization");
            if(authenticationHeader==null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            String token=authenticationHeader.split("Bearer ")[1];

            Long userId=jwtService.getUserIdFromToken(token);

            // Spring Boot 3.4.1
            ServerHttpRequest mutatedRequest=exchange.getRequest().mutate()
                    .header("X_User-Id",userId.toString())
                    .build();


            return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }
    public static class Config {

    }
}
```

## 📸 Screenshots

**1.Eureka Default**

![image](https://github.com/user-attachments/assets/d69a1e90-0c02-4a05-ba28-15c9fe8a422f)
<br><br>

**2.Both Services (Inventory & Order) Configured with Eureka**

![2 both service Inventory and orderconfigure with eureka](https://github.com/user-attachments/assets/03a8309c-0201-4f72-bd96-a8702401c989)
<br><br>


**3.Demo API Call from Inventory to Order Service**

![3 demo Api call from inventory to order service](https://github.com/user-attachments/assets/7359ac75-e7ac-433b-8be4-bd2b7cba0017)
<br><br>

**4.API Gateway Calling Inventory → Order (via Gateway)**

![4 apigatewaycalling_m and m2 calling m1 api means inventory calls order service via gateway ](https://github.com/user-attachments/assets/3916fb1f-5cf2-4087-981a-6c2646117ab6)
<br><br>

**5.Filters Added in API Gateway**

![5 add Filters in api gateway](https://github.com/user-attachments/assets/e29440fe-3258-4d68-a0cc-95752fc63803)
<br><br>

**6.OpenFeign Client Setup**

![6 Open Feign Client](https://github.com/user-attachments/assets/3cc2f86a-2596-4ebc-97d8-ba37543ba1d3)
<br><br>

**7.Order Items via OpenFeign and Reduce Product Stock**

![7 OpenFeign order items and it will order and reduce stocks from products](https://github.com/user-attachments/assets/d46d1e99-9d1a-4005-afbc-fb7a576e8352)
<br><br>

**8.After Ordering Items**

![8 After ordering items](https://github.com/user-attachments/assets/7433c215-228e-4e1e-b779-e95a95e9f401)
<br><br>

**9.Resilience4j Circuit Breaker Call**

![9 Resilience @CircuitBreaker call](https://github.com/user-attachments/assets/fb14a595-7944-4994-817d-325c4565d1ac)
<br><br>

**10.GlobalLogin Gateway Filter Working**

![11 GlobalLogin Gateway Filter Working Fine](https://github.com/user-attachments/assets/0343f03a-2fde-41fc-97cb-d66857fb9450)
<br><br>

**11.Global Logging Filter Registered in Gateway**

![12 Global Logging Filter Registered in Gateway Filter Chain ](https://github.com/user-attachments/assets/926fa034-ca30-485c-8002-6efdd6ed86a9)
<br><br>

**12.Custom Logging Filter for Order Service**

![13 Custom Orders Logging Gateway Filter For Order Service](https://github.com/user-attachments/assets/fefb49b5-19ab-41f4-9d2b-51e15b2cc8ed)
<br><br>

**13.Both Filters Registered Successfully**

![14 Both Filters Registered Successfully  ](https://github.com/user-attachments/assets/2c561262-2fc8-458f-9a4d-56f2464f72b0)
<br><br>

**14.Zipkin Dependencies Flow**

![15 zipkin dependencies fl;ow](https://github.com/user-attachments/assets/36fbd815-17fe-4db9-9d92-24f50f7ffe89)
<br><br>

**15.Elasticsearch Running Successfully**

![16  elastic search running successfully](https://github.com/user-attachments/assets/5539eb16-ddef-4ea1-b5cc-bc45b04053a9)
<br><br>

**16.Kibana Interface Working**

![17 Kibana interface workin successfully](https://github.com/user-attachments/assets/4f02c48a-03d5-4b13-a999-8b88e5a921fc)
<br><br>

**17.Logstash logs Sending To Elastic Search**

![image](https://github.com/user-attachments/assets/6d69d413-cd3a-4ecf-bb6b-ce5514631e88)
<br><br>

## 🎥 Demo Videos

The project includes comprehensive demo videos showcasing:


https://github.com/user-attachments/assets/c0bdba46-4de4-4ac0-b635-7a8f2de907e7



https://github.com/user-attachments/assets/73add77b-cfa9-4ab8-b129-b7f031d86640



https://github.com/user-attachments/assets/ebdc8373-410b-4e4e-a4ed-0dae654bda8e



https://github.com/user-attachments/assets/ce6d4989-deaa-4c78-816a-12f1605368c8



https://github.com/user-attachments/assets/66c1d20e-50fe-4f18-8306-b3ac780383c3



https://github.com/user-attachments/assets/c2419556-f6f3-43d5-b082-a94eef730f5c



https://github.com/user-attachments/assets/db8a361f-e848-427b-a3c4-cb5acf79e531



https://github.com/user-attachments/assets/57c70b69-cd46-4622-81bb-16a96df8062f




## 📚 Documentation

### API Endpoints

#### Order Service
```http
POST /orders/add
GET /orders/{id}
    /orders/all
PUT    /orders/update/{id}
DELETE /orders/delete/{id}
```

#### Inventory Service
```http
GET /inventory/products/all
GET /inventory/products/{id}
PUT /inventory/products/updateStocks
PUT /inventory/products/increaseStocks
```

#### Authentication
```http
http://localhost:8081/api/v1/..
```

### Detailed Documentation
- 📄 **API Documentation**: Add Dependency & Available at `/swagger-ui.html`
- 📖 **PDF Notes**: ....uploaded soon


## 🎯 Learning Outcomes

This project demonstrates:
- ✅ **Microservices Architecture** best practices
- ✅ **Spring Cloud** ecosystem integration
- ✅ **Production-ready** features implementation
- ✅ **Distributed system** challenges and solutions
- ✅ **DevOps** practices with monitoring and logging
- ✅ **Security** implementation in microservices
- ✅ **Performance** optimization techniques

## 🚀 Future Enhancements

- [ ] Kubernetes deployment
- [ ] Database per service pattern
- [ ] Event-driven architecture with Apache Kafka
- [ ] API versioning strategy
- [ ] Automated testing pipeline
- [ ] Service mesh with Istio

## 👨‍💻 Author

**Rohan Uke**  
Backend Developer | Java & Spring Boot Enthusiast

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/rohan-uke)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/ARONAGENT)

---

## ⭐ Show your support

Give a ⭐️ if this project helped you learn microservices architecture!

## 📞 Support

If you have any questions or need help with the project, please:
1. Check the [Issues](https://github.com/ARONAGENT/Microservices_SpringBoot_E-Commerce/issues) page
2. Create a new issue if your question isn't already answered
3. Contact me via [LinkedIn](https://linkedin.com/in/rohan-uke)

---

*Built with ❤️ using Spring Boot and Spring Cloud*
