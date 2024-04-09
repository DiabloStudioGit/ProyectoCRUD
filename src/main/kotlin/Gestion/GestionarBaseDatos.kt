package Gestion

import DatosBD
import Juego.Historial
import Usuario.Roles
import Usuario.Usuario
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException


class GestionarBaseDatos: DatosBD {


    fun conectarBD(): Connection?{
        val bduser = "root"
        val bdpasswd = ""
        val bdurl = "jdbc:mysql://localhost:3306/crud"

        return try {
            DriverManager.getConnection(bdurl, bduser, bdpasswd)
        } catch (e: SQLException) {
            println("[ERROR] Al conectar a base de datos: ${e.message}")
            null
        }
    }

    fun obtenerUsuario(email: String, connection: Connection): Usuario? {
        val statement = connection.prepareStatement("SELECT * FROM usuarios WHERE email = ?")
        statement.setString(1, email)
        val resultSet = statement.executeQuery()

        var usuario: Usuario? = null

        try {
            if (resultSet.next()) {
                val nombre = resultSet.getString("nombre")
                val apellidos = resultSet.getString("apellidos")
                val edad = resultSet.getInt("edad")
                val contrasenia = resultSet.getString("password")
                var rol: Roles
                if (resultSet.getInt("rol") == 1) {
                    rol = Roles.ADMINISTRADOR
                } else {
                    rol = Roles.ESTANDAR
                }

                usuario = Usuario(nombre, apellidos, edad, email, contrasenia, rol)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            resultSet.close()
            statement.close()
        }

        return usuario
    }

    fun obtenerHistorial(email: String, connection: Connection): Historial? {
        val sql = "SELECT * FROM historial WHERE email = ?"
        val statement = connection.prepareStatement(sql)
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
            e.printStackTrace()
        } finally {
            resultSet.close()
            statement.close()
        }

        return historial
    }







}
