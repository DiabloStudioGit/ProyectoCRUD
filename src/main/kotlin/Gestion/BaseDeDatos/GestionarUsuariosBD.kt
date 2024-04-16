package Gestion.BaseDeDatos

import Gestion.*
import UI.MenuColores
import Data.Usuario.Roles
import Data.Usuario.Usuario
import java.sql.SQLException


class GestionarUsuariosBD : IGestorUsuarios {
    /**
     * Añade un nuevo usuario a la base de datos.
     *
     * @param usuario El objeto Usuario que se va a añadir a la base de datos.
     */
    override fun añadirUsuario(usuario : Usuario) {
        val query = "INSERT INTO usuarios (nombre, apellidos, edad, email, password, rol) VALUES (?, ?, ?, ?, ?, ?)"

        try {
            val statement = ConexionBD.connection!!.prepareStatement(query)
            statement.setString(1, usuario.nombre)
            statement.setString(2, usuario.apellido)
            statement.setInt(3, usuario.edad)
            statement.setString(4, usuario.email)
            statement.setString(5, usuario.contrasenia)
            when (usuario.rol) {
                Roles.ADMINISTRADOR -> {
                    statement.setInt(6,1)
                }
                Roles.STAFF -> {
                    statement.setInt(6, 2)
                }
                else -> {
                    statement.setInt(6, 0)
                }
            }
            val logRegistro = Log(usuario.email, Gestores.fechaActual(), "Usuario creado")
            Gestores.gestorLogs.añadirLog(logRegistro)

            statement.executeUpdate()
            statement.close()
        } catch (e: SQLException) {
            if (e.errorCode == 1062) {
                println(MenuColores.error() + " El usuario ya existe.")
            } else {
                println(MenuColores.error() + " Error al añadir al usuario:")
                println(MenuColores.amarillo("[${e.errorCode}]") +  "${e.message}")
            }
        }
    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param usuario El objeto Usuario que se va a eliminar de la base de datos.
     */
    override fun borrarUsuario(usuario: Usuario) {
        val user = obtenerUsuario(usuario.email)
        if (user != null) {
            val query = "DELETE FROM usuarios WHERE email = ?"

            try {
                val statement = ConexionBD.connection!!.prepareStatement(query)
                statement.setString(1, usuario.email)

                val logEliminar = Log(usuario.email, Gestores.fechaActual(), "Usuario eliminado")
                Gestores.gestorLogs.añadirLog(logEliminar)

                statement.executeUpdate()
                statement.close()

                val historial = Gestores.gestorHistoriales.obtenerHistorial(usuario, false)
                if (historial != null) {
                    Gestores.gestorHistoriales.borrarHistorial(historial)
                }
            } catch (e: SQLException) {
                println(MenuColores.error() + " Error al " + MenuColores.rojo("eliminar") + " el usuario:")
                println(MenuColores.amarillo("[${e.errorCode}]") +  "${e.message}")
            }
        } else {
            println(MenuColores.error() + " El usuario no existe.")
        }

    }

    /**
     * Obtiene un usuario de la base de datos según su dirección de correo electrónico.
     *
     * @param email La dirección de correo electrónico del usuario que se desea obtener.
     * @return El objeto Usuario correspondiente al correo electrónico proporcionado, o null si no se encuentra en la base de datos.
     */
    override fun obtenerUsuario(email : String) : Usuario? {
        val statement = ConexionBD.connection!!.prepareStatement("SELECT * FROM usuarios WHERE email = ?")
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
                } else if (resultSet.getInt("rol") == 2){
                    rol = Roles.STAFF
                } else {
                    rol = Roles.ESTANDAR
                }

                usuario = Usuario(nombre, apellidos, edad, email, contrasenia, rol)
            }
        } catch (e: SQLException) {
            println(MenuColores.error() + " Error al obtener los datos del usuario:")
            println(MenuColores.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return usuario
    }

    /**
     * Modifica los permisos de un usuario en la base de datos.
     *
     * @param usuario El objeto Usuario cuyos permisos se van a modificar.
     * @param rol El nuevo rol que se asignará al usuario.
     */
    override fun modificarPermisos(usuario : Usuario, rol : Roles) {
        val user = obtenerUsuario(usuario.email)
        if (user != null) {
            val query = "UPDATE usuarios SET rol = ? WHERE email = ?"

            try {
                val statement = ConexionBD.connection!!.prepareStatement(query)
                when (rol) {
                    Roles.ADMINISTRADOR -> {
                        statement.setInt(1, 1)
                    }
                    Roles.STAFF -> {
                        statement.setInt(1,2)
                    }
                    else -> {
                        statement.setInt(1, 0)
                    }
                }
                statement.setString(2, usuario.email)

                val logPermiso = Log(usuario.email, Gestores.fechaActual(), "Permisos del usuario modificados.")
                Gestores.gestorLogs.añadirLog(logPermiso)

                statement.executeUpdate()
                statement.close()
                println(MenuColores.ok() + " Permisos modificados con éxito")
            } catch (e: SQLException) {
                println(MenuColores.error() + " Error al cambiar el permiso del usuario:")
                println(MenuColores.amarillo("[${e.errorCode}]") +  "${e.message}")
            }
        } else {
            println(MenuColores.error() + " El usuario no existe.")
        }
    }

    /**
     * Modifica los datos de un usuario en la base de datos.
     *
     * @param usuarioOriginal El objeto Usuario cuyos datos se van a modificar.
     * @param datosNuevos El objeto Usuario con los nuevos datos que se van a asignar al usuario.
     */
    override fun modificarUsuario(usuarioOriginal: Usuario, datosNuevos: Usuario) {
        val user = obtenerUsuario(usuarioOriginal.email)
        if (user != null) {
            val query = "UPDATE usuarios SET nombre = ?, apellidos = ?, edad = ?, email = ?, password = ? WHERE email = ?"
            try {
                val statement = ConexionBD.connection!!.prepareStatement(query)
                statement.setString(1, datosNuevos.nombre)
                statement.setString(2, datosNuevos.apellido)
                statement.setInt(3, datosNuevos.edad)
                statement.setString(4, datosNuevos.email)
                statement.setString(5, datosNuevos.contrasenia)
                statement.setString(6, usuarioOriginal.email)

                var cambios = ""
                if (datosNuevos.nombre != usuarioOriginal.nombre) {
                    cambios = "Nombre: ${usuarioOriginal.nombre} -> ${datosNuevos.nombre}"
                }
                if (datosNuevos.apellido != usuarioOriginal.apellido) {
                    cambios = "Apellido: ${usuarioOriginal.apellido} -> ${datosNuevos.apellido}"
                }
                if (datosNuevos.edad != usuarioOriginal.edad) {
                    cambios = "Edad: ${usuarioOriginal.edad} -> ${datosNuevos.edad}"
                }
                if (datosNuevos.email != usuarioOriginal.email) {
                    cambios = "Email: ${usuarioOriginal.email} -> ${datosNuevos.email}"
                    Gestores.gestorLogs.modificarLog(usuarioOriginal.email, datosNuevos.email)
                }
                if (datosNuevos.contrasenia != usuarioOriginal.contrasenia) {
                    cambios = "Contraseña"
                }

                val logModificacion = Log(datosNuevos.email, Gestores.fechaActual(), "Se ha modificado el campo $cambios")
                Gestores.gestorLogs.añadirLog(logModificacion)

                statement.executeUpdate()
                statement.close()
                println(MenuColores.ok() + " Usuario " + MenuColores.magenta(datosNuevos.nombre) + " modificado con éxito")
            } catch (e: SQLException) {
                println(MenuColores.error() + " Error al modificar el usuario:")
                println(MenuColores.amarillo("[${e.errorCode}]") + "${e.message}")
            }
        } else {
            println(MenuColores.error() + " El usuario no existe.")
        }
    }

    /**
     * Muestra todos los usuarios almacenados en la base de datos.
     * Imprime los detalles de cada usuario en la consola, incluyendo nombre, apellidos, edad, email y rol.
     */
    override fun mostrarUsuarios() {
        println("Usuarios:")
        obtenerUsuarios().forEachIndexed { index, usuario ->
            println("${index + 1}. Nombre: ${usuario.nombre}, Apellidos: ${usuario.apellido}, Edad: ${usuario.edad}, Email: ${usuario.email}, Rol: ${usuario.rol}")
        }
    }

    /**
     * Obtiene todos los usuarios almacenados en la base de datos.
     *
     * @return ArrayList de objetos Usuario que representan a todos los usuarios almacenados en la base de datos.
     */
    override fun obtenerUsuarios(): ArrayList<Usuario> {
        val listaUsuarios = ArrayList<Usuario>()

        val query = "SELECT * FROM usuarios"
        val statement = ConexionBD.connection!!.prepareStatement(query)
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
            println(MenuColores.error() + " Error al obtener los datos de los usuarios:")
            println(MenuColores.amarillo("[${e.errorCode}]") +  "${e.message}")
        } finally {
            resultSet.close()
            statement.close()
        }

        return listaUsuarios
    }
}
