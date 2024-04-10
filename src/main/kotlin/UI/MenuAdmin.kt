package UI

import Gestion.BaseDeDatos.GestionarBaseDatos
import Gestion.Fichero.GestionarHistoriales
import Gestion.Gestor
import Gestion.IGestorHistoriales
import Gestion.IGestorUsuarios
import Inputs.InputsMenus
import Inputs.InputsRegistro
import Usuario.Usuario
import java.awt.Menu

class MenuAdmin {
    val gestUsuarios : IGestorUsuarios

    constructor(gestor : IGestorUsuarios) {
        this.gestUsuarios = gestor
    }

    fun mostrarMenu(): Int {

        println(MenuColores.set("@======", MenuColores.azul) + "¿Qué Desea Hacer?" + MenuColores.set("======@", MenuColores.azul))
        println(MenuColores.set("|                             |", MenuColores.azul))
        println(MenuColores.set("|   ", MenuColores.azul) + MenuColores.set("[1]", MenuColores.magenta) + "  Añadir Usuario" + MenuColores.set("       |", MenuColores.azul))
        println(MenuColores.set("|   ", MenuColores.azul) + MenuColores.set("[2]", MenuColores.magenta) + "  Mostrar Usuarios" + MenuColores.set("     |", MenuColores.azul))
        println(MenuColores.set("|   ", MenuColores.azul) + MenuColores.set("[3]", MenuColores.magenta) + "  Buscar Usuario" + MenuColores.set("       |", MenuColores.azul))
        println(MenuColores.set("|   ", MenuColores.azul) + MenuColores.set("[4]", MenuColores.magenta) + "  Borrar Usuario" + MenuColores.set("       |", MenuColores.azul))
        println(MenuColores.set("|   ", MenuColores.azul) + MenuColores.set("[5]", MenuColores.magenta) + "  Modificar Usuario" + MenuColores.set("    |", MenuColores.azul))
        println(MenuColores.set("|   ", MenuColores.azul) + MenuColores.set("[6]", MenuColores.magenta) + "  Cambiar Permisos" + MenuColores.set("     |", MenuColores.azul))
        println(MenuColores.set("|   ", MenuColores.azul) + MenuColores.set("[7]", MenuColores.magenta) + "  Salir" + MenuColores.set("                |", MenuColores.azul))
        println(MenuColores.set("|                             |", MenuColores.azul))
        println(MenuColores.set("@=============================@", MenuColores.azul))

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
            println(MenuColores.error() + " Usuario no encontrado")
        } else {
            gestUsuarios.borrarUsuario(usuario)
        }
    }
    fun modificarUsuario(){
        val usuario = gestUsuarios.obtenerUsuario(InputsRegistro.introducirEmail())
        if (usuario == null) {
            println(MenuColores.error() + " Usuario no encontrado")
        } else {
            val modificaciones = usuario.copy()

            println("Usuario seleccionado: ${usuario.nombre}")
            println(MenuColores.set("@====", MenuColores.rojo) + "¿Qué Desea Modificar?" + MenuColores.set("====@", MenuColores.rojo))
            println(MenuColores.set("|   ", MenuColores.rojo) + MenuColores.set("[1]", MenuColores.amarillo) + "  Nombre" + MenuColores.set("               |", MenuColores.rojo))
            println(MenuColores.set("|   ", MenuColores.rojo) + MenuColores.set("[2]", MenuColores.amarillo) + "  Apellido" + MenuColores.set("             |", MenuColores.rojo))
            println(MenuColores.set("|   ", MenuColores.rojo) + MenuColores.set("[3]", MenuColores.amarillo) + "  Edad" + MenuColores.set("                 |", MenuColores.rojo))
            println(MenuColores.set("|   ", MenuColores.rojo) + MenuColores.set("[4]", MenuColores.amarillo) + "  Email" + MenuColores.set("                |", MenuColores.rojo))
            println(MenuColores.set("|   ", MenuColores.rojo) + MenuColores.set("[5]", MenuColores.amarillo) + "  Contraseña" + MenuColores.set("           |", MenuColores.rojo))
            println(MenuColores.set("|   ", MenuColores.rojo) + MenuColores.set("[6]", MenuColores.amarillo) + "  Salir" + MenuColores.set("                |", MenuColores.rojo))
            println(MenuColores.set("@=============================@", MenuColores.rojo))

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
                    val historialNuevo = historial!!.copy()
                    historialNuevo.emailJugador = emailNuevo
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
            println(MenuColores.error() + " Usuario no encontrado")
        } else {
            gestUsuarios.modificarPermisos(usuario, InputsRegistro.introducirRol())
        }
    }
}
