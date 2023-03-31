package com.example.myapplication

/*Creamos la clase usuario */
class Usuario {
    /*Creamos las variables, les asignamos el tipo y las inicializamos*/
    var cedula: String = ""
    var nombre: String = ""
    var apellido: String = ""
    var telefono: String = ""
    var edad: String = ""

    /*Se crea el constructor (método que se ejecuta de manera automática al instanciar un objeto de una clase)
    * y el constructor recibe los parametros con el tipo de parametro*/

    constructor(cedula: String, nombre: String, apellido: String, telefono: String, edad: String) {
        /*El this hace referencia a la clase usuario. La cedula del usuario va a ser igual a la cedula que
        * se le pase al contructor y asi sucesivamente*/
        this.cedula = cedula
        this.nombre = nombre
        this.apellido = apellido
        this.telefono = telefono
        this.edad = edad
    }

    /*El constructor vació se crea para la creación de la clase sin instanciar nada*/
    constructor() {

    }
}
