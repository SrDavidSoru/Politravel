package com.example.politravel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SelectImgs : AppCompatActivity() {
    object PaquetesConstant {
        const val PAQUETE = "PAQUETE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_imgs)
        supportActionBar?.hide()
        var listImagenes = findViewById<RecyclerView>(R.id.listImgs)
        val paqueteEscogido = intent.getSerializableExtra(DetailActivy.PaquetesConstant.PAQUETE) as Paquete
        var btnGuardarCambios = findViewById<Button>(R.id.btnGuardarCambios)

        //hacer un foreach de las img del paquete por cada img de este array y setear a true las que cuya ruta coincida y las otras false
        var lstImagenes = ArrayList<Imagen>()
        lstImagenes.add(0, Imagen("Tokyo", "tokyo.jpeg", false))
        lstImagenes.add(1, Imagen("Tokyo", "tokyo2.jpeg", false))
        lstImagenes.add(2, Imagen("Tokyo", "tokyo3.jpeg", false))
        lstImagenes.add(3, Imagen("Ámsterdam", "amsterdam.jpeg", false))
        lstImagenes.add(4, Imagen("Ámsterdam", "amsterdam2.jpeg", false))
        lstImagenes.add(5, Imagen("Ámsterdam", "amsterdam3.jpeg", false))
        lstImagenes.add(6, Imagen("Edimburgo", "edimburgo.jpeg", false))
        lstImagenes.add(7, Imagen("Edimburgo", "edimburgo2.jpeg", false))
        lstImagenes.add(8, Imagen("Edimburgo", "edimburgo3.jpeg", false))
        lstImagenes.add(9, Imagen("Mallorca", "mallorca.jpeg", false))
        lstImagenes.add(10, Imagen("Mallorca", "mallorca2.jpeg", false))
        lstImagenes.add(11, Imagen("Mallorca", "mallorca3.jpeg", false))
        lstImagenes.add(12, Imagen("París", "paris.jpeg", false))
        lstImagenes.add(13, Imagen("París", "paris2.jpeg", false))
        lstImagenes.add(14, Imagen("París", "paris3.jpeg", false))

        for(img: Imagen in lstImagenes) {
            for(path: String in paqueteEscogido.imgCiudad) {
                if(img.path.equals(path)) {
                    img.escogida = true
                }
            }
        }


        val adapterImgs = SelectImgAdapter(this, lstImagenes)
        listImagenes.hasFixedSize()
        listImagenes.layoutManager = GridLayoutManager (this, 2)
        listImagenes.adapter = adapterImgs

        adapterImgs.setOnClickListener() {
            val numImgEscogida = listImagenes.getChildAdapterPosition(it)
            var imgEscogida = lstImagenes.get(numImgEscogida)
            imgEscogida.escogida = !imgEscogida.escogida
            lstImagenes.set(numImgEscogida, imgEscogida)
            adapterImgs.notifyDataSetChanged()
        }

        btnGuardarCambios.setOnClickListener() {
            var listPaths = ArrayList<String>()
            for(img: Imagen in lstImagenes) {
                if(img.escogida == true) {
                    listPaths.add(img.path)
                }
            }
            val intent = Intent(this, CrearEditarPaquete::class.java)
            intent.putExtra("paths", listPaths)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}