# Documentación Complementaria del Proyecto

## Consideraciones del SGBD MySQL

Durante el desarrollo del proyecto se trabajó utilizando el motor de base de datos **MySQL**.  
Debido a las características propias de este SGBD, algunos objetos mencionados en los lineamientos del proyecto presentan diferencias o limitaciones respecto a otros motores como Oracle o PostgreSQL.

### Objetos soportados correctamente

El sistema desarrollado permite visualizar y administrar los siguientes objetos:

- Tablas
- Vistas
- Procedimientos almacenados
- Funciones
- Triggers
- Índices
- Usuarios

---

## Limitaciones encontradas en MySQL

### Tablespaces

En MySQL el manejo de tablespaces es diferente comparado con otros motores de base de datos.

Aunque MySQL posee soporte interno para tablespaces mediante InnoDB, su administración no es tan accesible ni estándar para proyectos académicos de administración visual como este. Además:

- No existe una metadata simple y uniforme para administrarlos visualmente.
- Muchas configuraciones dependen directamente del motor InnoDB.
- Su uso suele ser administrativo y de bajo nivel.
- No se manejan como objetos independientes visibles para el usuario común.

Por esta razón, el proyecto documenta esta limitación y no implementa administración visual de tablespaces.

---

### Secuencias / Generadores

MySQL no utiliza secuencias independientes como PostgreSQL u Oracle.

En MySQL normalmente se utiliza:

```sql
AUTO_INCREMENT
```

Por ello:

- No existen objetos de secuencia independientes.
- El sistema utiliza columnas `AUTO_INCREMENT` para simular este comportamiento.

---

## Uso de System Tables

Uno de los requisitos principales del proyecto fue el uso explícito de metadata mediante system tables.

Para cumplir con este requisito se utilizaron consultas como:

```sql
SHOW FULL TABLES
SHOW PROCEDURE STATUS
SHOW FUNCTION STATUS
SHOW TRIGGERS
SHOW INDEX
SHOW CREATE TABLE
SHOW CREATE VIEW
```

Además, se evitó completamente el uso de:

```sql
information_schema
```

cumpliendo así con las restricciones establecidas en el proyecto.

---

# Desafíos Encontrados y Soluciones

## 1. Manejo dinámico de metadata

### Problema

Inicialmente resultó complejo obtener información dinámica sobre los objetos de la base de datos sin utilizar `information_schema`.

### Solución

Se investigó el uso de comandos propios de MySQL como:

```sql
SHOW TABLES
SHOW CREATE TABLE
SHOW TRIGGERS
SHOW INDEX
```

permitiendo obtener metadata directamente desde el motor.

---

## 2. Generación de DDL de vistas

### Problema

El DDL de las vistas aparecía en una sola línea, dificultando su lectura.

### Solución

Se aplicó formateo manual al SQL generado mediante reemplazos de texto para mejorar la legibilidad del ddl.

---

# Aprendizajes Clave

Durante el desarrollo de este proyecto se fortalecieron conocimientos importantes relacionados con:

- JDBC y conexiones MySQL
- manejo de metadata
- administración de objetos de base de datos
- generación dinámica de SQL
- arquitectura de aplicaciones Desktop en Java Swing
- uso de system tables
- diseño de interfaces gráficas
- manejo de múltiples conexiones
- organización modular del código
- DDL y DML
- triggers, funciones y procedimientos almacenados

---

# Reflexión Personal

El desarrollo de este proyecto permitió comprender de forma práctica cómo interactúa una aplicación con un sistema gestor de bases de datos a nivel administrativo.

Uno de los aspectos más importantes fue aprender a trabajar directamente con metadata del motor MySQL, evitando dependencias externas y comprendiendo cómo los administradores de bases de datos obtienen información de los objetos del sistema.

También se aprendió la importancia de:

- diseñar interfaces claras
- organizar correctamente el código
- manejar errores
- validar sentencias SQL
- crear herramientas reutilizables
- trabajar con JDBC y metadata
- generar DDL dinámicamente
- administrar múltiples conexiones
- estructurar aplicaciones Desktop en Java Swing

El proyecto representó un reto tanto a nivel técnico como visual, especialmente al trabajar con componentes dinámicos y manejo manual de interfaces gráficas.

Para el desarrollo visual del sistema se utilizó apoyo de Inteligencia Artificial en el diseño de la interfaz y organización de componentes Swing, también para elaborar el README.

Finalmente, este proyecto permitió adquirir una visión más completa sobre la administración de bases de datos y el funcionamiento interno de herramientas administrativas similares a gestores profesionales.