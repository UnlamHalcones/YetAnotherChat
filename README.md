# YetAnotherChat


**Objetivo**
• Crear una aplicación Cliente-Servidor de Salas de Chat.

**Alcance**
• La implementación debe ser realizada con Swing.
• El Servidor concentra toda las operaciones de la aplicación.
• No hay persistencia de datos tanto de usuarios como de las salas creadas.


**Requisitos Funcionales**

1. Como usuario quiero ingresar al lobby, donde hay muchas salas de chat disponibles
2. Como usuario debo poder crear salas de chat, estás serán públicas. Deberán poseer un nombre.
3. Como usuario debo poder ver las salas existentes y la cantidad de usuarios conectados en cada una.
4. Como usuario dentro de la sala puedo ver mi tiempo y el de los usuarios conectados a la misma.
5. Los usuarios pueden participar en más de una sala al mismo tiempo (con la condición de que solo se podrá estar conectado en hasta tres salas).
6. Como usuario puedo chatear en forma privada con un usuario de la sala a la que estoy conectado por vez.
7. Como usuario puedo descargar el historial de chat a la que estoy conectado.
8. Cada mensaje de chat debe poder identificarse con el usuario que lo escribió, la fecha y hora que lo envió

# Aplicación

La aplicación consta de 2 partes, el servidor y los clientes. Tanto en el server como en el cliente tenemos Threads escuchando las comunicaciones. En el Server tenemos un thread por cada usuario conectado, mientras que en el cliente tenemos un solo thread escuchando al servidor

#### Servidor

Es el que se encargar de tener toda la información y asegurar la comunicación entre los usuarios. Para iniciar el servidor se debe inicar el paquete ar.edu.unlam.servidor.graphics, que internamente tiene una clase llamada VentanaServer que al iniciar nos permite establecer en que puerto queremos que corra. Esto nos da la posibilidad tener varios servidores al mismo tiempo. 

#### Cliente 
Para iniciar el cliente hay que iniciar el paquete ar.edu.unlam.cliente.ventanas, que internamente tiene la clase VentanaIngresoCliente. Al ejecutarlo, nos va a pedir Nombre de usuario, IP y puerto del servidor al cual nos queremos conectar. Esto va a ir a buscar el servidor en la direccion indicada, y desde el server va a validar que el usuario no esté repetido. 

Una vez autenticado va a mostrar todas las salas que hay abiertas. Aqui se pueden crear nuevas salas para chatear o unirse a salas creadas por otros usuarios.

El cliente tiene un Singleton para escuchar las llamadas del servidor cada vez que otro usuario crea una sala, se une a una sala de la que formo parte o envía un mensaje. Ese singleton se encarga de actualizar las ventanas que el usuario tenga abiertas

Dentro de la ventana de chat se puede seleccionar envíar mensajes a todos los integrantes de la sala, o mandar un mensaje privado a todos los integrantes de la sala

#### Comunicación

Para comunicarse enetre el servidor y el cliente, creamos unas clase llamada Command. Un command está formada por 2 partes, CommandType y un Object. Cada vez que uno entre el server y el cliente necesitan comunicarse se manda un Command de que es lo que quiere hacer (CommandType) y la informacion con lo que tiene que hacer (sería el Object info). 

Por ejemplo, cuando desde el cliente pedimos que queremos crear una sala, mandamos un Command al servidor con CommandType CREAR_SALA y el Object info sería una instancia de la SalaChat a crear.

Luego el servidor va a crear la sala (luego de hacer las validaciones), responder que se creó la sala, y va a enviar un nuevo Command a todos los clientes conectados con el CommandType INFO_SALA y un Object info con todas las Salas de chat que tiene el server

## Mejoras Pendientes
Nos quedaron mejoras a futuro a hacer, que por temas de tiempo no pudimos

- No tenemos test realizados en la aplicación
- No pusimos iconos en las ventanas y varias cuestiones de diseño de los formularios
- Al cerrar la ventana del cliente no se esta desconectando el hilo del server para es cliente. De todos modos el server sigue funcionando 
- Al mismo tiempo, desde el cliente no estamos contemplandosi se cae el server o si no está conectado al momento de que se conecta

## Autores
* Nicolas Carlos Cailotto
* Domingo Miceli
* Francisco Pretto
* Martín Pfeiffer
* Tomas Della Maddalena
* Héctor Marcelo Gramajo
* Federico Martin Medina
* Sergio Salas
