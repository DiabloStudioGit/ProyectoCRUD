package UI

import Gestion.BaseDeDatos.GestionarBaseDatos
import Gestion.Fichero.GestionarHistoriales
import Gestion.Gestor
import Gestion.IGestorHistoriales
import Gestion.IGestorUsuarios
import Inputs.InputsMenus
import Inputs.InputsRegistro
import Usuario.Usuario

class MenuAdmin {
    val gestUsuarios : IGestorUsuarios

    constructor(gestor : IGestorUsuarios) {
        this.gestUsuarios = gestor
    }

    fun mostrarMenu(): Int {

        println("@======¿Qué Desea Hacer?======@")
        println("|                             |")
        println("|   [1]  Añadir Usuario       |")
        println("|   [2]  Mostrar Usuarios     |")
        println("|   [3]  Buscar Usuario       |")
        println("|   [4]  Borrar Usuario       |")
        println("|   [5]  Modificar Usuario    |")
        println("|   [6]  Cambiar Permisos     |")
        println("|   [7]  Salir                |")
        println("|                             |")
        println("@=============================@")

        return InputsMenus.seleccionarOpcionMenu(7)
    }

    fun menuAdmin(): Boolean{
        var opcion = true

        when (mostrarMenu()) {
            1 -> anadirUsuario()
            2 -> mostrarUsuarios()
            3 -> buscarUsuario()
            4 -> borrarUsuario()
            5 -> modificarUsuario()
            6 -> cambiarPermisosUsuario()
            7 -> opcion = false
        }
        return opcion
    }

    fun anadirUsuario(){
        gestUsuarios.añadirUsuario(Usuario(InputsRegistro.introducirNombre(), InputsRegistro.introducirApellidos(), InputsRegistro.introducirEdad(), InputsRegistro.introducirEmail(), InputsRegistro.introducirContrasenia(), InputsRegistro.introducirRol()))

    }
    fun mostrarUsuarios(){
        gestUsuarios.mostrarUsuarios()
    }
    fun buscarUsuario(){
        println(gestUsuarios.obtenerUsuario(InputsRegistro.introducirEmail()))
    }
    fun borrarUsuario(){
        val usuario = gestUsuarios.obtenerUsuario(InputsRegistro.introducirEmail())
        if (usuario == null) {
            println("[ERROR] Usuario no encontrado")
        } else {
            gestUsuarios.borrarUsuario(usuario)
        }
    }
    fun modificarUsuario(){
        val usuario = gestUsuarios.obtenerUsuario(InputsRegistro.introducirEmail())
        if (usuario == null) {
            println(MenuColores.error() + " Usuario no encontrado")
        } else {
            val modificaciones = usuario

            println("Usuario seleccionado: ${usuario.nombre}")
            println("@====¿Qué Desea Modificar?====@")
            println("|   [1]  Nombre               |")
            println("|   [2]  Apellido             |")
            println("|   [3]  Edad                 |")
            println("|   [4]  Email                |")
            println("|   [5]  Contraseña           |")
            println("|   [6]  Salir                |")
            println("@=============================@")

            val opcion = InputsMenus.seleccionarOpcionMenu(6)
            when (opcion) {
                1 -> modificaciones.nombre = InputsRegistro.introducirNombre()
                2 -> modificaciones.apellido = InputsRegistro.introducirApellidos()
                3 -> modificaciones.edad = InputsRegistro.introducirEdad()
                4 -> {
                    val emailNuevo = InputsRegistro.introducirEmail()
                    val gestionarHistorial : IGestorHistoriales
                    if (Gestor.eleccion) {
                        gestionarHistorial = GestionarBaseDatos()
                    } else {
                        gestionarHistorial = GestionarHistoriales()
                    }
                    val historial = gestionarHistorial.obtenerHistorial(usuario.email)
                    val historialNuevo = historial
                    historialNuevo!!.emailJugador = emailNuevo
                    gestionarHistorial.modificarHistorial(historial, historialNuevo, true)
                    modificaciones.email = emailNuevo
                }
                5 -> modificaciones.contrasenia = InputsRegistro.introducirContrasenia()
                6 -> {}
            }
            if (opcion != 6) {
                gestUsuarios.modificarUsuario(usuario, modificaciones)
            }
        }
    }
    fun cambiarPermisosUsuario(){
        val usuario = gestUsuarios.obtenerUsuario(InputsRegistro.introducirEmail())
        if (usuario == null) {
            println("[ERROR] Usuario no encontrado")
        } else {
            gestUsuarios.modificarPermisos(usuario, InputsRegistro.introducirRol())
        }
    }
}
