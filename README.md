# Backend MVP para tiendas
Este proyecto consiste en un backend para un software de venta de productos. Ofrece una API REST, a consumir por un frontend, que gestiona la persistencia de clientes, productos y compras.
Se incluyen además archivos de configuración para poder desplegar fácilmente el backend utilizando *docker-compose*.
## API
La API se expone en la URL */venta*, con distintos enpoints para cada recurso (clientes, categorías, productos y compras). Todos estos endpoints soportan las operaciones básicas CRUD, así como una operación de búsqueda. Los distintos endpoints se detallan a continuación.
### Categorías
Para gestionar categorías de productos, una categoría es un objeto con los siguientes campos:
* **categoria_id**: Identificador único de la categoría.
* **nombre**: Nombre de la categoría.
* **descripcion**: Descripción de la categoría.

Este endpoint está expuesto en la URL */venta/categoria* soporta las siguientes operaciones:
* **GET /** Obtiene la lista de todas las categorías almacenadas.
* **POST /** Añade una categoría nueva.
* **PUT /{id}** Actualiza la categoría identificada por *id*.
* **DELETE /{id}** Elimina la categoría identificada por *id*.
* **POST /search** Busca categorías según sus campos. Para ello es necesario enviar un objeto de búsqueda con las condiciones. En el caso de campos de texto, soporta búsqueda literal y con el wildcard *%*. Un ejemplo de uso es el siguiente, que busca todas las categorías cuyo nombre empiece por "A-":
	~~~~
	POST /search
	{"nombre":"A-%"}
	~~~~

### Productos
Para gestionar productos, un producto es un objeto con los siguientes campos:
* **producto_id**: Identificador único del producto.
* **categoria**: Categoría a la que pertenece el producto.
* **nombre**: Nombre del producto.
* **precio**: Precio unitario del producto.
* **cantidad**: Cantidad del producto actualmente en stock.

Este endpoint está expuesto en la URL */venta/producto* soporta las mismas operaciones que el endpoint de categorías.
A destacar de la función de busqueda, para campos númericos o de fechas, soporta la búsqueda por rangos, con la sintaxis indicada en el siguiente ejemplo, que busca todos los productos en stock de la categoría "Comestibles" que cuesten menos de 2€.
~~~~
	POST /search
	{"categoria":{nombre:"Comestibles"}, "cantidad_from":1, "precio_to":2}
~~~~

### Clientes
Para gestionar clientes, un cliente es un objeto con los siguientes campos:
* **cliente_id**: Identificador único del cliente.
* **nombre**: Nombre del cliente.
* **apellidos**: Apellidos del cliente.
* **telefono**: Télefono del cliente.
* **fecha_nacimiento**: Fecha de nacimiento del cliente.
* **email**: Correo electrónico del cliente.

Este endpoint está expuesto en la URL */venta/cliente* y soporta operaciones análogas al resto de endpoints.

### Compras
Para gestionar compras, una compra es un objeto con los siguientes campos:
* **compra_id**: Identificador único compra.
* **cliente**: Cliente que ha realizado la compra.
* **fecha**: Fecha en la que se realizó la compra.
* **metodo_pago**: Método de pago utilizado.
* **estado**: Estado de la compra.
* **productos**: Lista de productos de la compra, con los siguientes campos:
	 * **producto**: Producto comprado.
	 * **cantidad**: Cantidad vendida del producto.
	 * **total**: Precio total de esta línea.

Este endpoint está expuesto en la URL */venta/compras* y soporta operaciones análogas al resto de endpoints. Como ejemplo de la función de búsqueda, se muestra el siguiente request, que busca las compras entregadas del cliente "Alex" en las que ha comprado el producto "Heura".

~~~~
	POST /search
	{"cliente":{nombre:"Alex"}, "estado":"Entregado", "producto":{"producto":{"nombre":"Heura"}}}
~~~~

Además, este endpoint contiene un "subendpoint" adicional para gestionar el campo **productos**, con las siguientes operaciones:
* **POST /{id}/producto/** Añade una línea nueva a la compra identificada por *id*.
* **PUT /{id}/producto/{pid}** Actualiza la línea de la compra *id* correspondiente al producto *pid*.
* **DELETE /{id}/producto/{pid}** Elimina la línea de la compra *id* correspondiente al producto *pid*.
