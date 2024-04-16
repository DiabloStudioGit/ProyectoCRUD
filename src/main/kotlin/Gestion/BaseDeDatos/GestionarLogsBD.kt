package Gestion.BaseDeDatos

import Gestion.IGestorLogs
import Gestion.Log
import UI.MenuColores
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class GestionarLogsBD : IGestorLogs {

    /**
     * Añade un nuevo registro a la base de datos.
     *
     * @param log El objeto Log que se va a añadir a la base de datos.
     */
    override fun añadirLog(log: Log) {
        val query = "INSERT INTO logs (usuario, fecha, accion) VALUES (?, ?, ?)"

        try {
            val statement = ConexionBD.connection!!.prepareStatement(query)
            statement.setString(1, log.email)
            statement.setString(2, log.fecha)
            statement.setString(3, log.accion)

            statement.executeUpdate()
            statement.close()
        } catch (e: SQLException) {
            println(MenuColores.error() + " Error al añadir el registro:")
            println(MenuColores.amarillo("[${e.errorCode}]") +  "${e.message}")
        }
    }

    /**
     * Obtiene los logs de un usuario de la base de datos según su dirección de correo electrónico.
     *
     * @param email La dirección de correo electrónico del usuario que se desea obtener.
     * @return Devuelve un Array con los logs en caso de encontrarlo o null si no se encuentra en la Basd de Datos.
     */
    override fun obtenerLog(email: String): ArrayList<Log>? {
        val statement = ConexionBD.connection!!.prepareStatement("SELECT * FROM logs WHERE usuario = ?")
        statement.setString(1, email)
        val resultSet = statement.executeQuery()

        val logsEncontrados = ArrayList<Log>()

        try {
            while (resultSet.next()) {
                val emailLog = resultSet.getString("usuario")
                val fechaLog = resultSet.getString("fecha")
                val accionLog = resultSet.getString("accion")

                val log = Log(emailLog, fechaLog, accionLog)
                logsEncontrados.add(log)
            }
        } catch (e: SQLException) {
            println(MenuColores.error() + " Error al obtener los datos del registro:")
            println(MenuColores.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return if (logsEncontrados.isNotEmpty()) {
            logsEncontrados
        } else {
            null
        }
    }

    /**
     * Obtiene todos los registros almacenados en la base de datos.
     *
     * @return ArrayList de objetos Log que representan a todos los registros almacenados en la base de datos.
     */
    override fun obtenerLogs(): ArrayList<Log> {
        val listaLogs = ArrayList<Log>()

        val query = "SELECT * FROM logs"
        val statement = ConexionBD.connection!!.prepareStatement(query)
        val resultSet = statement.executeQuery()

        try {
            while (resultSet.next()) {
                val email = resultSet.getString("usuario")
                val fecha = resultSet.getString("fecha")
                val accion = resultSet.getString("accion")

                val log = Log(email, fecha, accion)
                listaLogs.add(log)
            }
        } catch (e: SQLException) {
            println(MenuColores.error() + " Error al obtener los datos de los registros:")
            println(MenuColores.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return listaLogs
    }

    /**
     * Muestra todos los registros almacenados en la base de datos.
     * Imprime los detalles de cada registro en la consola, incluyendo email, fecha y accion.
     */
    override fun mostarLogs() {
        println("Logs:")
        obtenerLogs().forEachIndexed { index, log ->
            println("${index + 1}. ${MenuColores.azul("Usuario:")} ${log.email}, ${MenuColores.magenta("Fecha:")} ${log.fecha}, ${MenuColores.cian("Accion:")} ${log.accion}")
        }
    }

    /**
     * Modifica los registros con el nuevo correo del Usuario.
     *
     * @param correoOriginal Registro a modificar.
     * @param correoNuevo Datos nuevos para el Registro a modificar.
     */
    override fun modificarLog(correoOriginal: String, correoNuevo: String) {
        val query = "UPDATE logs SET usuario = ? WHERE usuario = ?"
        val statement = ConexionBD.connection!!.prepareStatement(query)
        statement.setString(1, correoNuevo)
        statement.setString(2, correoOriginal)

        try {
            val rowsAffected = statement.executeUpdate()
            println(MenuColores.info() + " Se han modificado $rowsAffected registros.")
            println(MenuColores.ok() + " Logs modificados correctamente.")
        } catch (e: SQLException) {
            println(MenuColores.error() + " Error al modificar los registros de logs:")
            println(MenuColores.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            statement.close()
        }
    }
}