package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var cedula:TextInputEditText
    lateinit var nombre:TextInputEditText
    lateinit var apellido:TextInputEditText
    lateinit var telefono:TextInputEditText
    lateinit var edad:TextInputEditText
    lateinit var listaU:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cedula=findViewById(R.id.input_cedula)
        nombre=findViewById(R.id.input_nombre)
        apellido=findViewById(R.id.input_apellido)
        telefono=findViewById(R.id.input_telefono)
        edad=findViewById(R.id.input_edad)
        listaU =findViewById(R.id.textList)
    }
    fun  GuardarDatos(view:View){
        var db=BaseDatos(this)
        var usu=Usuario()
        if(
            cedula.text.toString().length> 0 &&
            nombre.text.toString().length> 0 &&
            apellido.text.toString().length> 0 &&
            telefono.text.toString().length> 0 &&
            edad.text.toString().length> 0
        ){

            usu.cedula=cedula.text.toString()
            usu.nombre=nombre.text.toString()
            usu.apellido=apellido.text.toString()
            usu.telefono=telefono.text.toString()
            usu.edad=edad.text.toString()

            var mensaje =db.Guardar(usu)
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        }
    }
    fun  ListaDatos(view:View){
        listaU.text=""
        val listaU = findViewById<TextView>(R.id.textList)
        var db=BaseDatos(this)
        var datosL = db.ListarDatos()
        listaU.visibility = View.VISIBLE
        for(i in 0..datosL.size-1){
            listaU.append(
                "Cédula:"+datosL.get(i).cedula+"\n"
                        +"Nombre: "+datosL.get(i).nombre+"\n"+
                        "Apellido: "+datosL.get(i).apellido+"\n"+
                        "Telefono: "+datosL.get(i).telefono+"\n"+
                        "Edad: "+datosL.get(i).edad
                )
            }
        }
    fun eliminarDatos(view:View) {
        var db = BaseDatos(this)

        if(cedula.text.toString().length>0){
            var mensaje = db.eliminarDatos(cedula.text.toString())
            listaU.text = mensaje
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "El campo cedula es requerido", Toast.LENGTH_SHORT).show()
        }
    }
    fun actualizarDatos(view:View){
        var db = BaseDatos(this)

        var mensaje = db.actualizarDatos1(
            cedula.text.toString(),
            nombre.text.toString(),
            apellido.text.toString(),
            telefono.text.toString(),
            edad.text.toString())

        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
    }
