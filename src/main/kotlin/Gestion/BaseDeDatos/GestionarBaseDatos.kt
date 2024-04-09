package Gestion.BaseDeDatos

import Gestion.IGestorHistoriales
import Gestion.IGestorUsuarios
import Juego.Historial
import Usuario.Roles
import Usuario.Usuario
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException


class GestionarBaseDatos : IGestorUsuarios, IGestorHistoriales {
    private val connection : Connection

    constructor() {
        val bduser = "root"
        val bdpasswd = ""
        val bdurl = "jdbc:mysql://localhost:3306/crud"

        this.connection = DriverManager.getConnection(bdurl, bduser, bdpasswd)
    }

    //USUARIOS
    override fun añadirUsuario(usuario : Usuario) {
        TODO("Not yet implemented")
    }

    override fun borrarUsuario(usuario: Usuario) {
        TODO("Not yet implemented")
    }

    override fun obtenerUsuario(email : String) : Usuario? {
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

    override fun modificarPermisos(usuario : Usuario, rol : Roles) {
        TODO("Not yet implemented")
    }

    override fun modificarUsuario(usuarioOriginal : Usuario, datosNuevos : Usuario) {
        TODO("Not yet implemented")
    }

    override fun mostrarUsuarios() {
        TODO("Not yet implemented")
    }

    override fun obtenerUsuarios(): ArrayList<Usuario> {
        TODO("Not yet implemented")
    }


    //HISTORIALES
    override fun añadirHistorial(historial: Historial) {
        TODO("Not yet implemented")
    }

    override fun borrarHistorial(historial: Historial) {
        TODO("Not yet implemented")
    }

    override fun obtenerHistorial(usuario: Usuario): Historial? {
        TODO("Not yet implemented")
    }

    override fun obtenerHistorial(email: String): Historial? {
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

    override fun modificarHistorial(historialOriginal: Historial, datosNuevos: Historial, esAdmin: Boolean) {
        TODO("Not yet implemented")
    }

    override fun obtenerHistoriales(): ArrayList<Historial> {
        TODO("Not yet implemented")
    }
}
