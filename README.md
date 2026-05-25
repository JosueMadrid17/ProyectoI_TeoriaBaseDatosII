# Database Manager Tool

Herramienta administrativa de bases de datos desarrollada en **Java + MySQL**, orientada a la administración de objetos, exploración de metadata y ejecución de sentencias SQL utilizando información interna del motor MySQL.

Proyecto realizado para la clase **Teoría de Base de Datos II**.

---

# Descripción General

El sistema permite conectarse a múltiples bases de datos MySQL mediante autenticación dinámica y administrar distintos objetos del sistema utilizando metadata propia del motor.

La aplicación fue desarrollada como una herramienta Desktop.

El proyecto implementa generación dinámica de DDL, exploración de objetos y ejecución de SQL sin utilizar frameworks ORM u otros ni `information_schema`.

---

# Características Principales

## Gestión de conexiones

- Inicio de sesión con cualquier usuario MySQL válido
- Manejo de múltiples conexiones guardadas
- Selección de conexiones almacenadas
- Cierre de sesión
- Eliminación de conexión actual
- Administración de distintas bases de datos MySQL

---

## Explorador de Objetos

El sistema permite visualizar metadata y administrar:

- Tablas
- Vistas
- Procedimientos almacenados
- Funciones
- Triggers
- Índices
- Usuarios

---

## Editor SQL

El editor SQL permite:

- Ejecutar consultas `SELECT`
- Ejecutar sentencias DDL
- Ejecutar sentencias DML
- Ejecutar scripts SQL completos
- Visualizar resultados en tablas dinámicas
- Limpiar editor SQL
- Generar DDL desde metadata

---

## Generación de DDL

El sistema puede generar automáticamente:

- `CREATE TABLE`
- `CREATE VIEW`
- `CREATE PROCEDURE`
- `CREATE FUNCTION`
- `CREATE TRIGGER`

utilizando metadata interna de MySQL.

---

## Creación Visual de Objetos

El sistema incluye interfaz visual para:

- Crear tablas
- Crear vistas

mediante formularios gráficos integrados en pestañas (`JTabbedPane`).

---

# Tecnologías Utilizadas

- Java
- Java Swing
- JDBC
- MySQL
- SQL

---

# Arquitectura del Proyecto

```text id="q7f81r"
src/
│
├── conexion/
│   └── Manejo de conexiones JDBC
│
├── modelos/
│   └── Objetos de conexión y entidades auxiliares
│
├── servicios/
│   └── Lógica SQL, metadata y generación DDL
│
├── ui/
│   └── Interfaces gráficas Swing
│
└── principal/
    └── Punto de inicio de la aplicación
```

---

# Funcionalidades Implementadas

## Metadata y System Tables

El proyecto utiliza metadata del motor MySQL mediante comandos como:

```sql id="a8r4v2"
SHOW FULL TABLES
SHOW CREATE TABLE
SHOW CREATE VIEW
SHOW PROCEDURE STATUS
SHOW FUNCTION STATUS
SHOW TRIGGERS
SHOW INDEX
```

---

## Operaciones Soportadas

### DDL

- CREATE
- ALTER
- DROP

### DML

- SELECT
- INSERT
- UPDATE
- DELETE

### Procedimientos

- CALL

---

# Interfaz Gráfica

La aplicación fue desarrollada completamente en Java Swing utilizando:

- `JFrame`
- `JPanel`
- `JTree`
- `JTable`
- `JTextArea`
- `JTabbedPane`
- `JScrollPane`

La interfaz incluye:

- Panel explorador de objetos
- Editor SQL dinámico
- Tabla de resultados
- Pestañas organizadas
- Gestión visual de conexiones

---

# Consideraciones Técnicas

## Restricciones Cumplidas

- Proyecto Desktop
- Uso explícito de metadata MySQL
- No uso de `information_schema`
- No uso de frameworks ORM, etc
- Manejo manual de JDBC
- Generación dinámica de SQL

---

## Limitaciones del SGBD

### Tablespaces

MySQL posee un manejo limitado y diferente de tablespaces comparado con otros motores como Oracle.

Debido a esto:

- no se implementó administración de tablespaces,
- ya que su manejo depende principalmente del motor InnoDB y configuraciones internas del servidor.

---

### Packages

MySQL no posee soporte para `packages` como Oracle.

---

### Secuencias

MySQL utiliza `AUTO_INCREMENT` en lugar de secuencias independientes.

---

# Instalación

## Requisitos

- Java JDK 22
- MySQL Server 8
- MySQL Connector/J

---

# Objetivo Académico

El objetivo principal del proyecto fue comprender:

- el manejo de metadata,
- la administración de objetos SQL,
- el uso de JDBC,
- la generación dinámica de DDL,
- y la construcción de herramientas administrativas de bases de datos.

---

# Autor

**Rigoberto Madrid**  
Estudiante de Ingeniería en Sistemas  
UNITEC — Teoría de Base de Datos II
