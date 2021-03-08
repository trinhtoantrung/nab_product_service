# Product Service
This is a microservice application for product content (product, color, tag, brand, ...).

## The software development principles, patterns & practices
1. Using CommonsRequestLoggingFilter of spring boot & HandleInterceptor to log all request.
2. RequestBodyAdviceAdapter to log all request body if any.
3. All request logs are saved in database using async method to keep track the activity of users.
4. Define custom Specification of Product in order to search & filter product.
5. Apply Junit 5, Jacoco for unit test.
6. Apply Spring Cloud Sleuth for distributed tracing. 
7. Using Jpa Audit.

## Code folder structure, libs & framework
### Define package based on uses.
For example:

com.nab.assignment.product: root package.

com.nab.assignment.product.config.requestlog: group all classes related for request log config.

### Some key libs & framework
1. Spring boot starter web.
2. Spring boot starter jpa.
3. Spring boot starter logging.
4. Spring boot starter validation.
5. Spring boot starter test.
6. Spring boot configuration processor.
7. Lombok.
8. Apache common libs: lang3 & collections4.
9. Gson.
10. Jacoco.

## Development
### Required environment
1. JDK 8.
2. Maven 3.
4. PostgreSQL server.

### Start application on local computer
#### Requirement
1. Install PostgreSQL server on local computer.
2. Create database nab_product.
3. Change database config from application-local.yml. For example: 
```
  datasource:
    url: jdbc:postgresql://localhost:5432/nab_product
    username: postgres
    password: 2020
```
#### Run application in local:
```$xslt
mvn spring-boot:run -Dspring-boot.run.profiles=local
```
Root path: http://localhost:8081/api

#### Testing
```$xslt
mvn clean verify
```
Jacoco report: target/site/jacoco/index.html

#### All CURL commands
Get info of application
```$xslt
curl --location --request GET 'localhost:8081/api/about-us/info'
```

Get brand list
```$xslt
curl --location --request GET 'localhost:8081/api/product/brand/list'
```

Get color list
```$xslt
curl --location --request GET 'localhost:8081/api/product/color/list'
```

Get tag list
```$xslt
curl --location --request GET 'localhost:8081/api/product/tag/list'
```

Search product
```$xslt
curl --location --request GET 'http://localhost:8081/api/product/search?text=%&brands=sonder-living,azhome-living,osho-living&colors=red,green,blue&tags=hot,new,sale&sort=name,asc'
```

Update price
```$xslt
curl --location --request POST 'localhost:8081/api/product/update-price' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": "23fd1610-7c2f-4dfa-a0b7-a55ac3d7895a",
    "price": 123456
}'
```

Get product details
```$xslt
curl --location --request GET 'localhost:8081/api/product/id/23fd1610-7c2f-4dfa-a0b7-a55ac3d7895a'
```

Validate quantity 
```$xslt
curl --location --request GET 'http://localhost:8081/api/product/validate-quantity?id=23fd1610-7c2f-4dfa-a0b7-a55ac3d7895a&quantity=1'
```

Get price
```$xslt
curl --location --request GET 'http://localhost:8081/api/product/price?id=23fd1610-7c2f-4dfa-a0b7-a55ac3d7895a'
```



















