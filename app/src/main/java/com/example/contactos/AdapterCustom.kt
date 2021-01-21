@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.contactos

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList

class AdapterCustom(private var contexto: Context, items: ArrayList<Contacto>) : BaseAdapter() {

    var items: ArrayList<Contacto>? = null
    private var copiaItems: ArrayList<Contacto>? = null

    init {
        this.items = ArrayList(items)
        this.copiaItems = items
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolder?
        var vista: View? = convertView

        if (vista == null) {
            vista = LayoutInflater.from(contexto).inflate(R.layout.template_contacto, null)
            viewHolder = ViewHolder(vista)
            vista.tag = viewHolder
        } else {
            viewHolder = vista.tag as? ViewHolder
        }
        val item = getItem(position) as Contacto

        viewHolder?.nombre?.text = item.nombre
        viewHolder?.apellido?.text = item.apellidos
        viewHolder?.empresa?.text = item.empresa
        viewHolder?.foto?.setImageResource(item.foto)

        return vista!!
    }

    override fun getItem(position: Int): Any {
        return this.items?.get(position)!!
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items?.count()!!
    }

    fun addItem(item: Contacto) {
        copiaItems?.add(item)
        items = ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun removeItem(Index: Int) {
        copiaItems?.removeAt(Index)
        items = ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun updateItem(Index: Int, newItem: Contacto) {
        copiaItems?.set(Index, newItem)
        items = ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun filtrar(str: String) {
        items?.clear()
        if (str.isEmpty()) {
            items = ArrayList(copiaItems)
            notifyDataSetChanged()
            return
        }
        var busqueda = str
        busqueda = busqueda.toLowerCase(Locale.getDefault())

        for (item in copiaItems!!) {
            val nombre = item.nombre.toLowerCase(Locale.getDefault())
            val apellido = item.apellidos.toLowerCase(Locale.getDefault())

            if (nombre.contains(busqueda) || apellido.contains(busqueda)) {
                items?.add(item)
            }
        }
        notifyDataSetChanged()
    }

    fun actualizar(contactos: ArrayList<Contacto>) {
        items = ArrayList(contactos)
        notifyDataSetChanged()
    }

    private class ViewHolder(vista: View) {
        var nombre: TextView? = null
        var foto: ImageView? = null
        var empresa: TextView? = null
        var apellido: TextView? = null

        init {
            this.nombre = vista.findViewById(R.id.tvNombre)
            this.empresa = vista.findViewById(R.id.tvEmpresa)
            this.foto = vista.findViewById(R.id.ivfoto)
            this.apellido = vista.findViewById(R.id.tvApellido)
        }
    }
}