package com.example.contactos

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    var viewSwitcher: ViewSwitcher? = null

    companion object {
        var contactos: ArrayList<Contacto>? = null
        var adapter: AdapterCustom? = null
        var adaptadorGrid: AdaptadorCustomGrid? = null
        var contenedor: String? = null

        fun addContacto(contacto: Contacto) {
            adapter?.addItem(contacto)
            adaptadorGrid?.actualizar(adaptadorGrid?.copiaItems!!)
            adaptadorGrid?.notifyDataSetChanged()
        }

        fun getContacto(Index: Int): Contacto? {
            return if (contenedor == "ListView") {
                adapter?.getItem(Index) as Contacto
            } else {
                adaptadorGrid?.getItem(Index) as Contacto
            }
        }

        fun removeContacto(Index: Int) {
            if (contenedor == "ListView") {
                adapter?.removeItem(Index)
                adaptadorGrid?.actualizar(adapter?.items!!)
            } else {
                adaptadorGrid?.removeItem(Index)
                adapter?.actualizar(adaptadorGrid?.items!!)
            }
        }

        fun updateContacto(Index: Int, nuevoContacto: Contacto) {
            if (contenedor == "ListView") {
                adapter?.updateItem(Index, nuevoContacto)
                adaptadorGrid?.actualizar(adapter?.items!!)
            } else {
                adaptadorGrid?.updateItem(Index, nuevoContacto)
                adapter?.actualizar(adaptadorGrid?.items!!)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        contactos = ArrayList()

        contactos?.add(
            Contacto(
                "Saúl",
                "Maldonado",
                "R2 DEV PROS",
                21,
                55.4f,
                "Mina #513",
                "861 116 76 60",
                "saul_99_27@hotmail.com",
                R.drawable.foto_03
            )
        )
        contactos?.add(
            Contacto(
                "Hector",
                "Gomez",
                "FAMSA",
                29,
                80.1f,
                "Juarez #014",
                "861 116 85 74",
                "hector@hotmail.com",
                R.drawable.foto_01
            )
        )
        contactos?.add(
            Contacto(
                "Carla",
                "Flores",
                "GUTIERREZ",
                49,
                66.5f,
                "Independencia #513",
                "861 679 47 20",
                "carla@gmail.com",
                R.drawable.foto_05
            )
        )
        contactos?.add(
            Contacto(
                "Emma",
                "Watson",
                "WARNER",
                33,
                74.9f,
                "Casas #12678",
                "861 111 14 89",
                "emma@hotmail.com",
                R.drawable.foto_04
            )
        )
        contactos?.add(
            Contacto(
                "Rosa",
                "Delgado",
                "COPPEL",
                8,
                8.44f,
                "Ramos #2223",
                "861 116 77 77",
                "rosa@hotmail.com",
                R.drawable.foto_06
            )
        )
        contactos?.add(
            Contacto(
                "Sebastian",
                "Ramirez",
                "MERCO",
                78,
                100.58f,
                "Niños heroes #8A",
                "61 2 54 08",
                "sebastian@hotmail.com",
                R.drawable.foto_02
            )
        )

        val lista = findViewById<ListView>(R.id.lista)
        val grid = findViewById<GridView>(R.id.grid)

        adapter = AdapterCustom(this, contactos!!)
        adaptadorGrid = AdaptadorCustomGrid(this, contactos!!)

        lista?.adapter = adapter
        grid?.adapter = adaptadorGrid

        viewSwitcher = findViewById(R.id.viewSwitcher)

        lista?.setOnItemClickListener { _, _, position, _ ->
            contenedor = "ListView"
            val intent = Intent(this, Detalle::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)
        }

        grid?.setOnItemClickListener { _, _, position, _ ->
            contenedor = "GridView"
            val intent = Intent(this, Detalle::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)
        }
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda = menu?.findItem(R.id.busqueda)
        val searchView = itemBusqueda?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Buscar contacto..."

        searchView.setOnQueryTextFocusChangeListener { _, _ ->
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filtrar(newText!!)
                adaptadorGrid?.filtrar(newText!!)
                return true
            }

        })

        val itemSwitch = menu.findItem(R.id.switchView)
        itemSwitch.setActionView(R.layout.switch_item)
        val switchView = itemSwitch.actionView?.findViewById<Switch>(R.id.sCambiaVista)

        switchView?.setOnCheckedChangeListener { _, _ ->
            viewSwitcher?.showNext()
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.iNuevo -> {
                val intent = Intent(this, NuevoContacto::class.java)
                startActivity(intent)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResume() {
        adapter?.notifyDataSetChanged()
        adaptadorGrid?.notifyDataSetChanged()
        super.onResume()
    }
}