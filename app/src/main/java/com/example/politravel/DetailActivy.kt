package com.example.politravel

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DetailActivy : AppCompatActivity() {
    object PaquetesConstant {
        const val PAQUETE = "PAQUETE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.detail_paquete)

        val paqueteEscogido = intent.getSerializableExtra(PaquetesConstant.PAQUETE) as Paquete
        val listImagenes = findViewById(R.id.lstImagenes) as RecyclerView
        val listParadas = findViewById<ListView>(R.id.listParadas)
        val lblTituloDetail = findViewById<TextView>(R.id.nombrePaqueteDetail)
        val lblInicioDetail = findViewById<TextView>(R.id.inicioDetail)
        val lblFinalDetail = findViewById<TextView>(R.id.finalDetail)
        val lblVehiculoDetail = findViewById<TextView>(R.id.nombreVehiculoDetail)
        val imgVehiculoDetail = findViewById<ImageView>(R.id.imgVehiculoDetail)
        val imgMaps = findViewById<ImageView>(R.id.mapsIconDetail)
        val lblPrecio = findViewById<TextView>(R.id.precioPaqueteDetail)
        val lblDias = findViewById<TextView>(R.id.diasPaqueteDetail)


        lblInicioDetail.text = paqueteEscogido.inicio
        lblFinalDetail.text = paqueteEscogido.final
        lblTituloDetail.text = paqueteEscogido.titulo
        lblPrecio.text = paqueteEscogido.precio.toString()
        lblDias.text = paqueteEscogido.dias.toString()

        val imgPath = getFilesDir().toString() + "/img/" + paqueteEscogido.imgTransporte
        val bitmap = BitmapFactory.decodeFile(imgPath)
        imgVehiculoDetail.setImageBitmap(bitmap)

        when(paqueteEscogido.imgTransporte) {
            "avion.png" -> lblVehiculoDetail.text = "Avión"
            "barco.png" -> lblVehiculoDetail.text = "Barco"
            "tren.png" -> lblVehiculoDetail.text = "Tren"
        }

        val adapterParadas = ParadasAdapter(this, R.layout.parada_item, paqueteEscogido.paradas)
        listParadas.adapter = adapterParadas
        updateListViewHeight(listParadas)

        val adapterImgs = ImgAdapter(this, paqueteEscogido)
        listImagenes.hasFixedSize()
        listImagenes.layoutManager = LinearLayoutManager (this, LinearLayoutManager.HORIZONTAL, false)
        listImagenes.adapter = adapterImgs


        imgMaps.setOnClickListener() {
            Toast.makeText(this, "Mapa no disponible temporalmente", Toast.LENGTH_SHORT).show()
        }


    }

    fun updateListViewHeight(myListView: ListView)
    {
        val myListAdapter: ParadasAdapter = (myListView.getAdapter() ?: return) as ParadasAdapter
        // get listview height
        var totalHeight = 0
        val adapterCount: Int = myListAdapter.getCount()
        for (size in 0 until adapterCount) {
            val listItem: View = myListAdapter.getView(size, null, myListView)
            listItem.measure(0, 0)
            totalHeight += listItem.getMeasuredHeight()
        }
        // Change Height of ListView
        val params: ViewGroup.LayoutParams = myListView.getLayoutParams()
        params.height = (totalHeight
                + myListView.getDividerHeight() * adapterCount)
        myListView.setLayoutParams(params)
    }
}