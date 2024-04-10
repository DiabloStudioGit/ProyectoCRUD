package Gestion.BaseDeDatos

import Gestion.Fichero.GestionarHistoriales
import Gestion.IGestorHistoriales
import Gestion.IGestorUsuarios
import Juego.Historial
import UI.MenuColores
import Usuario.Roles
import Usuario.Usuario
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException


class GestionarBaseDatos : IGestorUsuarios, IGestorHistoriales {
    private val connection : Connection

    constructor() {
        val bduser = ""
        val bdpasswd = ""
        val bdurl = "jdbc:mysql://:3306/crud"
        this.connection = DriverManager.getConnection(bdurl, bduser, bdpasswd)
    }

    //USUARIOS
    override fun añadirUsuario(usuario : Usuario) {
        var historial = Historial(usuario.email, 0, 0, 0)
        val query = "INSERT INTO usuarios (nombre, apellidos, edad, email, password, rol) VALUES (?, ?, ?, ?, ?, ?)"

        try {
            val statement = connection.prepareStatement(query)
            statement.setString(1, usuario.nombre)
            statement.setString(2, usuario.apellido)
            statement.setInt(3, usuario.edad)
            statement.setString(4, usuario.email)
            statement.setString(5, usuario.contrasenia)
            if (usuario.rol == Roles.ESTANDAR) {
                statement.setInt(6,0)
            } else {
                statement.setInt(6, 1)
            }

            statement.executeUpdate()
            statement.close()
        } catch (e: SQLException) {
            if (e.errorCode == 1062) {
                println(MenuColores.set("[ERROR]", MenuColores.rojo) + " El usuario ya existe.")
            } else {
                println("Error al añadir al usuario:")
                println(MenuColores.set("[${e.errorCode}]", MenuColores.rojo) +  "${e.message}")
            }
        }
        añadirHistorial(historial)
    }

    override fun borrarUsuario(usuario: Usuario) {
        val user = obtenerUsuario(usuario.email)
        if (user != null) {
            val query = "DELETE FROM usuarios WHERE email = ?"

            try {
                val statement = connection.prepareStatement(query)
                statement.setString(1, usuario.email)

                statement.executeUpdate()
                statement.close()
            } catch (e: SQLException) {
                println("Error al eliminar al usuario:")
                println(MenuColores.set("[${e.errorCode}]", MenuColores.rojo) +  "${e.message}")
            }
        } else {
            println(MenuColores.set("[ERROR]", MenuColores.rojo) + " El usuario no existe.")
        }

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
            println("Error al obtener los datos del usuario:")
            println(MenuColores.set("[${e.errorCode}]", MenuColores.rojo) +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return usuario
    }

    override fun modificarPermisos(usuario : Usuario, rol : Roles) {
        val user = obtenerUsuario(usuario.email)
        if (user != null) {
            val query = "UPDATE usuarios SET rol = ? WHERE email = ?"

            try {
                val statement = connection.prepareStatement(query)
                if (rol == Roles.ADMINISTRADOR) {
                    statement.setInt(1, 1)
                } else {
                    statement.setInt(1, 0)
                }
                statement.setString(2, usuario.email)

                statement.executeUpdate()
                statement.close()
                println(MenuColores.ok() + " Permisos modificados con éxito")
            } catch (e: SQLException) {
                println("Error al cambiar el permiso del usuario:")
                println(MenuColores.set("[${e.errorCode}]", MenuColores.rojo) +  "${e.message}")
            }
        } else {
            println(MenuColores.error() + " El usuario no existe.")
        }
    }

    override fun modificarUsuario(usuarioOriginal : Usuario, datosNuevos : Usuario) {
        val user = obtenerUsuario(usuarioOriginal.email)
        if (user != null) {

            val query = "UPDATE usuarios SET nombre = ?, apellidos = ?, edad = ?, email = ?, password = ? WHERE email = ?"

            try {
                val statement = connection.prepareStatement(query)
                statement.setString(1, datosNuevos.nombre)
                statement.setString(2, datosNuevos.apellido)
                statement.setInt(3, datosNuevos.edad)
                statement.setString(4, datosNuevos.email)
                statement.setString(5, datosNuevos.contrasenia)
                statement.setString(6, usuarioOriginal.email)

                statement.executeUpdate()
                statement.close()
                println(MenuColores.ok() + " Usuario modificado con éxito")
            } catch (e: SQLException) {
                println("Error al modificar el usuario:")
                println(MenuColores.set("[${e.errorCode}]", MenuColores.rojo) +  "${e.message}")
            }
        } else {
            println(MenuColores.error() + " El usuario no existe.")
        }
    }

    override fun mostrarUsuarios() {
        println("Usuarios:")
        obtenerUsuarios().forEachIndexed { index, usuario ->
            println("${index + 1}. Nombre: ${usuario.nombre}, Apellidos: ${usuario.apellido}, Edad: ${usuario.edad}, Email: ${usuario.email}, Rol: ${usuario.rol}")
        }
    }

    override fun obtenerUsuarios(): ArrayList<Usuario> {
        val listaUsuarios = ArrayList<Usuario>()

        val query = "SELECT * FROM usuarios"
        val statement = connection.prepareStatement(query)
        val resultSet = statement.executeQuery()

        try {
            while (resultSet.next()) {
                val email = resultSet.getString("email")
                val usuario = obtenerUsuario(email)
                if (usuario != null) {
                    listaUsuarios.add(usuario)
                }
            }
        } catch (e: SQLException) {
            println("Error al obtener los datos de los usuarios:")
            println(MenuColores.set("[${e.errorCode}]", MenuColores.rojo) +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return listaUsuarios
    }


    //HISTORIALES
    override fun añadirHistorial(historial: Historial) {
        val query = "INSERT INTO historial (email, partidasJugadas, partidasGanadas, puntos) VALUES (?, ?, ?, ?)"

        try {
            val statement = connection.prepareStatement(query)
            statement.setString(1, historial.emailJugador)
            statement.setInt(2, historial.partidasJugadas)
            statement.setInt(3, historial.partidasGanadas)
            statement.setInt(4, historial.puntos)

            statement.executeUpdate()
            statement.close()
        } catch (e: SQLException) {
            if (e.errorCode == 1062) {
                println(MenuColores.set("[ERROR]", MenuColores.rojo) + " El historial ya existe.")
            } else {
                println("Error al añadir el historial:")
                println(MenuColores.set("[${e.errorCode}]", MenuColores.rojo) +  "${e.message}")
            }
        }
    }

    override fun borrarHistorial(historial: Historial) {
        val log = obtenerHistorial(historial.emailJugador)
        if (log != null) {
            val query = "DELETE FROM historial WHERE email = ?"

            try {
                val statement = connection.prepareStatement(query)
                statement.setString(1, historial.emailJugador)

                statement.executeUpdate()
                statement.close()
            } catch (e: SQLException) {
                println("Error al eliminar al historial:")
                println(MenuColores.set("[${e.errorCode}]", MenuColores.rojo) +  "${e.message}")
            }
        } else {
            println(MenuColores.set("[ERROR]", MenuColores.rojo) + " El historial no existe.")
        }
    }

    override fun obtenerHistorial(usuario: Usuario): Historial? {
        val sql = "SELECT * FROM historial WHERE email = ?"
        val statement = connection.prepareStatement(sql)
        statement.setString(1, usuario.email)
        val resultSet = statement.executeQuery()

        var historial: Historial? = null

        try {
            if (resultSet.next()) {
                val partidasJugadas = resultSet.getInt("partidasJugadas")
                val partidasGanadas = resultSet.getInt("partidasGanadas")
                val puntos = resultSet.getInt("puntos")

                historial = Historial(usuario.email, partidasJugadas, partidasGanadas, puntos)
            }
        } catch (e: SQLException) {
            println("Error al obtener los datos del historial:")
            println(MenuColores.set("[${e.errorCode}]", MenuColores.rojo) +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return historial
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
            println("Error al obtener los datos del historial:")
            println(MenuColores.set("[${e.errorCode}]", MenuColores.rojo) +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return historial
    }

    override fun modificarHistorial(historialOriginal: Historial, datosNuevos: Historial, esAdmin: Boolean) {
        val historial = obtenerUsuario(historialOriginal.emailJugador)
        if (historial != null) {

            val query = "UPDATE historial SET email = ?, partidasJugadas = ?, partidasGanadas = ?, puntos = ? WHERE email = ?"

            try {
                val statement = connection.prepareStatement(query)
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
                println("Error al modificar el historial:")
                println(MenuColores.set("[${e.errorCode}]", MenuColores.rojo) +  "${e.message}")
            }
        } else {
            println(MenuColores.error() + " El historial no existe.")
        }
    }

    override fun obtenerHistoriales(): ArrayList<Historial> {
        val listaHistorial = ArrayList<Historial>()

        val query = "SELECT * FROM historial"
        val statement = connection.prepareStatement(query)
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
            println("Error al obtener los datos del historial:")
            println(MenuColores.set("[${e.errorCode}]", MenuColores.rojo) +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return listaHistorial
    }
}
