package gestion

import juego.Historial
import usuario.Usuario
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.StreamCorruptedException

class GestionarHistoriales {
    private val FICHERO_HISTORIALES = "historiales.dat"
    private var historiales : ArrayList<Historial>

    constructor() {
        this.historiales = this.obtenerHistoriales()
    }

    fun añadirHistorial(historial : Historial) {
        this.historiales.add(historial)
        this.guardarHistoriales()
        println("Historial creado correctamente.")
    }

    fun borrarHistorial(historial : Historial) {
        if (this.historiales.contains(historial)) {
            this.historiales.remove(historial)
            this.guardarHistoriales()
            println("Historial borrado correctamente.")
        }else {
            println("No se ha podido encontrar el historial")
        }
    }

    fun obtenerHistorial(usuario : Usuario) : Historial? {
        var historialBuscado : Historial? = null

        for (historial in this.historiales) {
            if (historial.emailJugador.equals(usuario.email, true)) {
                historialBuscado = historial
            }
        }

        if (historialBuscado == null) {
            println("No se ha encontrado el historial.")
        }

        return historialBuscado
    }

    fun obtenerHistorial(email : String) : Historial? {
        var historialBuscado : Historial? = null

        for (historial in this.historiales) {
            if (historial.emailJugador.equals(email, true)) {
                historialBuscado = historial
            }
        }

        if (historialBuscado == null) {
            println("No se ha encontrado el historial.")
        }

        return historialBuscado
    }

    fun modificarHistorial(historialOriginal : Historial, datosNuevos : Historial) {
        var exito = false
        for (i in this.historiales.indices) {
            if (this.historiales[i] == historialOriginal) {
                this.historiales[i] = datosNuevos
                exito = true
            }
        }

        if (exito) {
            this.guardarHistoriales()
            println("Se ha modificado el historial correctamente.")
        }else {
            println("No se ha encontrado el historial.")
        }
    }

    private fun obtenerHistoriales() : ArrayList<Historial> {
        var historialesExistentes = ArrayList<Historial>()
        val fichero = File(FICHERO_HISTORIALES)
        if (fichero.exists()) {
            var objectInputStream : ObjectInputStream? = null
            try {
                val fileInputStream = FileInputStream(FICHERO_HISTORIALES)
                objectInputStream = ObjectInputStream(fileInputStream)
                historialesExistentes = objectInputStream.readObject() as ArrayList<Historial>
            }catch (exception : StreamCorruptedException) {
                println("Los datos no se han podido obtener.")
            }finally {
                objectInputStream?.close()
            }
        }
        return historialesExistentes
    }

    private fun guardarHistoriales() {
        val fileStream = FileOutputStream(FICHERO_HISTORIALES)
        val objectStream = ObjectOutputStream(fileStream)
        objectStream.writeObject(historiales)
        objectStream.close()
    }
}