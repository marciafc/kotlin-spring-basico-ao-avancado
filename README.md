# Kotlin e Spring do ZERO ao Avançado

  - Extension function - ConverterExtensionFunction.kt
  
    - PostCustomerRequest.toCustomerModel()
    - PutCustomerRequest.toCustomerModel
    
  - Flyway
    - [https://flywaydb.org/documentation/concepts/migrations#versioned-migrations](https://flywaydb.org/documentation/concepts/migrations#versioned-migrations)
  
    - Incluir dependência
    
    - Incluir migrations: resources/db.migration
      - Seguir padrão no começo do nome do arquivo (V1__, V2__, etc...)
      - Ex.: V1__create_table_customer
      
  - ERRO 'No default constructor for entity:  : br.com.marcia.mercadolivro.model.CustomerModel'
  
    - Incluir plugin:
    
      ```id 'org.jetbrains.kotlin.plugin.jpa' version '1.4.32'``` 
      
    - ou plugin
    
      ```kotlin("plugin.jpa") version "1.4.30"```   
      
  - Plugins no build.gradle.kts: formas de incluir plugin do **KOTLIN**:
  
```
id("org.springframework.boot") version "2.4.3"
id("io.spring.dependency-management") version "1.0.11.RELEASE"
kotlin("jvm") version "1.4.30"
kotlin("plugin.spring") version "1.4.30"
kotlin("plugin.jpa") version "1.4.30"     
```

```
id 'org.springframework.boot' version '2.4.3'
id 'io.spring.dependency-management' version '1.0.11.RELEASE'	        	
id 'org.jetbrains.kotlin.jvm' version '1.4.32'
id 'org.jetbrains.kotlin.plugin.spring' version '1.4.32'
id 'org.jetbrains.kotlin.plugin.jpa' version '1.4.32'
```

  - Como sobrescrever um método set
  
    - BookModel.kt
    
  - Paginação
  
    - 2 formas: BookRepository.kt    
    - BookController.kt
      - @PageableDefault
    
  - Jackson somente exibir propriedades não nulas
  
    - application.yml -> default-property-inclusion: non_null    
    
  - Validações - Spring Validator
  
    - @Valid -> Controller
    - @field:NotEmpty
    - @field:Email    
    
  - Criar annotation
  
    - EmailAvailable.kt -> é uma annotation que valida
      - Informar quem vai fazer a validação em @Constraint
      
    - PostCustomerRequest.kt  -> usando a annotation com @EmailAvailable
  
  - Criar validador customizado 
  
    - EmailAvailableValidator.kt   
    
  - Swagger
  
    - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)   
    
  - Eventos do Spring
  
    - PurchaseController.kt
    
      - Usará outro tipo de **padrão de parser**, um **Mapper**. Nos demais controllers está sendo utilizado **Extension Function** :)
      
        - PurchaseMapper.kt: Mapper é uma outra alternativa de parser.
        - A injeção do BookService e CustomerService ficou no Mapper e não no controller.
        - Não precisa criar uma Extension Function, é uma classe especializada em criar o objeto esperado.
        
      - Disparando **evento** -> **Publisher**
      
        - PurchaseService.kt: dispara evento de compra
          - create \ applicationEventPublisher.publishEvent  
        
      - Ouvindo **evento** -> **Listener**
      
        - Tornar listener assíncrono -> ```@Async``` no método do listener + ```@EnableAsync``` na classe que sobe a aplicação (MercadoLivroApplication.kt)
        
        - Dizer que é vai ouvir um evento, que é um listener -> ```@EventListener```
          ```
          @Async
          @EventListener
          fun listen(purchaseEvent: PurchaseEvent) {
          ```
        - GenerateNfeListener.kt: ouve evento de compra e gera a nf
        
        - UpdateSoldBookListener.kt: ouve evento de compra e atualiza status dos livros (para VENDIDO)
        
      - TODO: não permitir vender um livro cujo status é VENDIDO, validar antes se o livro está disponível para a venda
      
      - TODO: criar endpoint que retorna os livros que o customer comprou e outro endpoint com os que ele vendeu
      
  - Spring Security
  
    - JWT (JSON Web Token)
    
    - [https://jwt.io](https://jwt.io/)
    
    - sub: identificação do usuário, id por exemplo
    
    - iat: até quando o token é válido
    
        
## Referências

  - [Tipos básicos do Kotlin](https://kotlinlang.org/docs/basic-types.html)

## Leituras extras

  - [Introduction to Kotlin Coroutines](https://www.baeldung.com/kotlin/coroutines)
  
  - [Setting up Spring Kafka With Kotlin](https://dev.to/magnuspedro/setting-up-spring-kafka-with-kotlin-53ea)
  
  - [Conhecendo o Spring WebFlux com Kotlin](https://www.linkedin.com/pulse/conhecendo-o-spring-webflux-com-kotlin-lucas-schwenke-paix%C3%A3o/?originalSubdomain=pt)
  
  - [DevDojo](https://devdojo.academy/#Cursos)
  
  - [Como criar um server gRPC usando Kotlin e Spring Boot](https://www.zup.com.br/blog/server-grpc-kotlin-spring-boot)
  
  - [Kotlin Coroutines](https://medium.com/ifood-tech/kotlin-coroutines-e6d048a59c40)
  
  - [Building a REST API using Kotlin (Ktor)](https://towardsdev.com/building-a-rest-api-with-ktor-a96247563c0)
  
  - [Testing Spring Boot Applications with Kotlin and Testcontainers](https://rieckpil.de/testing-spring-boot-applications-with-kotlin-and-testcontainers/)
  
  - [hexagonal architecture Github - Phill](https://github.com/Caps-Looking)
  
  - [hexagonal architecture Github](https://github.com/dustinsand/hex-arch-kotlin-spring-boot)
  
  - [Hexagonal Architecture by example - a hands-on introduction](https://blog.allegro.tech/2020/05/hexagonal-architecture-by-example.html)
  
  - [Example of the hexagonal architecture of the Netflix with kotlin](https://androidexample365.com/example-of-the-hexagonal-architecture-of-the-netflix-with-kotlin/)
  
  
