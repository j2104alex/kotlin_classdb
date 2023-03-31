package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


/*Se crea la clase base de datos (todas las clases deben tener un contexto) con la variable context que se le asigna
* la clase Context para hacer referencia a la clase actual.
*  SQLiteOpenHelper para llamar a la base de datos, recibe los parametros de context, el nombre de la base de datos
* factory que permite que la inserción sea nula y la version que debe ser int */
class BaseDatos(context: Context): SQLiteOpenHelper(context, "Venta",null,1) {

    /*Implementeción de los metodos de manera automatica de SQLiteOpenHelper*/
    override fun onCreate(db: SQLiteDatabase?) {

        /*Se crea la variable sql donde se va a almacenar la creación de la tabla y se asignan los
        * campos y el tipo de campo que es*/
        var sql = "CREATE TABLE Usuarios (cedula varchar(25) primary key, nombre varchar(25), apellido varchar(25),telefono varchar(25),edad varchar(25));"
        /*el ? es para comprobar si se hace referencia a un valor nulo, .execSQL cuando se ejecute el SQL de la variable sql*/
        db?.execSQL(sql)
    }

    /*Implementeción de los metodos de manera automatica de SQLiteOpenHelper*/
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    /*Función guardar recibe un parametro user que es de tipo clase Usuario y retorna un string al final de la ejecución (:String)*/
    fun Guardar(user: Usuario): String {

        /*variable db de la funcion Guardar implementa la propiedad writableDatabase para insertar un nuevo registro o fila */
        val db = this.writableDatabase

        /*Se define el contenedor como una lista para almacenar los parametros guardados en user*/
        var contenedor = ContentValues()
        /*Put es para añadir a la lista, para traer las propiedades del user de cada atributo*/
        contenedor.put("cedula", user.cedula)
        contenedor.put("nombre", user.nombre)
        contenedor.put("apellido", user.apellido)
        contenedor.put("telefono", user.telefono)
        contenedor.put("edad", user.edad)

        /*Ejecuta la insercion en la base de datos mediante la variable resultado
        * db ejecuta el metodo insert donde se inserta en la tabla Usuarios el contenido del contenedor (lista) mediante parametros*/
        var resultado = db.insert("Usuarios", null, contenedor)

        try {
            /*Cuando un resultado es -1 es un error*/
            if ( resultado == -1.toLong()) {
                return "Existe un error al insertar"
            } else {
                /*De lo contrario funciona correctamente*/
                return "Se guardó exitosamente"
            }
            /*En el catch se almacena en ex el error y se debe convertir porque la función espera que se retorne un String*/
        } catch (ex: Exception) {
            return ex.message.toString()
            /*Se debe cerrar la conexión a la base de datos con el finally y el metodo close*/
        } finally {
            db.close()
        }
    }
    /*CON PARAMETROS*/
    /*Función actualizar datos que recibe los parametros de string, nombre, apellido, telefono y edad
    * pero modifica todos menos la cedula*/
    fun actualizarDatos1(cedula:String, nombre:String, apellido:String, telefono:String, edad:String):String{
        val db=this.writableDatabase
        var contenedor= ContentValues()
        contenedor.put("nombre",nombre)
        contenedor.put("apellido",apellido)
        contenedor.put("telefono",telefono)
        contenedor.put("edad",edad)

        /*Se crea la variable resultado para implementar el metodo update de la base de datos en la
        * tabla Usuario el contenedor(lista), donde se recibe un parametro condicional para la ejecucion
        * de la actualizacion que se va a buscar en un arreglo de cedulas */
        var resultado =db.update("Usuarios",contenedor,"cedula=?", arrayOf(cedula))

        try {
            if(resultado>0){
                return "Actualización exitosa"
            }else{
                return "Error en la actualización"
            }
        }catch (ex:Exception){
            return ex.message.toString()
        }finally{
            db.close()
        }
    }

    /*SIN PARAMETROS*/
    fun actualizarDatos2(user:Usuario):String{
        val db=this.writableDatabase
        var contenedor= ContentValues()
        contenedor.put("nombre",user.nombre)
        contenedor.put("apellido",user.apellido)
        contenedor.put("telefono",user.telefono)
        contenedor.put("edad",user.edad)

        /*Se crea la variable resultado para implementar el metodo update de la base de datos en la
        * tabla Usuario el contenedor(lista), donde se recibe un parametro condicional para la ejecucion
        * de la actualizacion que se va a buscar en un arreglo de cedulas */
        var resultado =db.update("Usuarios",contenedor,"cedula=?", arrayOf(user.cedula))

        try {
            if(resultado>0){
                return "Actualización exitosa"
            }else{
                return "Error en la actualización"
            }
        }catch (ex:Exception){
            return ex.message.toString()
        }finally{
            db.close()
        }
    }

    /*Funcion eliminar*/
    fun eliminarDatos(user:String):String{

        val db=this.writableDatabase

        var resultado =db.delete("Usuarios","cedula=?", arrayOf(user))

        try {
            if(resultado>0){
                return "Usuario eliminado"
            }else{
                return "Error eliminando usuario"
            }
        }catch (ex:Exception){
            return ex.message.toString()
        }finally{
            db.close()
        }
    }

    /*Funcion listar datos*/
    @SuppressLint("Range")
    fun ListarDatos():MutableList<Usuario>{
        /*En la variable lista de tipo lista mutable que recibe elementos de tipo Usuario  va  almacenar una
        * lista de arreglos*/
        val lista:MutableList<Usuario> =ArrayList()
        /*En la variable db toma la base de datos y lee la base de datos*/
        val db=this.readableDatabase
        /*En la variable sql vamos a colocar la sentencia a ejecutar en la bd*/
        val sql="SELECT * FROM Usuarios"
        /*En la variable resultado vamos a tomar la base de datos
        * con rawQuery se ejecuta la consulta y recibimos como parametro la consulta y argumentos nullos*/
        var resultado=db.rawQuery(sql,null)

        /*Es como decir que arranca en el primer registro-moveToFirst*/
        if(resultado.moveToFirst()){
                do{
                    /*En la variable datos u se crea la variable de tipo Usuario
                    * Como hay un constructor vacio permite instanciar la clase sin
                    * instanciarlo*/
                    var datosu= Usuario()
                    /*a la variable datosu de tipo usuario estamos llamando las propiedades
                    * para asignarles el resultado de la consulta con getString se obtiene la
                    * primer fila y así sucesivamente*/
                    datosu.cedula=resultado.getString(resultado.getColumnIndex("cedula"))
                    datosu.nombre=resultado.getString(resultado.getColumnIndex("nombre"))
                    datosu.apellido=resultado.getString(resultado.getColumnIndex("apellido"))
                    datosu.telefono=resultado.getString(resultado.getColumnIndex("telefono"))
                    datosu.edad=resultado.getString(resultado.getColumnIndex("edad"))
                /*Se añade a la var lista el contenido de la var datosu*/
                lista.add(datosu)
                /*Es como decir que avanza al siguiente registro -moveToNext*/
                }while (resultado.moveToNext())
                resultado.close()
                db.close()
        }
        return lista;
    }
}