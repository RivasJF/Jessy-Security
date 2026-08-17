# JessySecurity

## Descripción General

**JessySecurity** es una aplicación backend segura de gestión de contraseñas y cuentas, construida con Spring Boot 4.0.6. Funciona como complemento para <b>APIs Zero-Knowledge</b>, permitiendo almacenar y administrar credenciales de múltiples servicios de manera segura.

### Información del Proyecto
- **Nombre:** JessySecurity (Expense Manager)
- **Versión:** 0.1
- **Java:** 21
- **Spring Boot:** 4.0.6
- **Base de Datos:** PostgreSQL / H2 (desarrollo)
- **Lenguajes:** Java + Kotlin

## Características

### 1. **Autenticación y Autorización**
- Sistema de login/registro con JWT
- Tokens de acceso con expiración
- Tokens de refresco en cookies HTTP-only
- Salt público por usuario para hashing de contraseñas en cliente
- Validación de email

### 2. **Gestión de Cuentas**
- CRUD completo de cuentas (Create, Read, Update, Delete)
- Categorización de cuentas en 9 tipos diferentes
- Almacenamiento de información adicional (contraseñas, PINs, códigos, URLs, notas)
- Soporte para múltiples tipos de información por cuenta

### 3. **Seguridad**
- Hashing de contraseñas con salt público
- JWT para autenticación sin estado
- Validación de correos electrónicos
- Protección CSRF
- Cookies HTTP-only para tokens de refresco
- Uso de BouncyCastle para operaciones criptográficas

### 4. **Persistencia de Datos**
- JPA/Hibernate como ORM
- Flyway para versionamiento de base de datos
- Migraciones automáticas

## Requisitos Previos

### Sistema
- **Java:** 21 o superior
- **Maven:** 3.8.1 o superior
- **PostgreSQL:** 12+ (producción) o H2 (desarrollo)
- **Docker:** Para ejecutar con Docker (opcional)

### Flujo de Autenticación

```
┌─────────────┐
│   Cliente   │
└──────┬──────┘
       │
       │ 1. POST /register
       │    (username, email, publicKey, publicSalt)
       ▼
┌─────────────────────────────┐
│  Servidor                   │
│  - Valida email             │
│  - Almacena salt público    │
└──────┬──────────────────────┘
       │
       │ 2. GET /salt?email=...
       │ (Cliente obtiene salt)
       ▼
┌─────────────────────────────┐
│  Cliente                    │
│  - Hash(password + salt)    │
└──────┬──────────────────────┘
       │
       │ 3. POST /login
       │    (username, hashedPassword)
       ▼
┌─────────────────────────────┐
│  Servidor                   │
│  - Valida hash              │
│  - Genera JWT               │
│  - Genera refresh token     │
└──────┬──────────────────────┘
       │
       │ 4. Response
       │    access_token (JWT)
       │    refresh_token (cookie HTTP-only)
       ▼
┌─────────────────────────────┐
│  Cliente                    │
│  - Almacena JWT en memoria  │
│  - Usa para requests        │
└─────────────────────────────┘
```