package com.example.politravel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileReader

class MainActivity : AppCompatActivity() {
    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult())
    {

        if (it.resultCode == RESULT_OK) {
            val msg = it.data?.getStringExtra("msg")
            if (msg != null) {
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                updateEverything()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.Theme_Politravel)
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        updateEverything()
    }

    fun updateEverything() {
        var listPaquetes = findViewById<RecyclerView>(R.id.listPaquetes)
        var btnNuevoPaquete = findViewById<ImageButton>(R.id.btnCrearPaquete)

        val adapter = PaquetesAdapter(this, getPaquetes())
        listPaquetes.hasFixedSize()
        listPaquetes.layoutManager = LinearLayoutManager (this)
        listPaquetes.adapter = adapter

        adapter.setOnClickListener() {
            val intent = Intent(this, DetailActivy::class.java)
            val paquete = getPaquetes()[listPaquetes.getChildAdapterPosition(it)]
            intent.putExtra(DetailActivy.PaquetesConstant.PAQUETE, paquete)
            startActivity(intent)
        }

        adapter.setOnLongClickListener() {
            val intent = Intent(this, CrearEditarPaquete::class.java)
            val paquete = getPaquetes()[listPaquetes.getChildAdapterPosition(it)]
            intent.putExtra(CrearEditarPaquete.PaquetesConstant.PAQUETE, paquete)
            getResult.launch(intent)
            true
        }

        btnNuevoPaquete.setOnClickListener() {
            val intent = Intent(this, CrearEditarPaquete::class.java)
            getResult.launch(intent)
        }
    }

    fun getPaquetes(): MutableList<Paquete>
    {
        val jsonpathfile = "$filesDir/json/paquetes.json"
        val jsonFile = FileReader(jsonpathfile)
        val listpaquetes = object: TypeToken<MutableList<Paquete>>() {}.type
        val paquetes: MutableList<Paquete> = Gson().fromJson(jsonFile, listpaquetes)

        return paquetes
    }
}