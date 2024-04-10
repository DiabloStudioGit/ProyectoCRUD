package Gestion

import Juego.Historial
import Usuario.Roles
import Usuario.Usuario

interface IGestorHistoriales {

    fun añadirHistorial(historial : Historial)
    fun borrarHistorial(historial : Historial)
    fun obtenerHistorial(usuario : Usuario) : Historial?//
    fun obtenerHistorial(email : String) : Historial?//
    fun modificarHistorial(historialOriginal : Historial, datosNuevos : Historial, esAdmin : Boolean)
    fun obtenerHistoriales() : ArrayList<Historial> //
}