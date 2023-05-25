package com.example.politravel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileReader
import java.io.FileWriter

class CrearEditarPaquete : AppCompatActivity() {
    object PaquetesConstant {
        const val PAQUETE = "PAQUETE"
    }



    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult())
    {

        if (it.resultCode == RESULT_OK) {
            val paths = it.data?.getStringArrayListExtra("paths")
            if (paths != null) {
                updateEverything(paths)
            }
        }
    }
    private var lstVacia = ArrayList<String>()
    private var emptyPaquete = Paquete(0,"", 1, lstVacia, "", 0, "", "", lstVacia)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.crear_editar_paquete)
        var paqueteEscogido = (intent.getSerializableExtra(DetailActivy.PaquetesConstant.PAQUETE) as Paquete?)

        if (paqueteEscogido != null) {
            updateEverything(paqueteEscogido.imgCiudad)
        } else {
            updateEverything(emptyPaquete.imgCiudad)
        }
    }

    fun updateEverything(paths: ArrayList<String>) {
        var paqueteEscogido = (intent.getSerializableExtra(DetailActivy.PaquetesConstant.PAQUETE) as Paquete?)

        var imgAvionSelect = findViewById<ImageView>(R.id.imgAvionSelect)
        var imgBarcoSelect = findViewById<ImageView>(R.id.imgBarcoSelect)
        var imgTrenSelect = findViewById<ImageView>(R.id.imgTrenSelect)
        var lblTransporte = findViewById<TextView>(R.id.transporteEditar)
        var numDias = findViewById<EditText>(R.id.diasPaqueteEditar)
        var imgSumarDias = findViewById<ImageView>(R.id.imgSumarDias)
        var imgRestarDias = findViewById<ImageView>(R.id.imgRestarDias)
        var txtDiasEditar = findViewById<TextView>(R.id.txtDiasEditar)
        var btnNuevaParada = findViewById<ImageView>(R.id.imgNuevaParada)
        var txtNuevaParada = findViewById<EditText>(R.id.nuevaParadaEditar)
        var lstParadas = findViewById<ListView>(R.id.listParadasEditar)
        var paradas = ArrayList<String>()
        var txtNombrePaquete = findViewById<EditText>(R.id.nombrePaqueteEditar)
        var txtPrecio = findViewById<EditText>(R.id.precioPaqueteEditar)
        var txtInicio = findViewById<EditText>(R.id.inicioEditar)
        var txtFinal = findViewById<EditText>(R.id.finalEditar)
        var lstImagenes = findViewById<RecyclerView>(R.id.lstImagenesEditar)
        var btnEditarImg = findViewById<ImageView>(R.id.editarImgButton)
        var btnGuardarCambios = findViewById<Button>(R.id.btnGuardarCambiosEditar)
        var btnBorrarPaquete = findViewById<ImageView>(R.id.imgBorrar)
        var numDiasInt = 15

        if(paqueteEscogido != null) {
            btnBorrarPaquete.isVisible = true
            paqueteEscogido.imgCiudad = paths
            numDiasInt = paqueteEscogido.dias
            paradas = paqueteEscogido.paradas

            when(paqueteEscogido.imgTransporte) {
                "avion.png" -> {
                    imgAvionSelect.setImageResource(R.drawable.avion)
                    imgBarcoSelect.setImageResource(R.drawable.barcouns)
                    imgTrenSelect.setImageResource(R.drawable.trenuns)
                    lblTransporte.text = "Avión"
                }
                "barco.png" -> {
                    imgAvionSelect.setImageResource(R.drawable.avionuns)
                    imgBarcoSelect.setImageResource(R.drawable.barco)
                    imgTrenSelect.setImageResource(R.drawable.trenuns)
                    lblTransporte.text = "Barco"
                }
                "tren.png" -> {
                    imgAvionSelect.setImageResource(R.drawable.avionuns)
                    imgBarcoSelect.setImageResource(R.drawable.barcouns)
                    imgTrenSelect.setImageResource(R.drawable.tren)
                    lblTransporte.text = "Tren"
                }
            }

            txtNombrePaquete.setText(paqueteEscogido.titulo)
            txtPrecio.setText(paqueteEscogido.precio.toString())
            txtInicio.setText(paqueteEscogido.inicio)
            txtFinal.setText(paqueteEscogido.final)

            val adapterImgs = ImgAdapter(this, paqueteEscogido)
            lstImagenes.hasFixedSize()
            lstImagenes.layoutManager = LinearLayoutManager (this, LinearLayoutManager.HORIZONTAL, false)
            lstImagenes.adapter = adapterImgs
        } else {
            btnBorrarPaquete.isVisible = false
            val adapterImgs = ImgAdapter(this, emptyPaquete)
            lstImagenes.hasFixedSize()
            lstImagenes.layoutManager = LinearLayoutManager (this, LinearLayoutManager.HORIZONTAL, false)
            lstImagenes.adapter = adapterImgs

            emptyPaquete.imgCiudad = paths

            numDiasInt = emptyPaquete.dias

            paradas = emptyPaquete.paradas
            txtNombrePaquete.setText(emptyPaquete.titulo)
            txtPrecio.setText(emptyPaquete.precio.toString())
            txtInicio.setText(emptyPaquete.inicio)
            txtFinal.setText(emptyPaquete.final)

            numDiasInt = emptyPaquete.dias
        }

        val adapterParadas = ParadasAdapter(this, R.layout.parada_item, paradas)
        updateParadas(lstParadas, adapterParadas)



        lstParadas.setOnItemLongClickListener { parent, view, position, id ->
            val paradaEscogida = parent.getItemAtPosition(position)
            paradas.remove(paradaEscogida)
            updateParadas(lstParadas, adapterParadas)
            true
        }

        btnNuevaParada.setOnClickListener() {
            if (!txtNuevaParada.text.isEmpty()) {
                paradas.add(txtNuevaParada.text.toString())
                updateParadas(lstParadas, adapterParadas)
                txtNuevaParada.setText("")
            }
        }


        numDias.setText(numDiasInt.toString())

        numDias.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!numDias.text.isEmpty()) {
                    numDiasInt = numDias.text.toString().toInt()
                } else {
                    numDiasInt = 1
                }

                if(numDiasInt > 100) {
                    numDiasInt = 100
                    numDias.setText(numDiasInt.toString())
                }

                if(numDiasInt < 1) {
                    numDiasInt = 1
                    numDias.setText(numDiasInt.toString())
                }

                if(numDiasInt == 1) {
                    txtDiasEditar.text = "día"
                } else {
                    txtDiasEditar.text = "días"
                }

            }
        })

        imgSumarDias.setOnClickListener() {
            if(numDiasInt < 100) {
                numDiasInt++
                numDias.setText(numDiasInt.toString())
            }
        }

        imgRestarDias.setOnClickListener() {
            if(numDiasInt > 1) {
                numDiasInt--
                numDias.setText(numDiasInt.toString())
            }
        }

        imgAvionSelect.setOnClickListener() {
            imgAvionSelect.setImageResource(R.drawable.avion)
            imgBarcoSelect.setImageResource(R.drawable.barcouns)
            imgTrenSelect.setImageResource(R.drawable.trenuns)
            lblTransporte.text = "Avión"
        }

        imgBarcoSelect.setOnClickListener() {
            imgAvionSelect.setImageResource(R.drawable.avionuns)
            imgBarcoSelect.setImageResource(R.drawable.barco)
            imgTrenSelect.setImageResource(R.drawable.trenuns)
            lblTransporte.text = "Barco"
        }


        imgTrenSelect.setOnClickListener() {
            imgAvionSelect.setImageResource(R.drawable.avionuns)
            imgBarcoSelect.setImageResource(R.drawable.barcouns)
            imgTrenSelect.setImageResource(R.drawable.tren)
            lblTransporte.text = "Tren"
        }

        btnEditarImg.setOnClickListener() {
            val intent = Intent(this, SelectImgs::class.java)
            if(paqueteEscogido != null) {
                fillPaqueteObj(paqueteEscogido, txtNombrePaquete, lblTransporte, numDias,
                txtPrecio, txtInicio, txtFinal, paradas)
                intent.putExtra(DetailActivy.PaquetesConstant.PAQUETE, paqueteEscogido)
            } else {
                fillPaqueteObj(emptyPaquete, txtNombrePaquete, lblTransporte, numDias,
                    txtPrecio, txtInicio, txtFinal, paradas)
                intent.putExtra(DetailActivy.PaquetesConstant.PAQUETE, emptyPaquete)
            }
            getResult.launch(intent)
        }

        btnBorrarPaquete.setOnClickListener() {
            var paqueteBorrar = emptyPaquete
            var listPaquetes = getPaquetes()
            for(p: Paquete in listPaquetes) {
                if (paqueteEscogido != null) {
                    if (paqueteEscogido.id == p.id) {
                        paqueteBorrar = p
                    }
                }
            }
            listPaquetes.remove(paqueteBorrar)
            var cont = 1
            for(p: Paquete in listPaquetes) {
                p.id = cont
                cont++
            }
            reWritePaquetes(listPaquetes)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("msg", "Paquete borrado con éxito")
            setResult(RESULT_OK, intent)
            finish()
        }

        btnGuardarCambios.setOnClickListener() {
            if(checkCampos(txtNombrePaquete, txtPrecio, txtInicio, txtFinal, numDias, lblTransporte) == true) {
                var listPaquetes = getPaquetes()
                if(paqueteEscogido != null) {
                    fillPaqueteObj(paqueteEscogido, txtNombrePaquete, lblTransporte, numDias,
                        txtPrecio, txtInicio, txtFinal, paradas)
                    for(p: Paquete in listPaquetes) {
                        if(paqueteEscogido.id == p.id) {
                            p.id = paqueteEscogido.id
                            p.dias = paqueteEscogido.dias
                            p.paradas = paqueteEscogido.paradas
                            p.imgTransporte = paqueteEscogido.imgTransporte
                            p.imgCiudad = paqueteEscogido.imgCiudad
                            p.precio = paqueteEscogido.precio
                            p.final = paqueteEscogido.final
                            p.inicio = paqueteEscogido.inicio
                            p.titulo = paqueteEscogido.titulo
                            reWritePaquetes(listPaquetes)
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("msg", "Paquete editado con éxito")
                            setResult(RESULT_OK, intent)
                            finish()
                        }
                    }
                } else {
                    fillPaqueteObj(emptyPaquete, txtNombrePaquete, lblTransporte, numDias,
                        txtPrecio, txtInicio, txtFinal, paradas)
                    if(!listPaquetes.isEmpty()) {
                        emptyPaquete.id = getHighestId(listPaquetes) + 1
                    } else {
                        emptyPaquete.id = 1
                    }

                    listPaquetes.add(emptyPaquete)
                    reWritePaquetes(listPaquetes)
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("msg", "Paquete añadido con éxito")
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }


        }
    }

    fun checkCampos(tituloPaquete: EditText, txtPrecio: EditText, txtInicio: EditText,
                    txtFinal: EditText, txtNumDias: EditText, lblTransporte: TextView) : Boolean {
        var camposLlenos = true

        if(tituloPaquete.text.isEmpty()) {
            tituloPaquete.setError("Rellena este campo para continuar")
            camposLlenos = false
        }

        if(txtPrecio.text.isEmpty()) {
            txtPrecio.setError("Rellena este campo para continuar")
            camposLlenos = false
        }

        if(txtInicio.text.isEmpty()) {
            txtInicio.setError("Rellena este campo para continuar")
            camposLlenos = false
        }

        if(txtFinal.text.isEmpty()) {
            txtFinal.setError("Rellena este campo para continuar")
            camposLlenos = false
        }

        if(txtNumDias.text.isEmpty()) {
            txtNumDias.setError("Rellena este campo para continuar")
            camposLlenos = false
        }

        if(!lblTransporte.text.equals("Avión") && !lblTransporte.text.equals("Barco") && !lblTransporte.text.equals("Tren")) {
            Toast.makeText(this, "Escoge un transporte para guardar los cambios", Toast.LENGTH_LONG).show()
            camposLlenos = false
        }


        return camposLlenos
    }

    fun reWritePaquetes(paquetesList: MutableList<Paquete>) {
        val jsonpathfile = "$filesDir/json/paquetes.json"
        val jsonFile = FileWriter(jsonpathfile)
        var gson = Gson()
        var jsonElement = gson.toJson(paquetesList)
        jsonFile.write(jsonElement)
        jsonFile.close()
    }

    fun getHighestId(lstPaquetes: MutableList<Paquete>): Int {
        var highestId = 0
        highestId = lstPaquetes.get(0).id

        for(p: Paquete in lstPaquetes) {
            if(p.id > highestId) {
                highestId = p.id
            }
        }

        return highestId
    }

    fun getPaquetes(): MutableList<Paquete>
    {
        val jsonpathfile = "$filesDir/json/paquetes.json"
        val jsonFile = FileReader(jsonpathfile)
        val listpaquetes = object: TypeToken<MutableList<Paquete>>() {}.type
        val paquetes: MutableList<Paquete> = Gson().fromJson(jsonFile, listpaquetes)

        return paquetes
    }

    fun fillPaqueteObj(paquete: Paquete, txtPaquete: EditText, lblTransporte: TextView,
                        txtNumDias: EditText, txtPrecio: EditText, txtInicio: EditText,
                        txtFinal: EditText, lstParadas: ArrayList<String>) {
        paquete.titulo = txtPaquete.text.toString()
        paquete.inicio = txtInicio.text.toString()
        paquete.final = txtFinal.text.toString()
        paquete.dias = txtNumDias.text.toString().toInt()
        paquete.precio = txtPrecio.text.toString().toInt()
        paquete.paradas = lstParadas

        when(lblTransporte.text) {
            "Avión" -> paquete.imgTransporte = "avion.png"
            "Barco" -> paquete.imgTransporte = "barco.png"
            "Tren" -> paquete.imgTransporte = "tren.png"
        }

    }


    fun updateParadas(lstParadas: ListView, adapterParadas: ParadasAdapter) {
        lstParadas.adapter = adapterParadas
        updateListViewHeight(lstParadas)
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