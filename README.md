#Autoevaluación: 5

##Integrantes
Juan Camilo Pinzon Jaimes - Wilson Yair Forero Ferreira - Neiver Granados Quintero

# Sistema de Inventario

## Descripción

Sistema de Inventario es una aplicación web desarrollada con Spring Boot que permite gestionar productos, categorías, proveedores y usuarios. El sistema proporciona funcionalidades completas de administración de inventario con autenticación y autorización de usuarios.

## Características Principales

- ✅ **Gestión de Productos**: Crear, actualizar, eliminar y listar productos
- ✅ **Gestión de Categorías**: Administración de categorías de productos
- ✅ **Gestión de Proveedores**: Control de proveedores y sus datos
- ✅ **Gestión de Usuarios**: Administración de usuarios del sistema
- ✅ **Autenticación y Seguridad**: Sistema de login y roles de usuario
- ✅ **Interfaz Web**: Interfaz intuitiva con templates HTML/Thymeleaf

## Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **Java 11 o superior**
- **Maven 3.6 o superior**
- **Base de datos MySQL 8.0 o superior**
- **Git** (opcional)

## Instalación y Configuración

### 1. Clonar o descargar el proyecto
```bash
cd Java-Project-0/Sistema-Inventario
```

### 2. Configurar la base de datos

Ejecuta el script SQL para crear la base de datos:
```bash
mysql -u root -p < ../SQL/database.sql
```

### 3. Configurar las propiedades de la aplicación

Edita el archivo `src/main/resources/application.properties` con tus credenciales de base de datos:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nombre_base_datos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### 4. Instalar dependencias

```bash
mvn clean install
```

## Ejecución

### Usando Maven
```bash
mvn spring-boot:run
```

### Usando Java directamente
```bash
java -jar target/proyecto-corte3-1.0.jar
```

Una vez iniciada la aplicación, accede a:
```
http://localhost:8080
```

## Estructura del Proyecto

```
Sistema-Inventario/
├── src/
│   ├── main/
│   │   ├── java/uts/edu/java/proyecto/
│   │   │   ├── ProyectoCorte3Application.java      # Clase principal
│   │   │   ├── SecurityConfig.java                 # Configuración de seguridad
│   │   │   ├── controlador/                         # Controladores REST/MVC
│   │   │   │   ├── AppController.java
│   │   │   │   ├── CategoriaControlador.java
│   │   │   │   ├── ProductoControlador.java
│   │   │   │   ├── ProveedorControlador.java
│   │   │   │   └── UsuarioControlador.java
│   │   │   ├── modelo/                              # Entidades JPA
│   │   │   │   ├── Categoria.java
│   │   │   │   ├── Producto.java
│   │   │   │   ├── Proveedor.java
│   │   │   │   └── Usuario.java
│   │   │   ├── repositorio/                         # Repositorios Spring Data
│   │   │   │   ├── CategoriaRepositorio.java
│   │   │   │   ├── ProductoRepositorio.java
│   │   │   │   ├── ProveedorRepositorio.java
│   │   │   │   └── UsuarioRepositorio.java
│   │   │   └── servicio/                            # Servicios de lógica de negocio
│   │   │       ├── CategoriaServicio.java
│   │   │       ├── ProductoServicio.java
│   │   │       ├── ProveedorServicio.java
│   │   │       ├── UsuarioServicio.java
│   │   │       └── interfaces/ (I*.java)
│   │   └── resources/
│   │       ├── application.properties                # Configuración de la app
│   │       └── templates/                            # Templates Thymeleaf
│   │           ├── home.html
│   │           ├── login.html
│   │           └── views/                            # Vistas CRUD
│   │               ├── categorias/
│   │               ├── productos/
│   │               └── proveedor/
│   └── test/
│       └── java/uts/edu/java/proyecto/
│           └── ProyectoCorte3ApplicationTests.java
├── pom.xml                                          # Configuración Maven
├── mvnw                                             # Maven Wrapper
└── mvnw.cmd                                         # Maven Wrapper (Windows)
```

## Tecnologías Utilizadas

- **Spring Boot 2.x** - Framework principal
- **Spring Data JPA** - Acceso a datos
- **Spring Security** - Autenticación y autorización
- **MySQL** - Base de datos
- **Thymeleaf** - Motor de templates
- **Maven** - Gestor de dependencias
- **JUnit** - Testing

## Endpoints Principales

### Categorías
- `GET /categorias` - Listar todas las categorías
- `POST /categorias` - Crear nueva categoría
- `GET /categorias/{id}` - Obtener categoría por ID
- `PUT /categorias/{id}` - Actualizar categoría
- `DELETE /categorias/{id}` - Eliminar categoría

### Productos
- `GET /productos` - Listar todos los productos
- `POST /productos` - Crear nuevo producto
- `GET /productos/{id}` - Obtener producto por ID
- `PUT /productos/{id}` - Actualizar producto
- `DELETE /productos/{id}` - Eliminar producto

### Proveedores
- `GET /proveedores` - Listar todos los proveedores
- `POST /proveedores` - Crear nuevo proveedor
- `GET /proveedores/{id}` - Obtener proveedor por ID
- `PUT /proveedores/{id}` - Actualizar proveedor
- `DELETE /proveedores/{id}` - Eliminar proveedor

### Usuarios
- `GET /usuarios` - Listar todos los usuarios
- `POST /usuarios` - Crear nuevo usuario
- `GET /usuarios/{id}` - Obtener usuario por ID
- `PUT /usuarios/{id}` - Actualizar usuario
- `DELETE /usuarios/{id}` - Eliminar usuario

## Configuración de Seguridad

El proyecto utiliza Spring Security para proteger los endpoints. La configuración se encuentra en `SecurityConfig.java`. Asegúrate de:

1. Configurar usuarios y roles en la base de datos
2. Ajustar las reglas de autorización según tus necesidades
3. Usar HTTPS en producción

## Base de Datos

El proyecto incluye un script SQL (`../SQL/database.sql`) que crea automáticamente:
- Tabla de Usuarios
- Tabla de Categorías
- Tabla de Productos
- Tabla de Proveedores

## Troubleshooting

### Error de conexión a base de datos
- Verifica que MySQL está corriendo
- Confirma las credenciales en `application.properties`
- Asegúrate de que la base de datos existe

### Puerto 8080 en uso
- Cambiar el puerto en `application.properties`:
```properties
server.port=8081
```

### Problemas al compilar
```bash
mvn clean install -DskipTests
```

## Contribuir

Las contribuciones son bienvenidas. Por favor:
1. Fork el proyecto
2. Crea una rama para tu feature
3. Commit tus cambios
4. Push a la rama
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la licencia MIT. Ver el archivo LICENSE para más detalles.

## Autores

Desarrollado como parte del Corte 3 del proyecto académico.

## Soporte

Para reportar bugs o solicitar features, por favor abre un issue en el repositorio.

---

**Última actualización**: 2026
