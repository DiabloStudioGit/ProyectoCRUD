package Gestion

import Data.Juego.Historial
import Data.Usuario.Usuario

interface IGestorHistoriales {

    fun añadirHistorial(historial : Historial)
    fun borrarHistorial(historial : Historial)
    fun obtenerHistorial(usuario : Usuario, info: Boolean) : Historial?//
    fun obtenerHistorial(email : String) : Historial?//
    fun modificarHistorial(historialOriginal : Historial, datosNuevos : Historial, esAdmin : Boolean)
    fun obtenerHistoriales() : ArrayList<Historial> //
}