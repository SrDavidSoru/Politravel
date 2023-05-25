package com.example.politravel

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ParadasAdapter (context: Context, val layout: Int, val paradas: ArrayList<String>):
    ArrayAdapter<String>(context, layout, paradas), View.OnClickListener{
    private var clickListener: View.OnClickListener? = null
    private var longClickListener: View.OnLongClickListener? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View

        if(convertView != null) {
            view = convertView
        } else {
            view = LayoutInflater.from(getContext()).inflate(layout, parent, false)
        }

        bindParada (view, paradas[position])

        return view
    }

    fun bindParada (view: View, parada: String) {
        val nombreParada = view.findViewById<TextView>(R.id.nombreParada)

        nombreParada.text = parada

    }

    fun setOnClickListener (listener: View.OnClickListener) {
        clickListener = listener
    }

    fun setOnLongClickListener (listener: View.OnLongClickListener) {
        longClickListener = listener
    }


    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

    fun onLongClick(view: View?): Boolean {
        longClickListener?.onLongClick(view)
        return true
    }
}