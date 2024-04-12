package Gestion

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Gestor {

    companion object {
        //false (Sistema Ficheros) - true (SistemaBD)
        var eleccion = false

        //Fecha actual formateada
        fun fechaActual(): String {
            return (LocalDateTime.now()).format(DateTimeFormatter.ofPattern("HH:mm dd:MM:yyyy"))
        }
    }
}