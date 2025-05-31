# TallerApsDistribuidasT01
# ☕ Coffee & Code – Sistema de Gestión de Pedidos (Microservicios)

Este proyecto implementa una arquitectura de microservicios para la gestión de pedidos de una cafetería en línea. Incluye servicios independientes para productos, clientes, pedidos y pagos, junto con un API Gateway y Eureka Service Discovery.

---

## 📦 Microservicios

- `producto` → Gestión del catálogo de productos.  
- `cliente` → Registro y autenticación de clientes.  
- `pedido` → Creación y seguimiento de pedidos.  
- `pago` → Procesamiento simulado de pagos.  
- `eurekaserver` → Descubrimiento de servicios (Eureka).  
- `apigateway` → Punto de entrada único (Spring Cloud Gateway).  

---

## 🧱 Tecnologías

- Java 17  
- Spring Boot 3.x  
- Spring Cloud Netflix Eureka  
- Spring Cloud Gateway  
- Spring Data JPA  
- PostgreSQL  
- Docker y Docker Compose  

---

## 🚀 ¿Cómo ejecutar el sistema?

### 1. Clonar el repositorio

```bash
git clone https://github.com/MiguelVaDu/TallerApsDistribuidasT01.git
cd TallerApsDistribuidasT01