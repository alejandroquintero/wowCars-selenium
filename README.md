# Tabla de contenidos
-  [WowTeam](#wowTeam)
-  [Introducción](#introducción)
-  [API](#api-de-la-aplicación-concesionario)
  - [Recurso Brand](#recurso-brand)
    - [GET /brands](#GET-/brands)
    - [GET /brands/{id}](#GET-/brands/{id})
    - [POST /brands](#POST-/brands)
    - [PUT /brands/{id}](#PUT-/brands/{id})
    - [DELETE /brands/{id}](#DELETE-/brands/{id})
    - [GET brands/{brandsid}/car](#GET-brands/{brandsid}/car)
    - [GET brands/{brandsid}/car/{carid}](#GET-brands/{brandsid}/car/{carid})
    - [POST brands/{brandsid}/car/{carid}](#POST-brands/{brandsid}/car/{carid})
    - [PUT brands/{brandsid}/car](#PUT-brands/{brandsid}/car)
    - [DELETE brands/{brandsid}/car/{carid}](#DELETE-brands/{brandsid}/car/{carid}])
  - [Recurso Category](#recurso-category)
    - [GET /categorys](#GET-/categorys)
    - [GET /categorys/{id}](#GET-/categorys/{id})
    - [POST /categorys](#POST-/categorys)
    - [PUT /categorys/{id}](#PUT-/categorys/{id})
    - [DELETE /categorys/{id}](#DELETE-/categorys/{id})
  - [Recurso Client](#recurso-client)
    - [GET /clients](#GET-/clients)
    - [GET /clients/{id}](#GET-/clients/{id})
    - [POST /clients](#POST-/clients)
    - [PUT /clients/{id}](#PUT-/clients/{id})
    - [DELETE /clients/{id}](#DELETE-/clients/{id})

# WowTeam

Integrantes:

- Catherine Lizethe Santana (cl.santana@uniandes.edu.co)
- Fredy Gonzalo Captuayo Novoa (fg.captuayo@uniandes.edu.co)
- Julian Esteban Osorio Lopez (je.osoriol@uniandes.edu.co)
- Oscar Daniel Garcia Villamil (od.garcia@uniandes.edu.co)
- Armando Rey Ramos (a.reyr@uniandes.edu.co)

Reglas de Funcionamiento:

- Puntualidad y asistencia a las reuniones.
- Entregar en los tiempos establecidos.
- Atender y participar en las comunicaciones del equipo.
- Informar a la brevedad los inconvenientes o novedades que se presenten a cada integrante.

# API Rest
## Introducción
La comunicación entre cliente y servidor se realiza intercambiando objetos JSON. Para cada entidad se hace un mapeo a JSON, donde cada uno de sus atributos se transforma en una propiedad de un objeto JSON. Todos los servicios se generan en la URL /Concesionario.api/api/. Por defecto, todas las entidades tienen un atributo `id`, con el cual se identifica cada registro:

```javascript
{
    id: '',
    attribute_1: '',
    attribute_2: '',
    ...
    attribute_n: ''
}
```

Cuando se transmite información sobre un registro específico, se realiza enviando un objeto con la estructura mencionada en la sección anterior.
La única excepción se presenta al solicitar al servidor una lista de los registros en la base de datos, que incluye información adicional para manejar paginación de lado del servidor en el header `X-Total-Count` y los registros se envían en el cuerpo del mensaje como un arreglo.

La respuesta del servidor al solicitar una colección presenta el siguiente formato:

```javascript
[{}, {}, {}, {}, {}, {}]
```

## API de la aplicación Concesionario
### Recurso Brand
El objeto Brand tiene 2 representaciones JSON:	

#### Representación Minimum
```javascript
{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo String*/
}
```




#### GET /brands

Retorna una colección de objetos Brand en representación Detail.

#### Parámetros

#### N/A

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de [representaciones Detail](#recurso-brand)
409|Un objeto relacionado no existe|Mensaje de error
500|Error interno|Mensaje de error

#### GET /brands/{id}

Retorna una colección de objetos Brand en representación Detail.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Brand a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Brand en [representaciones Detail](#recurso-brand)
404|No existe un objeto Brand con el ID solicitado|Mensaje de error
500|Error interno|Mensaje de error

#### POST /brands

Es el encargado de crear objetos Brand.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
body|body|Objeto Brand que será creado|Sí|[Representación Detail](#recurso-brand)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Brand ha sido creado|[Representación Detail](#recurso-brand)
409|Un objeto relacionado no existe|Mensaje de error
500|No se pudo crear el objeto Brand|Mensaje de error

#### PUT /brands/{id}

Es el encargado de actualizar objetos Brand.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Brand a actualizar|Sí|Integer
body|body|Objeto Brand nuevo|Sí|[Representación Detail](#recurso-brand)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Brand actualizado|[Representación Detail](#recurso-brand)
412|business exception, no se cumple con las reglas de negocio|Mensaje de error
500|No se pudo actualizar el objeto Brand|Mensaje de error

#### DELETE /brands/{id}

Elimina un objeto Brand.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Brand a eliminar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
500|Error interno|Mensaje de error


#### GET brands/{brandsid}/car

Retorna una colección de objetos Car asociados a un objeto Brand en representación Detail.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Brand a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de objetos Car en [representación Detail](#recurso-car)
500|Error consultando car |Mensaje de error

#### GET brands/{brandsid}/car/{carid}

Retorna un objeto Car asociados a un objeto Brand en representación Detail.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
brandsid|Path|ID del objeto Brand a consultar|Sí|Integer
carid|Path|ID del objeto Car a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Car en [representación Detail](#recurso-car)
404|No existe un objeto Car con el ID solicitado asociado al objeto Brand indicado |Mensaje de error
500|Error interno|Mensaje de error

#### POST brands/{brandsid}/car/{carid}

Asocia un objeto Car a un objeto Brand.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
brandsid|PathParam|ID del objeto Brand al cual se asociará el objeto Car|Sí|Integer
carid|PathParam|ID del objeto Car a asociar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Objeto Car asociado|[Representación Detail de Car](#recurso-car)
500|No se pudo asociar el objeto Car|Mensaje de error

#### PUT brands/{brandsid}/car

Es el encargado de actualizar un objeto Car asociada a un objeto Brand.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
brandsid|Path|ID del objeto Brand cuya colección será remplazada|Sí|Integer
body|body|Colección de objetos Car|Sí|[Representación Detail](#recurso-car)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|Se actualizo el objeto|Objeto Car en [Representación Detail](#recurso-car)
500|No se pudo actualizar|Mensaje de error

#### DELETE brands/{brandsid}/car/{carid}

Remueve un objeto Car asociado a un objeto Brand.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
brandsid|Path|ID del objeto Brand asociado al objeto Car|Sí|Integer
carid|Path|ID del objeto Car a remover|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto removido|N/A
500|Error interno|Mensaje de error


[Volver arriba](#tabla-de-contenidos)
### Recurso Category
El objeto Category tiene 2 representaciones JSON:	

#### Representación Minimum
```javascript
{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo String*/
}
```

#### Representación Detail
```javascript
{
    // todo lo de la representación Minimum más los objetos Minimum con relación simple.
    parentCategory: {
    id: '' /*Tipo Long*/,
    name: '' /*Tipo String*/    }
}
```



#### GET /categorys

Retorna una colección de objetos Category en representación Detail.
Cada Category en la colección tiene embebidos los siguientes objetos: Category.

#### Parámetros

#### N/A

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de [representaciones Detail](#recurso-category)
409|Un objeto relacionado no existe|Mensaje de error
500|Error interno|Mensaje de error

#### GET /categorys/{id}

Retorna una colección de objetos Category en representación Detail.
Cada Category en la colección tiene los siguientes objetos: Category.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Category a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Category en [representaciones Detail](#recurso-category)
404|No existe un objeto Category con el ID solicitado|Mensaje de error
500|Error interno|Mensaje de error

#### POST /categorys

Es el encargado de crear objetos Category.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
body|body|Objeto Category que será creado|Sí|[Representación Detail](#recurso-category)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Category ha sido creado|[Representación Detail](#recurso-category)
409|Un objeto relacionado no existe|Mensaje de error
500|No se pudo crear el objeto Category|Mensaje de error

#### PUT /categorys/{id}

Es el encargado de actualizar objetos Category.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Category a actualizar|Sí|Integer
body|body|Objeto Category nuevo|Sí|[Representación Detail](#recurso-category)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Category actualizado|[Representación Detail](#recurso-category)
412|business exception, no se cumple con las reglas de negocio|Mensaje de error
500|No se pudo actualizar el objeto Category|Mensaje de error

#### DELETE /categorys/{id}

Elimina un objeto Category.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Category a eliminar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
500|Error interno|Mensaje de error



[Volver arriba](#tabla-de-contenidos)
### Recurso Client
El objeto Client tiene 2 representaciones JSON:	

#### Representación Minimum
```javascript
{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo String*/,
    email: '' /*Tipo String*/,
    phone: '' /*Tipo Long*/
}
```




#### GET /clients

Retorna una colección de objetos Client en representación Detail.

#### Parámetros

#### N/A

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Colección de [representaciones Detail](#recurso-client)
409|Un objeto relacionado no existe|Mensaje de error
500|Error interno|Mensaje de error

#### GET /clients/{id}

Retorna una colección de objetos Client en representación Detail.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Client a consultar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
200|OK|Objeto Client en [representaciones Detail](#recurso-client)
404|No existe un objeto Client con el ID solicitado|Mensaje de error
500|Error interno|Mensaje de error

#### POST /clients

Es el encargado de crear objetos Client.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
body|body|Objeto Client que será creado|Sí|[Representación Detail](#recurso-client)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Client ha sido creado|[Representación Detail](#recurso-client)
409|Un objeto relacionado no existe|Mensaje de error
500|No se pudo crear el objeto Client|Mensaje de error

#### PUT /clients/{id}

Es el encargado de actualizar objetos Client.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Client a actualizar|Sí|Integer
body|body|Objeto Client nuevo|Sí|[Representación Detail](#recurso-client)

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
201|El objeto Client actualizado|[Representación Detail](#recurso-client)
412|business exception, no se cumple con las reglas de negocio|Mensaje de error
500|No se pudo actualizar el objeto Client|Mensaje de error

#### DELETE /clients/{id}

Elimina un objeto Client.

#### Parámetros

Nombre|Ubicación|Descripción|Requerido|Esquema
:--|:--|:--|:--|:--
id|Path|ID del objeto Client a eliminar|Sí|Integer

#### Respuesta

Código|Descripción|Cuerpo
:--|:--|:--
204|Objeto eliminado|N/A
500|Error interno|Mensaje de error



[Volver arriba](#tabla-de-contenidos)
