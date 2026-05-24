# Database Manager Tool

Herramienta administrativa de bases de datos desarrollada en **Java + MySQL**, orientada a la administración de objetos y ejecución de sentencias SQL utilizando metadata del motor MySQL.

Proyecto realizado para la clase **Teoría de Base de Datos II**.

---

# Descripción

El sistema permite conectarse a distintas bases de datos MySQL y administrar objetos del sistema mediante consultas SQL y metadata interna del SGBD.

La aplicación incluye:

- Gestión de conexiones
- Explorador de objetos
- Generación de DDL
- Editor SQL
- Administración básica de objetos
- Ejecución de scripts SQL

---

# Tecnologías Utilizadas

- Java
- JDBC
- MySQL
- SQL
- Swing

---

# Funcionalidades

## Gestión de conexiones

- Inicio de sesión con usuarios MySQL
- Manejo de múltiples conexiones
- Administración de sesiones

## Administración de objetos

- Tablas
- Vistas
- Procedimientos almacenados
- Funciones
- Triggers
- Índices
- Usuarios
- Tablespaces

## Operaciones soportadas

- Crear tablas y vistas
- Visualizar metadata
- Generar scripts DDL
- Modificar objetos mediante SQL
- Ejecutar scripts SQL

## Editor SQL

- Ejecución de consultas SELECT
- Ejecución de DDL
- Ejecución de DML
- Visualización de resultados

---

# Arquitectura del Proyecto

```text
src/
│
├── conexion/
├── ui/
├── servicios/
└── principal/
```

---

# Instalación

## Requisitos

- Java JDK 22
- MySQL Server 8.0.45
- MySQL Connector/J

---

# Autor

**Rigoberto Madrid**  
Teoría de Base de Datos II — UNITEC

