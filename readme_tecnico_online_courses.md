# Online Courses - Monolito Modular

Proyecto de aprendizaje construido con **Java 21**, **Spring Boot**, **Maven**, **PostgreSQL**, **Flyway** y **JPA/Hibernate** para entender cómo diseñar un **monolito modular** con separación por capas, comunicación unidireccional entre módulos y reglas de negocio protegidas en el dominio.

---

## 1. Objetivo del proyecto

El objetivo de `online-courses` es modelar una plataforma básica de cursos online donde:

1. Se crean usuarios.
2. Se crean cursos.
3. Un usuario se inscribe a un curso.
4. Se crea una orden de pago para la inscripción.
5. Se procesa el pago.
6. Al confirmarse el pago, la inscripción se activa.

El proyecto busca practicar:

- Monolito modular.
- Clean Architecture por módulo.
- DDD básico.
- Separación de responsabilidades.
- Comunicación interna mediante APIs de módulo.
- Persistencia con PostgreSQL usando un schema por módulo.
- Migraciones con Flyway.
- Manejo global de respuestas y excepciones.
- Transacciones para mantener consistencia.

---

## 2. Stack tecnológico

- Java 21
- Spring Boot
- Maven multi-module
- PostgreSQL
- Flyway
- Spring Data JPA
- Hibernate
- REST APIs

---

## 3. Estructura general del proyecto

```text
online-courses
├── pom.xml
├── online-courses-app
├── shared
├── identity
├── catalog
├── enrollment
└── billing
```

### Responsabilidad de cada módulo

| Módulo | Responsabilidad |
|---|---|
| `online-courses-app` | Aplicación Spring Boot ejecutable. Levanta el monolito. |
| `shared` | Código común reutilizable, como `ApiResponse`. |
| `identity` | Gestión de usuarios. |
| `catalog` | Gestión de cursos. |
| `enrollment` | Gestión de inscripciones. |
| `billing` | Gestión de órdenes de pago. |

---

## 4. Comunicación entre módulos

La comunicación entre módulos es **unidireccional**.

```text
billing -> enrollment -> identity
                    -> catalog
```

### Reglas importantes

- `billing` puede comunicarse con `enrollment`.
- `enrollment` puede comunicarse con `identity` y `catalog`.
- `identity` no depende de ningún módulo de negocio.
- `catalog` no depende de ningún módulo de negocio.
- Los módulos no acceden directamente a repositorios, entidades JPA ni tablas internas de otros módulos.

La comunicación se realiza mediante APIs internas de módulo:

```text
IdentityModuleApi
CatalogModuleApi
EnrollmentModuleApi
```

Ejemplo:

```text
enrollment -> IdentityModuleApi.existsActiveUser(userId)
enrollment -> CatalogModuleApi.existsActiveCourse(courseId)
billing -> EnrollmentModuleApi.existsPendingEnrollment(enrollmentId)
billing -> EnrollmentModuleApi.activateEnrollment(enrollmentId)
```

---

## 5. Arquitectura interna por módulo

Cada módulo sigue una estructura por capas:

```text
api
 ↓
application
 ↓
domain

infrastructure
 ↑
application / domain
```

### Capas

| Capa | Responsabilidad |
|---|---|
| `api` | Controladores REST, requests, responses y exception handlers. |
| `application` | Casos de uso, comandos, respuestas, puertos, servicios internos y APIs de módulo. |
| `domain` | Modelos, reglas de negocio, value objects, enums y excepciones de negocio. |
| `infrastructure` | Configuración Spring, repositorios concretos, entidades JPA, mappers y adaptadores técnicos. |

### Regla principal

El dominio no depende de Spring, JPA, PostgreSQL ni detalles técnicos.

---

## 6. Módulo `shared`

El módulo `shared` contiene elementos comunes reutilizables por todos los módulos.

Actualmente contiene:

```text
shared
└── src/main/java/com/onlinecourses/shared/api/response
    └── ApiResponse.java
```

### `ApiResponse`

Estructura estándar para las respuestas HTTP:

```json
{
  "status": 201,
  "message": "Operación exitosa",
  "data": {},
  "timestamp": "2026-05-07T00:00:00"
}
```

Los módulos `identity`, `catalog`, `enrollment` y `billing` dependen de `shared` para reutilizar esta respuesta.

---

## 7. Módulo `identity`

Responsable de la gestión de usuarios.

### Entidad principal

```text
User
```

Campos principales:

```text
UUID id
String fullName
Email email
UserRole role
UserStatus status
LocalDateTime createdAt
```

### Value Object

```text
Email
```

Reglas:

- No puede ser `null`.
- No puede estar vacío.
- Debe contener `@`.
- Se normaliza para evitar inconsistencias.

### Estados y roles

```java
UserRole:
- STUDENT
- INSTRUCTOR

UserStatus:
- ACTIVE
- INACTIVE
```

### Endpoint principal

```http
POST /users
```

### Persistencia

```text
identity.users
```

### API interna

```java
IdentityModuleApi
```

Método principal:

```java
boolean existsActiveUser(UUID userId);
```

Usado por `enrollment` para validar que un usuario exista y esté activo.

---

## 8. Módulo `catalog`

Responsable de la gestión de cursos.

### Entidad principal

```text
Course
```

Campos principales:

```text
UUID id
String title
String description
BigDecimal price
CourseStatus status
LocalDateTime createdAt
```

### Reglas de dominio

- El título no puede estar vacío.
- La descripción no puede estar vacía.
- El precio debe ser mayor a cero.
- Un curso nuevo inicia en estado `ACTIVE`.

### Estado

```java
CourseStatus:
- ACTIVE
- INACTIVE
```

### Endpoint principal

```http
POST /courses
```

### Persistencia

```text
catalog.courses
```

### API interna

```java
CatalogModuleApi
```

Método principal:

```java
boolean existsActiveCourse(UUID courseId);
```

Usado por `enrollment` para validar que un curso exista y esté activo.

---

## 9. Módulo `enrollment`

Responsable de gestionar inscripciones de usuarios a cursos.

### Entidad principal

```text
Enrollment
```

Campos principales:

```text
UUID id
UUID userId
UUID courseId
EnrollmentStatus status
LocalDateTime createdAt
```

### Estados

```java
EnrollmentStatus:
- PENDING_PAYMENT
- ACTIVE
- CANCELLED
```

### Reglas de dominio

- `userId` no puede ser `null`.
- `courseId` no puede ser `null`.
- Una inscripción nueva inicia en `PENDING_PAYMENT`.
- Una inscripción solo puede activarse si está en `PENDING_PAYMENT`.

### Endpoint principal

```http
POST /enrollments
```

### Validaciones del caso de uso

Para crear una inscripción:

1. Validar que el usuario exista y esté activo mediante `IdentityModuleApi`.
2. Validar que el curso exista y esté activo mediante `CatalogModuleApi`.
3. Validar que el usuario no esté inscrito previamente en el curso.
4. Crear la inscripción en estado `PENDING_PAYMENT`.

### Persistencia

```text
enrollment.enrollments
```

### Constraint importante

```sql
CONSTRAINT uk_enrollment_user_course UNIQUE (user_id, course_id)
```

Evita que un usuario se inscriba dos veces al mismo curso.

### API interna

```java
EnrollmentModuleApi
```

Métodos principales:

```java
boolean existsPendingEnrollment(UUID enrollmentId);
void activateEnrollment(UUID enrollmentId);
```

Usado por `billing` para validar y activar inscripciones.

---

## 10. Módulo `billing`

Responsable de gestionar órdenes de pago.

### Entidad principal

```text
PaymentOrder
```

Campos principales:

```text
UUID id
UUID enrollmentId
BigDecimal amount
PaymentOrderStatus status
LocalDateTime createdAt
```

### Estados

```java
PaymentOrderStatus:
- PENDING
- PAID
- CANCELLED
```

### Reglas de dominio

- `enrollmentId` no puede ser `null`.
- `amount` debe ser mayor a cero.
- Una orden nueva inicia en `PENDING`.
- Una orden solo puede pagarse si está en estado `PENDING`.

### Endpoints principales

Crear orden de pago:

```http
POST /payment-orders
```

Pagar orden de pago:

```http
POST /payment-orders/{paymentOrderId}/pay
```

### Persistencia

```text
billing.payment_orders
```

### Flujo de creación de orden

1. `billing` recibe `enrollmentId` y `amount`.
2. Valida que la inscripción exista y esté pendiente usando `EnrollmentModuleApi.existsPendingEnrollment(...)`.
3. Valida que no exista una orden pendiente para esa inscripción.
4. Crea `PaymentOrder` en estado `PENDING`.
5. Persiste en `billing.payment_orders`.

### Flujo de pago

1. Busca la orden por `paymentOrderId`.
2. Ejecuta regla de dominio `paymentOrder.pay()`.
3. Guarda la orden con estado `PAID`.
4. Llama a `EnrollmentModuleApi.activateEnrollment(enrollmentId)`.
5. La inscripción pasa de `PENDING_PAYMENT` a `ACTIVE`.

---

## 11. Flujo completo del negocio

```text
1. Crear usuario
   POST /users
   Resultado: identity.users -> ACTIVE

2. Crear curso
   POST /courses
   Resultado: catalog.courses -> ACTIVE

3. Crear inscripción
   POST /enrollments
   Validaciones:
     - IdentityModuleApi.existsActiveUser(userId)
     - CatalogModuleApi.existsActiveCourse(courseId)
   Resultado: enrollment.enrollments -> PENDING_PAYMENT

4. Crear orden de pago
   POST /payment-orders
   Validación:
     - EnrollmentModuleApi.existsPendingEnrollment(enrollmentId)
   Resultado: billing.payment_orders -> PENDING

5. Pagar orden
   POST /payment-orders/{paymentOrderId}/pay
   Resultado:
     - billing.payment_orders -> PAID
     - enrollment.enrollments -> ACTIVE
```

---

## 12. Flujo de estados

### Antes del pago

```text
enrollment.status = PENDING_PAYMENT
payment_order.status = PENDING
```

### Después del pago exitoso

```text
enrollment.status = ACTIVE
payment_order.status = PAID
```

---

## 13. PostgreSQL schemas

Cada módulo es dueño de su propio schema.

```text
identity.users
catalog.courses
enrollment.enrollments
billing.payment_orders
```

### Decisión importante

No se usan foreign keys directas entre schemas de módulos.

La validación entre módulos se realiza mediante APIs internas:

```text
IdentityModuleApi
CatalogModuleApi
EnrollmentModuleApi
```

Esto mantiene los límites de cada módulo más claros.

---

## 14. Flyway

Las tablas se crean mediante migraciones Flyway.

Ejemplo de migraciones:

```text
V1__create_identity_schema_and_users_table.sql
V2__create_catalog_schema_and_courses_table.sql
V3__create_enrollment_schema_and_enrollments_table.sql
V4__create_billing_schema_and_payment_orders_table.sql
```

Hibernate está configurado con:

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate
```

Esto significa que Hibernate no crea tablas automáticamente. Solo valida que el modelo JPA coincida con la base de datos creada por Flyway.

---

## 15. Transaccionalidad

El flujo de pago modifica dos módulos:

```text
billing.payment_orders

enrollment.enrollments
```

Para mantener consistencia, el caso de uso de pago usa `@Transactional`.

```java
@Transactional
public PaymentOrderResponse execute(UUID paymentOrderId) {
    PaymentOrder paymentOrder = paymentOrderRepository.findById(paymentOrderId)
            .orElseThrow(() -> new PaymentOrderNotFoundException(paymentOrderId));

    paymentOrder.pay();

    PaymentOrder savedPaymentOrder = paymentOrderRepository.save(paymentOrder);

    enrollmentModuleApi.activateEnrollment(savedPaymentOrder.getEnrollmentId());

    return PaymentOrderResponse.fromDomain(savedPaymentOrder);
}
```

### Ruta feliz

```text
payment_order: PENDING -> PAID
enrollment: PENDING_PAYMENT -> ACTIVE
COMMIT
```

### Ruta con error

```text
payment_order intenta pasar a PAID
enrollment.activate() falla
ROLLBACK
payment_order vuelve a PENDING
```

Esto evita inconsistencias como:

```text
payment_order = PAID
enrollment = PENDING_PAYMENT
```

---

## 16. Patrones aplicados

### Factory Method estático

Usado en:

```text
User.create(...)
Course.create(...)
Enrollment.create(...)
PaymentOrder.create(...)
```

Permite crear objetos válidos centralizando reglas de construcción.

### Rehydrate

Usado para reconstruir entidades de dominio desde la base de datos:

```text
User.rehydrate(...)
Course.rehydrate(...)
Enrollment.rehydrate(...)
PaymentOrder.rehydrate(...)
```

Evita usar `create(...)` cuando el objeto ya existe.

### Repository Pattern

Puertos definidos en application:

```text
UserRepository
CourseRepository
EnrollmentRepository
PaymentOrderRepository
```

Application depende de interfaces, no de detalles técnicos.

### Adapter / Driven Adapter

Implementaciones técnicas:

```text
PostgresUserRepository
PostgresCourseRepository
PostgresEnrollmentRepository
PostgresPaymentOrderRepository
```

Adaptan los puertos de application a PostgreSQL/JPA.

### Mapper Pattern

Usado para convertir entre dominio y JPA:

```text
UserJpaMapper
CourseJpaMapper
EnrollmentJpaMapper
PaymentOrderJpaMapper
```

Evita contaminar el dominio con anotaciones JPA.

### DTO Pattern

Usado para separar los contratos HTTP del modelo interno:

```text
CreateUserRequest
CreateCourseRequest
CreateEnrollmentRequest
CreatePaymentOrderRequest
UserResponse
CourseResponse
EnrollmentResponse
PaymentOrderResponse
ApiResponse
```

### Facade / Module API

APIs internas para comunicación entre módulos:

```text
IdentityModuleApi
CatalogModuleApi
EnrollmentModuleApi
```

Permiten que un módulo exponga capacidades sin revelar detalles internos.

---

## 17. Principios SOLID aplicados

### S - Single Responsibility Principle

Cada clase tiene una responsabilidad clara:

```text
Controller -> HTTP
UseCase -> orquestación
Domain Model -> reglas de negocio
Repository Port -> contrato
Repository Impl -> persistencia
Mapper -> conversión
ExceptionHandler -> manejo de errores HTTP
```

### O - Open/Closed Principle

El sistema permite cambiar implementaciones sin modificar el caso de uso.

Ejemplo:

```text
InMemoryPaymentOrderRepository
PostgresPaymentOrderRepository
```

Ambos pueden implementar el mismo puerto.

### L - Liskov Substitution Principle

Las implementaciones concretas pueden reemplazar al contrato sin romper el comportamiento esperado.

Ejemplo:

```text
CourseRepository
  -> InMemoryCourseRepository
  -> PostgresCourseRepository
```

### I - Interface Segregation Principle

Las APIs internas son pequeñas y específicas.

Ejemplos:

```java
boolean existsActiveUser(UUID userId);
boolean existsActiveCourse(UUID courseId);
boolean existsPendingEnrollment(UUID enrollmentId);
void activateEnrollment(UUID enrollmentId);
```

### D - Dependency Inversion Principle

Los casos de uso dependen de abstracciones:

```text
CreateEnrollmentUseCase -> EnrollmentRepository, IdentityModuleApi, CatalogModuleApi
CreatePaymentOrderUseCase -> PaymentOrderRepository, EnrollmentModuleApi
PayPaymentOrderUseCase -> PaymentOrderRepository, EnrollmentModuleApi
```

No dependen directamente de JPA, PostgreSQL ni entidades de otros módulos.

---

## 18. Manejo de errores

Cada módulo tiene su propio `ExceptionHandler` en la capa `api`.

Ejemplo:

```text
PaymentOrderExceptionHandler
EnrollmentExceptionHandler
CatalogExceptionHandler
IdentityExceptionHandler
```

Los errores se devuelven usando `shared.ApiResponse`.

Ejemplo de error controlado:

```json
{
  "status": 404,
  "message": "No existe una orden de pago con id: ...",
  "data": [],
  "timestamp": "2026-05-07T00:00:00"
}
```

---

## 19. Decisiones arquitectónicas importantes

### Un schema por módulo

Cada módulo tiene control sobre sus propias tablas.

### Sin acceso directo a datos de otros módulos

Un módulo no usa repositorios ni entidades JPA de otro módulo.

### APIs internas para comunicación

La integración entre módulos se realiza por interfaces de aplicación.

### Dominio limpio

El dominio no usa:

```text
@Entity
@Repository
@Service
@RestController
JpaRepository
```

### Persistencia desacoplada

La infraestructura puede cambiar sin afectar application/domain.

### Transacción en el caso de uso de pago

El flujo de pago y activación de inscripción se ejecuta como una unidad transaccional.

---

## 20. Próximo objetivo: Kafka

El siguiente paso será evolucionar parte del flujo síncrono hacia eventos.

Actualmente:

```text
Billing -> EnrollmentModuleApi.activateEnrollment(...)
```

Futuro con Kafka:

```text
Billing publica PaymentApprovedEvent
Enrollment consume PaymentApprovedEvent
Enrollment activa la inscripción
```

Eventos posibles:

```text
EnrollmentCreatedEvent
PaymentOrderCreatedEvent
PaymentApprovedEvent
EnrollmentActivatedEvent
```

Esto permitirá aprender comunicación asíncrona, eventos de dominio/integración y desacoplamiento temporal entre módulos.

---

## 21. Resumen final

`online-courses` implementa un monolito modular funcional con límites claros por módulo, persistencia separada por schemas, comunicación unidireccional mediante APIs internas y reglas de negocio protegidas en el dominio.

El flujo completo permite crear usuarios, cursos, inscripciones, órdenes de pago y activar inscripciones cuando el pago es exitoso, manteniendo consistencia transaccional.

Este proyecto sirve como base sólida para evolucionar hacia arquitectura basada en eventos con Kafka.

