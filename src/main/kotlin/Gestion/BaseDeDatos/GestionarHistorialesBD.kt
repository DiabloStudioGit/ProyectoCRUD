package Gestion.BaseDeDatos

import Gestion.Gestores
import Gestion.IGestorHistoriales
import Gestion.Log
import Data.Juego.Historial
import UI.MenuColores
import Data.Usuario.Usuario
import java.sql.SQLException

class GestionarHistorialesBD : IGestorHistoriales {
    /**
     * Añade un nuevo registro al historial de un jugador en la base de datos.
     *
     * @param historial El objeto Historial que se va a añadir al historial del jugador.
     */
    override fun añadirHistorial(historial: Historial) {
        val query = "INSERT INTO historial (email, partidasJugadas, partidasGanadas, puntos) VALUES (?, ?, ?, ?)"

        try {
            val statement = ConexionBD.connection!!.prepareStatement(query)
            statement.setString(1, historial.emailJugador)
            statement.setInt(2, historial.partidasJugadas)
            statement.setInt(3, historial.partidasGanadas)
            statement.setInt(4, historial.puntos)

            val logRegistro = Log(historial.emailJugador, Gestores.fechaActual(), "Historial de juego creado")
            Gestores.gestorLogs.añadirLog(logRegistro)

            statement.executeUpdate()
            statement.close()
        } catch (e: SQLException) {
            if (e.errorCode == 1062) {
                println(MenuColores.error() + " El historial ya existe.")
            } else {
                println(MenuColores.error() + " Error al añadir el historial:")
                println(MenuColores.amarillo("[${e.errorCode}]") +  "${e.message}")
            }
        }
    }

    /**
     * Elimina el historial de un jugador de la base de datos.
     *
     * @param historial El objeto Historial cuyo historial se va a eliminar de la base de datos.
     */
    override fun borrarHistorial(historial: Historial) {
        val log = obtenerHistorial(historial.emailJugador)
        if (log != null) {
            val query = "DELETE FROM historial WHERE email = ?"

            try {
                val statement = ConexionBD.connection!!.prepareStatement(query)
                statement.setString(1, historial.emailJugador)

                val logEliminar = Log(historial.emailJugador, Gestores.fechaActual(), "Historial de juego eliminado")
                Gestores.gestorLogs.añadirLog(logEliminar)

                statement.executeUpdate()
                statement.close()
            } catch (e: SQLException) {
                println(MenuColores.error() + " Error al eliminar al historial:")
                println(MenuColores.amarillo("[${e.errorCode}]") +  "${e.message}")
            }
        } else {
            println(MenuColores.error() + " El historial no existe.")
        }
    }

    /**
     * Obtiene el historial de un jugador desde la base de datos.
     *
     * @param usuario El objeto Usuario del cual se desea obtener el historial.
     * @param info Devuelve información en caso de error.
     * @return El objeto Historial del jugador correspondiente, o null si no se encuentra en la base de datos.
     */
    override fun obtenerHistorial(usuario: Usuario, info: Boolean): Historial? {
        val sql = "SELECT * FROM historial WHERE email = ?"
        val statement = ConexionBD.connection!!.prepareStatement(sql)
        statement.setString(1, usuario.email)
        val resultSet = statement.executeQuery()

        var historial: Historial? = null

        try {
            if (resultSet.next()) {
                val partidasJugadas = resultSet.getInt("partidasJugadas")
                val partidasGanadas = resultSet.getInt("partidasGanadas")
                val puntos = resultSet.getInt("puntos")

                historial = Historial(usuario.email, partidasJugadas, partidasGanadas, puntos)
            } else {
                if (info) {
                    println(MenuColores.error() + " No se ha encontrado el historial.")
                }
            }
        } catch (e: SQLException) {
            if (info) {
                println(MenuColores.error() + " Error al obtener los datos del historial:")
                println(MenuColores.amarillo("[${e.errorCode}]") +  "${e.message}")
            }
        } finally {
            resultSet.close()
            statement.close()
        }

        return historial
    }

    /**
     * Obtiene el historial de un jugador según su dirección de correo electrónico desde la base de datos.
     *
     * @param email La dirección de correo electrónico del jugador del cual se desea obtener el historial.
     * @return El objeto Historial del jugador correspondiente, o null si no se encuentra en la base de datos.
     */
    override fun obtenerHistorial(email: String): Historial? {
        val sql = "SELECT * FROM historial WHERE email = ?"
        val statement = ConexionBD.connection!!.prepareStatement(sql)
        statement.setString(1, email)
        val resultSet = statement.executeQuery()

        var historial: Historial? = null

        try {
            if (resultSet.next()) {
                val partidasJugadas = resultSet.getInt("partidasJugadas")
                val partidasGanadas = resultSet.getInt("partidasGanadas")
                val puntos = resultSet.getInt("puntos")

                historial = Historial(email, partidasJugadas, partidasGanadas, puntos)
            }
        } catch (e: SQLException) {
            println(MenuColores.error() + " Error al obtener los datos del historial:")
            println(MenuColores.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return historial
    }

    /**
     * Modifica el historial de un jugador en la base de datos.
     *
     * @param historialOriginal El objeto Historial cuyos datos se van a modificar.
     * @param datosNuevos El objeto Historial con los nuevos datos que se van a asignar al historial del jugador.
     * @param esAdmin Un booleano que indica si el usuario que realiza la modificación es un administrador.
     */
    override fun modificarHistorial(historialOriginal: Historial, datosNuevos: Historial, esAdmin: Boolean) {
        val historial = Gestores.gestorUsuarios.obtenerUsuario(historialOriginal.emailJugador)
        if (historial != null) {

            val query = "UPDATE historial SET email = ?, partidasJugadas = ?, partidasGanadas = ?, puntos = ? WHERE email = ?"

            try {
                val statement = ConexionBD.connection!!.prepareStatement(query)
                statement.setString(1, datosNuevos.emailJugador)
                statement.setInt(2, datosNuevos.partidasJugadas)
                statement.setInt(3, datosNuevos.partidasGanadas)
                statement.setInt(4, datosNuevos.puntos)
                statement.setString(5, historialOriginal.emailJugador)

                statement.executeUpdate()
                statement.close()

                if (esAdmin) {
                    println(MenuColores.ok() + " Historial modificado con éxito")
                }
            } catch (e: SQLException) {
                println(MenuColores.error() + " Error al modificar el historial:")
                println(MenuColores.amarillo("[${e.errorCode}]") +  "${e.message}")
            }
        } else {
            println(MenuColores.error() + " El historial no existe.")
        }
    }

    /**
     * Obtiene todos los registros de historial de jugadores almacenados en la base de datos.
     *
     * @return ArrayList de objetos Historial que representan todos los registros de historial de jugadores almacenados en la base de datos.
     */
    override fun obtenerHistoriales(): ArrayList<Historial> {
        val listaHistorial = ArrayList<Historial>()

        val query = "SELECT * FROM historial"
        val statement = ConexionBD.connection!!.prepareStatement(query)
        val resultSet = statement.executeQuery()

        try {
            while (resultSet.next()) {
                val email = resultSet.getString("email")
                val historial = obtenerHistorial(email)
                if (historial != null) {
                    listaHistorial.add(historial)
                }
            }
        } catch (e: SQLException) {
            println(MenuColores.error() + " Error al obtener los datos del historial:")
            println(MenuColores.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return listaHistorial
    }
}