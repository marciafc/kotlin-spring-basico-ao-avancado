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

  - Injeção de dependência: boa prática
  
    - Sempre declarar **private**: evitar que outras classes tenham acesso indevidamente
```
@Service
class CustomerService(
        private val customerRepository: CustomerRepository,
        private val bookService: BookService,
        private val bCrypt: BCryptPasswordEncoder
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
  
    - Arquivos: **SecurityConfig.kt** e package **security**
  
    - Ao adicionar a dependência do Spring Security todas as rotas ficarão "bloqueadas" - 401 Unauthorized
    
    - Spring cria uma rota POST /login
  
    - JWT (JSON Web Token)
    
      - [https://jwt.io](https://jwt.io/)
      
      - Dependência 'jwt gradle dependency' -> [https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt/0.9.1](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt/0.9.1)
      
      - Gerar jwt.secret do application.yml -> [https://www.uuidgenerator.net/](https://www.uuidgenerator.net/)
      
      - Validar token JWT
      
        - Após chamar rota **POST /login**, copiar do response Header Authorization (exemplo Bearer eyJhbGciOiJIUzUx...)
        - Colar no site https://jwt.io tudo sem o texto 'Bearer ' e onde diz 'your-256-bit-secret', colar o jwt.secret do application.yml
          - Se ok, irá mudar para **'Signature Verified'**
          
      - Acessar endpoints com token JWT   
      
        - Realizar login na rota **POST /login**
        - Copiar **token do Header** de resposta 'Authorization' (exemplo Bearer eyJhbGciOiJIUzUxM..)
        - Colar esse token no endpoint que quer acessar (no **Header de request 'Authorization'**) e fazer a requisição. OU ainda na **aba Authorization** do POSTMAN sem texto 'Bearer '
    
    - sub: identificação do usuário, id por exemplo
    
    - iat: até quando o token é válido
    
    - Configurar as **roles do usuário**: tabela customer_roles
      - INSERT INTO mercadolivro.customer_roles(customer_id, `role`) VALUES(<INSERIR_ID>, 'CUSTOMER')
      - INSERT INTO mercadolivro.customer_roles(customer_id, `role`) VALUES(<INSERIR_ID>, 'ADMIN')
    
  - Automatizações do **POSTMAN**
  
    - Criar ambiente "Local" e suas variáveis:
          
      - base_url  -> localhost:8080
      - token     -> Bearer eyJhbGc...
      
    - Alterar todos endpoints para utilizar  o **Environment** "Local" e **{{base_url}}**
      
    - Criar automação para gerar o token e já preencher a respectiva variável 
    
      - No endpoint que realiza login **POST {{base_url}}/login** na aba **Tests** escrever script
      ```
      var token = pm.response.headers.get("Authorization")
      pm.environment.set('token', token)
      ```
      - Alterar endpoints para usar a variável **{{token}}** no header **Authorization**    
      
        - Exemplo: 
        
        ```
        GET {{base_url}}/customers/16
        
        - Headers do request:
        
        key: Authorization, Value: {{token}}
        ```
      
    - Quando for acessar um endpoint no POSTMAN, basta executar antes o endpoint de login, que ele já preencherá a var **{{token}}**
    
      - Após isso, chamar o endpoint que quer testar, não esquecendo de por no header da request **Authorization** com **{{token}}**                 

  - Customizando a paginação
  
    - PageResponse.kt
    - fun <T> Page<T>.toPageResponse(): PageResponse<T> em **ConverterExtensionFunction.kt**
    - BookController.kt
    
  - Configurar Profile
  
    - application.yml
    - OU VM Options -> -Dspring.profiles.active=prod     
  
  - Configurar Swagger para não estar disponível em prod
  
    - [Artigo Spring Profiles](https://www.baeldung.com/spring-profiles)
  
    - SwaggerConfig.kt -> ```@Profile("!prod")```  
    
    - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) 

  -  Jacoco: cobertura dos testes
  
    - Incluir plugin no build.gradle
    
    - Executar Tasks do Gradle:
    
      - verification \ check
      - verification \ jacocoTestCoverageVerification
      - verification \ jacocoTestReport
      
    - Abrir no navegador o report
    
      - build/reports/jacoco/test/html/index.html    
      
  - Testes unitários
  
    - Retorno método void
      - just runs
  
    - CustomerServiceTest -> `should delete customer`
    
      - ERRO: no answer found for: Optional(child of customerRepository...).orElseThrow(br.com.marcia.mercadolivro.service.CustomerService$findById$...)
      - Necessário usar **@SpyK** no inject mock **@InjectMockKs customerService** para testar método do próprio service, no caso findById(id)     

## Leituras extras

  - [Tipos básicos do Kotlin](https://kotlinlang.org/docs/basic-types.html)

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
  
  
