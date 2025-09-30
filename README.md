Proyecto: CineMagenta
Descripción: Aplicación de escritorio en Java Swing para registrar, buscar, editar y eliminar películas en una base de datos MySQL.

Requisitos:
-----------
✔ Java 8 o superior
✔ NetBeans IDE (recomendado)
✔ MySQL o MariaDB
✔ MySQL Connector/J (driver JDBC)

Estructura del Proyecto:
-------------------------
El proyecto sigue el patrón MVC (Modelo-Vista-Controlador):

- com.cinemagenta.model         → Clases de dominio y conexión DB
- com.cinemagenta.controller    → Lógica de negocio
- com.cinemagenta.view          → Formularios Swing
- com.cinemagenta.utils         → Utilidades como validaciones
- com.cinemagenta.Main.java     → Punto de entrada

Pasos para ejecutar desde 0:
-----------------------------

1. Clonar o descomprimir el proyecto.

2. Abrir el proyecto en NetBeans:
   - `Archivo > Abrir Proyecto > Seleccionar la carpeta del proyecto`

3. Importar el driver JDBC de MySQL:
   - Clic derecho en el proyecto → `Propiedades > Libraries > Compile > Add JAR/Folder`
   - Selecciona el archivo `mysql-connector-java-8.0.xx.jar`

4. Crear la base de datos:
   - Abrir tu cliente de base de datos (ej: DBeaver, Workbench o consola)
   - Ejecutar el archivo `cine_db_script.sql` en **dos partes**:
     a) Crear la base de datos:
     ```sql
     CREATE DATABASE IF NOT EXISTS Cine_DB;
     USE Cine_DB;
     ```
     b) Crear la tabla:
     ```sql
     CREATE TABLE movie_catalog (
         id INT AUTO_INCREMENT PRIMARY KEY,
         title VARCHAR(150) NOT NULL,
         director VARCHAR(50) NOT NULL,
         release_year INT NOT NULL,
         duration_minutes INT NOT NULL,
         genre VARCHAR(50) NOT NULL
     );
     ```

5. Verificar credenciales de conexión:
   - Por defecto, el proyecto usa:
     - Usuario: `PRY2203_EXP3`
     - Contraseña: `PRY2203`
     - Base de datos: `Cine_DB`
   - Si cambias estas credenciales, actualiza el archivo:
     `com.cinemagenta.model.DatabaseConnection.java`

6. Ejecutar el programa:
   - Ejecutar la clase `Main.java`
   - Se abrirá la ventana principal con el menú
   - Desde allí se puede:
     - Agregar películas
     - Buscar, editar o eliminar películas
