package com.example.contactos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar

class NuevoContacto : AppCompatActivity() {

    var fotoIndex: Int = 0

    val fotos = arrayOf(
        R.drawable.foto_01,
        R.drawable.foto_02,
        R.drawable.foto_03,
        R.drawable.foto_04,
        R.drawable.foto_05,
        R.drawable.foto_06
    )

    var foto: ImageView? = null

    var index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_contacto)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        foto = findViewById<ImageView>(R.id.ivFotografia)
        foto?.setOnClickListener {
            seleccionarFoto()
        }

        if (intent.hasExtra("ID")) {
            index = intent.getStringExtra("ID")?.toInt()!!
            rellenarDatos(index)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nuevo_contacto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.iCrearNuevo -> {

                val nombre: EditText = findViewById(R.id.tvNombre)
                val apellidos: EditText = findViewById(R.id.tvApellidos)
                val empresa: EditText = findViewById(R.id.tvEmpresa)
                val edad: EditText = findViewById(R.id.tvEdad)
                val peso: EditText = findViewById(R.id.tvPeso)
                val direccion: EditText = findViewById(R.id.tvDireccion)
                val telefono: EditText = findViewById(R.id.tvTelefono)
                val email: EditText = findViewById(R.id.tvEmail)

                val campos = ArrayList<String>()
                campos.add(nombre.text.toString())
                campos.add(apellidos.text.toString())
                campos.add(empresa.text.toString())
                campos.add(edad.text.toString())
                campos.add(peso.text.toString())
                campos.add(direccion.text.toString())
                campos.add(telefono.text.toString())
                campos.add(email.text.toString())

                var flag = 0
                for (campo in campos) {
                    if (campo.isEmpty())
                        flag++
                }
                if (flag > 0) {
                    Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_LONG).show()
                } else {
                    if (index > -1) {
                        MainActivity.updateContacto(
                            index,
                            Contacto(
                                campos[0],
                                campos[1],
                                campos[2],
                                campos[3].toInt(),
                                campos[4].toFloat(),
                                campos[5],
                                campos[6],
                                campos[7],
                                obtenerFoto(fotoIndex)
                            )
                        )
                    } else {
                        MainActivity.addContacto(
                            Contacto(
                                campos[0],
                                campos[1],
                                campos[2],
                                campos[3].toInt(),
                                campos[4].toFloat(),
                                campos[5],
                                campos[6],
                                campos[7],
                                obtenerFoto(fotoIndex)
                            )
                        )
                    }
                    finish()
                }
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun seleccionarFoto() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.mensaje_dialogo_foto))

        val adaptadorDialogo =
            ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item)
        adaptadorDialogo.add("Foto 01")
        adaptadorDialogo.add("Foto 02")
        adaptadorDialogo.add("Foto 03")
        adaptadorDialogo.add("Foto 04")
        adaptadorDialogo.add("Foto 05")
        adaptadorDialogo.add("Foto 06")

        builder.setAdapter(adaptadorDialogo) { dialog, which ->
            fotoIndex = which

            foto?.setImageResource(obtenerFoto(fotoIndex))
        }


        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    fun obtenerFoto(index: Int): Int {
        return fotos[index]
    }

    fun rellenarDatos(index: Int) {
        val contacto = MainActivity.getContacto(index)

        val nombre = findViewById<EditText>(R.id.tvNombre)
        val apellidos = findViewById<EditText>(R.id.tvApellidos)
        val empresa = findViewById<EditText>(R.id.tvEmpresa)
        val edad = findViewById<EditText>(R.id.tvEdad)
        val direccion = findViewById<EditText>(R.id.tvDireccion)
        val peso = findViewById<EditText>(R.id.tvPeso)
        val email = findViewById<EditText>(R.id.tvEmail)
        val telefono = findViewById<EditText>(R.id.tvTelefono)
        val imagen = findViewById<ImageView>(R.id.ivFotografia)

        nombre.setText(contacto?.nombre, TextView.BufferType.EDITABLE)
        apellidos.setText(contacto?.apellidos, TextView.BufferType.EDITABLE)
        empresa.setText(contacto?.empresa, TextView.BufferType.EDITABLE)
        edad.setText(contacto?.edad.toString(), TextView.BufferType.EDITABLE)
        direccion.setText(contacto?.direccion, TextView.BufferType.EDITABLE)
        peso.setText(contacto?.peso.toString(), TextView.BufferType.EDITABLE)
        email.setText(contacto?.email, TextView.BufferType.EDITABLE)
        telefono.setText(contacto?.telefono, TextView.BufferType.EDITABLE)
        imagen.setImageResource(contacto?.foto!!)

        var posicion = 0
        for (foto in fotos) {
            if (contacto.foto == foto) {
                fotoIndex = posicion
            }
            posicion++
        }

    }
}