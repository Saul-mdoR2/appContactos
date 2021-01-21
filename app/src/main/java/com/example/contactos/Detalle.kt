package com.example.contactos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class Detalle : AppCompatActivity() {

    private var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        index = intent.getStringExtra("ID")!!.toInt()

        mapearDatos()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalle, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.iEliminar -> {
                MainActivity.removeContacto(index)
                finish()
                return true
            }

            R.id.iEditar -> {
                val intent = Intent(this, NuevoContacto::class.java)
                intent.putExtra("ID", index.toString())
                startActivity(intent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun mapearDatos() {
        val contacto = MainActivity.getContacto(index)

        val nombre = findViewById<TextView>(R.id.tvNombre)
        val empresa = findViewById<TextView>(R.id.tvEmpresa)
        val edad = findViewById<TextView>(R.id.tvEdad)
        val direccion = findViewById<TextView>(R.id.tvDireccion)
        val peso = findViewById<TextView>(R.id.tvPeso)
        val email = findViewById<TextView>(R.id.tvEmail)
        val telefono = findViewById<TextView>(R.id.tvTelefono)
        val imagen: ImageView = findViewById(R.id.ivFotografia)

        nombre.text = String.format("%s %s", contacto?.nombre, contacto?.apellidos)
        empresa.text = contacto?.empresa
        edad.text = contacto?.edad.toString()
        direccion.text = contacto?.direccion
        peso.text = contacto?.peso.toString()
        email.text = contacto?.email
        telefono.text = contacto?.telefono
        imagen.setImageResource(contacto?.foto!!)
    }

    override fun onResume() {
        mapearDatos()
        super.onResume()
    }
}