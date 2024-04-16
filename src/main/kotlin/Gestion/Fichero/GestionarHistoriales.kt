package Gestion.Fichero

import Gestion.Gestores
import Gestion.IGestorHistoriales
import Gestion.Log
import Data.Juego.Historial
import UI.MenuColores
import Data.Usuario.Usuario
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.StreamCorruptedException

class GestionarHistoriales : IGestorHistoriales {
    private val FICHERO_HISTORIALES = "historiales.dat"
    private var historiales : ArrayList<Historial>

    /**
     * Constructor para inicializar los historiales.
     */
    constructor() {
        this.historiales = this.obtenerHistoriales()
    }

    /**
     * Añade el historial al listado de historiales tanto instanciado como al fichero.
     *
     * @param historial Historial a añadir
     */
    override fun añadirHistorial(historial : Historial) {
        this.historiales.add(historial)
        this.guardarHistoriales()
        println(MenuColores.ok() + " Historial creado correctamente.")
        val logRegistro = Log(historial.emailJugador, Gestores.fechaActual(), "Historial de juego creado")
        Gestores.gestorLogs.añadirLog(logRegistro)
    }

    /**
     * Borra el historial de la lista de historiales y actualiza el fichero.
     *
     * @param historial Historial a eliminar.
     */
    override fun borrarHistorial(historial : Historial) {
        if (this.historiales.contains(historial)) {
            this.historiales.remove(historial)
            this.guardarHistoriales()
            println(MenuColores.ok() + " Historial " + MenuColores.rojo("borrado") + " correctamente.")
            val logEliminar = Log(historial.emailJugador, Gestores.fechaActual(), "Historial de juego eliminado")
            Gestores.gestorLogs.añadirLog(logEliminar)
        }else {
            println(MenuColores.error() + " No se ha podido encontrar el historial")
        }
    }

    /**
     * Devuelve el historial con el usuario en cuestion.
     *
     * @param usuario Usuario a buscar entre los historailes.
     * @param info Devuelve información en caso de error.
     * @return El historial en caso de encontrarlo o null en caso opuesto.
     */
    override fun obtenerHistorial(usuario : Usuario, info: Boolean) : Historial? {
        var historialBuscado : Historial? = null

        for (historial in this.historiales) {
            if (historial.emailJugador.equals(usuario.email, true)) {
                historialBuscado = historial
            }
        }

        if (historialBuscado == null) {
            if (info) {
                println(MenuColores.error() + " No se ha encontrado el historial.")
            }
        }

        return historialBuscado
    }

    /**
     * Devuelve el historial con el email en cuestion.
     *
     * @param email Email a buscar entre los historailes.
     * @return El historial en caso de encontrarlo o null en caso opuesto.
     */
    override fun obtenerHistorial(email : String) : Historial? {
        var historialBuscado : Historial? = null

        for (historial in this.historiales) {
            if (historial.emailJugador.equals(email, true)) {
                historialBuscado = historial
            }
        }

        if (historialBuscado == null) {
            println(MenuColores.error() + " No se ha encontrado el historial.")
        }

        return historialBuscado
    }

    /**
     * Modifica un historial completo a raiz de una plantilla (Otro historial falso).
     *
     * @param historialOriginal Historial a modificar.
     * @param datosNuevos Plantilla de historial con los datos nuevos.
     * @param esAdmin En caso de ser verdadero muestra mensaje de Exito
     */
    override fun modificarHistorial(historialOriginal : Historial, datosNuevos : Historial, esAdmin : Boolean) {
        var exito = false
        for (i in this.historiales.indices) {
            if (this.historiales[i] == historialOriginal) {
                this.historiales[i] = datosNuevos
                exito = true
            }
        }

        if (exito && esAdmin) {
            this.guardarHistoriales()
            println(MenuColores.ok() + " Se ha modificado el historial correctamente.")
        }else if (exito && !esAdmin) {
            this.guardarHistoriales()
        } else if (!esAdmin) {
            println(MenuColores.error() + " No se ha podido guardar la Puntuacion.")
        } else {
            println(MenuColores.error() + " No se ha encontrado el historial.")
        }
    }

    /**
     * Obtiene todos los historiales que se encuentren en el fichero.
     *
     * @return Devuelve la lista de historiales.
     */
    override fun obtenerHistoriales() : ArrayList<Historial> {
        var historialesExistentes = ArrayList<Historial>()
        val fichero = File(FICHERO_HISTORIALES)
        if (fichero.exists()) {
            var objectInputStream : ObjectInputStream? = null
            try {
                val fileInputStream = FileInputStream(FICHERO_HISTORIALES)
                objectInputStream = ObjectInputStream(fileInputStream)
                historialesExistentes = objectInputStream.readObject() as ArrayList<Historial>
            }catch (exception : StreamCorruptedException) {
                println(MenuColores.error() + " Los datos no se han podido obtener.")
            }finally {
                objectInputStream?.close()
            }
        }
        return historialesExistentes
    }

    /**
     * Guarda/Actualiza los historiales que se encuentren actualmente en la memoria del programa.
     */
    private fun guardarHistoriales() {
        val fileStream = FileOutputStream(FICHERO_HISTORIALES)
        val objectStream = ObjectOutputStream(fileStream)
        objectStream.writeObject(historiales)
        objectStream.close()
    }
}