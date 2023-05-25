package com.example.politravel

import java.io.Serializable

class Paquete (var id: Int, var titulo: String, var dias: Int, var imgCiudad: ArrayList<String>, var imgTransporte: String, var precio: Int, var inicio: String, var final: String, var paradas: ArrayList<String>) : Serializable {

}