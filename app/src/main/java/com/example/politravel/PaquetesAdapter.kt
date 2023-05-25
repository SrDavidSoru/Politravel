package com.example.politravel

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PaquetesAdapter (private val context: Context,
                       private val paquetes: MutableList<Paquete>):
    RecyclerView.Adapter<PaquetesAdapter.PaquetesViewHolder>(), View.OnClickListener,
    View.OnLongClickListener {
    private val layout = R.layout.paquetes_item
    private var clickListener: View.OnClickListener? = null
    private var longClickListener: View.OnLongClickListener? = null

    class PaquetesViewHolder (val view: View): RecyclerView.ViewHolder(view) {
        var lblTituloPaquete: TextView
        var lblDiasPaquete: TextView
        var imgPaquete: ImageView
        var imgVehiculo: ImageView
        var lblPrecio: TextView
        var lblInicio: TextView
        var lblFinal: TextView

        init {
            lblTituloPaquete = view.findViewById(R.id.tituloPaquete)
            lblDiasPaquete = view.findViewById(R.id.diasPaquete)
            imgPaquete = view.findViewById(R.id.imgPaquete)
            imgVehiculo = view.findViewById(R.id.imgVehiculoPaquete)
            lblPrecio = view.findViewById(R.id.precioPaquete)
            lblInicio = view.findViewById(R.id.inicioPaquete)
            lblFinal = view.findViewById(R.id.finalPaquete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaquetesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
        return PaquetesViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaquetesViewHolder, position: Int) {
        val paquete = paquetes[position]
        bindPaquete(holder, paquete)
    }

    fun bindPaquete (holder: PaquetesViewHolder, paquete: Paquete) {
        holder.lblTituloPaquete?.text = paquete.titulo
        holder.lblDiasPaquete?.text = paquete.dias.toString()
        holder.lblPrecio?.text = paquete.precio.toString()
        holder.lblInicio?.text = paquete.inicio
        holder.lblFinal?.text = paquete.final

        val portadaPath: String
        if(!paquete.imgCiudad.isEmpty()) {
            portadaPath = context.getFilesDir().toString() + "/img/" + paquete.imgCiudad.get(0)
        } else {
            portadaPath = context.getFilesDir().toString() + "/img/sinfotos.png"
        }
        val bitmap = BitmapFactory.decodeFile(portadaPath)
        holder.imgPaquete?.setImageBitmap(bitmap)

        val vehiculoPath = context.getFilesDir().toString() + "/img/" + paquete.imgTransporte
        val bitmap2 = BitmapFactory.decodeFile(vehiculoPath)
        holder.imgVehiculo?.setImageBitmap(bitmap2)

    }

    fun setOnClickListener (listener: View.OnClickListener) {
        clickListener = listener
    }

    fun setOnLongClickListener(listener: View.OnLongClickListener) {
        longClickListener = listener
    }

    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }



    override fun getItemCount() = paquetes.size
    override fun onLongClick(view: View?): Boolean {
        longClickListener?.onLongClick(view)
        return true
    }


}