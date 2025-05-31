# TallerApsDistribuidasT01
# ☕ Coffee & Code – Sistema de Gestión de Pedidos (Microservicios)

Este proyecto implementa una arquitectura de microservicios para la gestión de pedidos de una cafetería en línea. Incluye servicios independientes para productos, clientes, pedidos y pagos, junto con un API Gateway y Eureka Service Discovery.

---

## 📦 Microservicios

- `Product` → Gestión del catálogo de productos.  
- `Customer` → Registro y autenticación de clientes.  
- `Order` → Creación y seguimiento de pedidos.  
- `Payment` → Procesamiento simulado de pagos.  

### 📦 Adicional
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

## 🧱 Arquitectura

La arquitectura del sistema sigue el patrón de microservicios, en donde cada servicio está desacoplado y cumple una función específica dentro de la aplicación. Los componentes principales son:

- **Eureka Server**: Actúa como un registro de servicios. Permite que los microservicios se descubran y se comuniquen entre sí sin conocer sus direcciones físicas.
- **API Gateway**: Sirve como punto de entrada único para todas las peticiones del cliente. Se encarga de enrutar las solicitudes al microservicio correspondiente.
- **Microservicios**:
  - **Customer**: Maneja el registro, autenticación y consulta de clientes.
  - **Product**: Gestiona el catálogo de productos disponibles.
  - **Order**: Crea y consulta pedidos realizados por los clientes.
  - **Payment**: Simula el procesamiento de pagos.

### 🔗 Flujo de Comunicación

```text
[Cliente Frontend]
       |
       v
[ API Gateway ] --> [ Servicio Cliente ]
       |            --> [ Servicio Producto ]
       |            --> [ Servicio Pedido ]
       |            --> [ Servicio Pago ]
       |
       v
[ Eureka Server (registro de servicios) ]

---

## 🚀 ¿Cómo ejecutar el sistema?

### 1. Clonar el repositorio

```bash
git clone https://github.com/MiguelVaDu/TallerApsDistribuidasT01.git
cd TallerApsDistribuidasT01

---

## 🧪 Pruebas con Insomnia

Para facilitar las pruebas de los microservicios, se ha creado una colección de **Insomnia** que contiene todos los endpoints necesarios para interactuar con el sistema a través del **API Gateway**.

### 📁 Archivo de colección

Puedes importar la colección desde el archivo `Insomnia_Collection.json` incluido en este repositorio.

### 🚀 ¿Cómo usar la colección?

1. Abre **Insomnia**.
2. Ve a `Application` > `Import/Export` > `Import Data`.
3. Selecciona `From File` y elige el archivo `Insomnia_Collection.json`.
4. Asegúrate de que los microservicios estén en ejecución.
5. Realiza las peticiones desde la colección, agrupadas por servicio:
   - `Producto`
   - `Cliente`
   - `Pedido`
   - `Pago`

### 📌 Consideraciones

- Todos los endpoints están configurados para usar `http://localhost:8080` como base URL del **API Gateway**.
- Si cambias el puerto o nombre del host, actualiza el entorno (`Environment`) en Insomnia.
- Algunos endpoints requieren datos en formato JSON (por ejemplo, para crear pedidos o productos). Asegúrate de enviar el `Content-Type: application/json`.

---

Ejemplo de cuerpo para crear un producto:

```json
{
  "nombre": "Latte",
  "descripcion": "Café con leche",
  "precio": 9.5
}