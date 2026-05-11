-- =============================================
-- SISTEMA DE INVENTARIO Y VENTAS - BASE DE DATOS
-- =============================================

CREATE DATABASE IF NOT EXISTS inventario_tienda 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_general_ci;

USE inventario_tienda;

-- =============================================
-- TABLAS
-- =============================================

-- 1. Usuarios
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100),
    rol ENUM('ADMIN', 'VENDEDOR') NOT NULL DEFAULT 'VENDEDOR',
    email VARCHAR(100),
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 2. Categorías
CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 3. Proveedores
CREATE TABLE proveedores (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    contacto VARCHAR(100),
    telefono VARCHAR(20),
    email VARCHAR(100),
    direccion TEXT,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 4. Productos
CREATE TABLE productos (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio_compra DECIMAL(10,2) NOT NULL,
    precio_venta DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    stock_minimo INT NOT NULL DEFAULT 5,
    id_categoria INT,
    id_proveedor INT,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria) ON DELETE SET NULL,
    FOREIGN KEY (id_proveedor) REFERENCES proveedores(id_proveedor) ON DELETE SET NULL
);

-- 5. Ventas
CREATE TABLE ventas (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    fecha_venta DATETIME DEFAULT CURRENT_TIMESTAMP,
    subtotal DECIMAL(10,2) NOT NULL,
    impuesto DECIMAL(10,2) DEFAULT 0.00,
    total DECIMAL(10,2) NOT NULL,
    estado ENUM('COMPLETADA', 'ANULADA') DEFAULT 'COMPLETADA',
    
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- 6. Detalle de Ventas
CREATE TABLE detalle_ventas (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    
    FOREIGN KEY (id_venta) REFERENCES ventas(id_venta) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);

-- =============================================
-- ÍNDICES PARA MEJORAR EL RENDIMIENTO
-- =============================================
CREATE INDEX idx_producto_codigo ON productos(codigo);
CREATE INDEX idx_producto_nombre ON productos(nombre);
CREATE INDEX idx_venta_fecha ON ventas(fecha_venta);
CREATE INDEX idx_venta_usuario ON ventas(id_usuario);

-- =============================================
-- TRIGGER: Actualizar stock automáticamente al vender
-- =============================================
DELIMITER //

CREATE TRIGGER actualizar_stock_venta
AFTER INSERT ON detalle_ventas
FOR EACH ROW
BEGIN
    UPDATE productos 
    SET stock = stock - NEW.cantidad 
    WHERE id_producto = NEW.id_producto;
END //

DELIMITER ;

-- =============================================
-- DATOS DE EJEMPLO (INSERTS)
-- =============================================

-- Usuarios
INSERT INTO usuarios (username, password, nombre, apellido, rol) VALUES
('admin', '$2a$10$8KzQvX9zL5mN7pR2tYvWq', 'Administrador', 'Sistema', 'ADMIN'),
('vendedor1', '$2a$10$8KzQvX9zL5mN7pR2tYvWq', 'Juan', 'Pérez', 'VENDEDOR'),
('vendedor2', '$2a$10$8KzQvX9zL5mN7pR2tYvWq', 'María', 'López', 'VENDEDOR');

-- Categorías
INSERT INTO categorias (nombre, descripcion) VALUES
('Electrónicos', 'Teléfonos, laptops, accesorios'),
('Ropa', 'Prendas de vestir para hombre y mujer'),
('Calzado', 'Zapatos y sandalias'),
('Alimentos', 'Productos comestibles y bebidas'),
('Hogar', 'Artículos para el hogar');

-- Proveedores
INSERT INTO proveedores (nombre, contacto, telefono, email, direccion) VALUES
('Tech Distributors', 'Carlos Ruiz', '555-1234', 'contacto@techdist.com', 'Av. Principal 123'),
('Moda Express', 'Ana Torres', '555-5678', 'ventas@modaexpress.com', 'Calle Comercio 456'),
('Alimentos SA', 'Luis Gómez', '555-9876', 'info@alimentossa.com', 'Zona Industrial');

-- Productos
INSERT INTO productos (codigo, nombre, descripcion, precio_compra, precio_venta, stock, stock_minimo, id_categoria, id_proveedor) VALUES
('P001', 'Laptop HP 15"', 'Laptop 8GB RAM 512GB SSD', 450.00, 650.00, 18, 5, 1, 1),
('P002', 'Mouse Inalámbrico Logitech', 'Mouse inalámbrico negro', 12.00, 25.00, 45, 10, 1, 1),
('P003', 'Camiseta Básica Algodón', 'Tallas S, M, L', 8.00, 18.00, 120, 20, 2, 2),
('P004', 'Zapatillas Deportivas', 'Color negro, talla 40-44', 25.00, 45.00, 35, 8, 3, 2),
('P005', 'Arroz 5kg', 'Arroz blanco premium', 4.50, 7.50, 80, 15, 4, 3);

-- Ventas de Ejemplo
INSERT INTO ventas (id_usuario, subtotal, impuesto, total) VALUES
(2, 695.00, 0.00, 695.00);

INSERT INTO detalle_ventas (id_venta, id_producto, cantidad, precio_unitario, subtotal) VALUES
(1, 1, 1, 650.00, 650.00),
(1, 3, 2, 18.00, 36.00),
(1, 5, 1, 7.50, 7.50);

-- =============================================
-- VISTA ÚTIL: Productos con bajo stock
-- =============================================
CREATE VIEW vista_bajo_stock AS
SELECT 
    codigo, nombre, stock, stock_minimo,
    (stock_minimo - stock) AS cantidad_faltante
FROM productos 
WHERE stock < stock_minimo;

-- =============================================
-- CONSULTAS DE PRUEBA
-- =============================================
-- SELECT * FROM productos;
-- SELECT * FROM vista_bajo_stock;