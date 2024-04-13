package Gestion.Fichero

import Gestion.IGestorLogs
import Gestion.Log
import UI.MenuColores
import java.io.*

class GestionarLogs : IGestorLogs {
    private val FICHERO_LOG = "log.dat"
    private var logs : ArrayList<Log>

    /**
     * Constructor para inicializar los logs.
     */
    constructor() {
        this.logs = this.obtenerLogs()
    }

    /**
     * Añade el log al listado de logs tanto instanciado como al fichero.
     *
     * @param log Log a añadir
     */
    override fun añadirLog(log: Log) {
        this.logs.add(log)
        this.guardarLogs()
    }


    /**
     * Devuelve los logs con el email en cuestion.
     *
     * @param email Usuario a buscar entre los re gistros.
     * @return Devuelve un Array con los logs en caso de encontrarlo o null en caso opuesto.
     */
    override fun obtenerLog(email: String): ArrayList<Log>? {
        var logsEncontrados = ArrayList<Log>()

        for (log in this.logs) {
            if (log.email.equals(email, true)) {
                logsEncontrados.add(log)
            }
        }

        return if (logsEncontrados.isEmpty()) {
            null
        } else {
            logsEncontrados
        }
    }

    /**
     * Obtiene todos los registros que se encuentren en el fichero.
     *
     * @return Devuelve la lista de logs.
     */
    override fun obtenerLogs(): ArrayList<Log> {
        var logsExistentes = arrayListOf<Log>()

        val fichero = File(FICHERO_LOG)
        if (fichero.exists()) {
            var objectInputStream : ObjectInputStream? = null
            try {
                val fileInputStream = FileInputStream(FICHERO_LOG)
                objectInputStream = ObjectInputStream(fileInputStream)
                logsExistentes = objectInputStream.readObject() as ArrayList<Log>
            } catch (exception : StreamCorruptedException) {
                println(MenuColores.error() + " Error al obtener los registros.")
            } finally {
                objectInputStream?.close()
            }
        }
        return logsExistentes
    }

    /**
     * Imprime en pantalla la informacion de todos los registros.
     */
    override fun mostarLogs() {
        if (this.logs.isNotEmpty()) {
            var index = 0
            for (log in this.logs) {
                println("${++index} $log")
            }
        } else {
            println(MenuColores.info() + " No hay logs para mostrar.")
        }
    }

    override fun modificarLog(correoOriginal: String, correoNuevo: String) {
        var exito = false
        for (i in this.logs.indices) {
            if (this.logs[i].email == correoOriginal) {
                this.logs[i].email = correoNuevo
                exito = true
            }

            if (!exito) {
                println(MenuColores.error() + " No se ha podido modificar el Log")
            }
        }
    }

    /**
     * Guarda/Actualiza los logs que se encuentren actualmente en la memoria del programa.
     */
    private fun guardarLogs() {
        val fileStream = FileOutputStream(FICHERO_LOG)
        val objectStream = ObjectOutputStream(fileStream)
        objectStream.writeObject(logs)
        objectStream.close()
    }
}