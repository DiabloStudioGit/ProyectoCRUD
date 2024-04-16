package Gestion.BaseDeDatos

import Data.Encriptacion
import java.sql.Connection
import java.sql.DriverManager

class ConexionBD {
    companion object {
        var connection : Connection? = null
    }

    constructor() {
        //TODO
        //val credenciales : ArrayList<String> = menuBases()
        ConexionBD.connection = DriverManager.getConnection(credenciales[2], credenciales[1], credenciales[0])
    }

    fun recogerRemoto() : ArrayList<String> {
        val enc = Encriptacion("A3VapMg22jAlIvO1")
        val credenciales : ArrayList<String> = arrayListOf()
        enc.desencriptar("credencialesRemoto.dat").forEach {str ->
            credenciales.add(str)
        }
        return credenciales
    }

    fun guardarLocal(clave : String, usuario : String, passwd : String, url : String) {
        val enc = Encriptacion(clave)
        enc.encriptar("credencialesLocal.dat","$usuario\n$passwd\n$url")
    }

    fun recogerLocal(clave : String) : ArrayList<String> {
        val enc = Encriptacion(clave)
        val credenciales : ArrayList<String> = arrayListOf()
        enc.desencriptar("credencialesLocal.dat").forEach {str ->
            credenciales.add(str)
        }
        return credenciales
    }
}