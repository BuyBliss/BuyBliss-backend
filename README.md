Sample application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/e_commerce
spring.datasource.username=root
spring.datasource.password=1234
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB


logging.level.org.springframework.web=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
logging.file.path=C:/Users/Shrinath/IdeaProjects/ECommerce/ECommerce/logs
#logging.level.com.yourpackage=INFO


# Producer Configuration
spring.kafka.producer.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer

# Kafka Producer Configuration  might be no use
#spring.kafka.producer.retries=3
#spring.kafka.producer.request-timeout-ms=5000
#spring.kafka.producer.delivery-timeout-ms=10000
#spring.kafka.properties.request.timeout.ms=4500
#spring.kafka.properties.socket.timeout.ms=4000
#spring.kafka.properties.retry.backoff.ms=1000

# Reduce the time Kafka waits for metadata from default 60s to 5s, achieves fast failure
spring.kafka.properties.max.block.ms=5000

#swagger
# default path : /swagger-ui.html
springdoc.swagger-ui.path=/docs
#  When true, automatically adds @ControllerAdvice responses to all the generated responses.
springdoc.override-with-generic-response=false
