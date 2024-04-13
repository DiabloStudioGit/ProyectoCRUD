package UI

import Gestion.*
import Gestion.BaseDeDatos.GestionarBaseDatos
import Gestion.Fichero.GestionarHistoriales
import Gestion.Fichero.GestionarLogs
import Inputs.InputsMenus
import Inputs.InputsRegistro
import Usuario.Usuario

class MenuAdmin {
    val gestUsuarios : IGestorUsuarios
    var gestLogs : IGestorLogs

    constructor(gestor : IGestorUsuarios) {
        this.gestUsuarios = gestor
        gestLogs = if (Gestor.eleccion) {
            GestionarBaseDatos()
        } else {
            GestionarLogs()
        }
    }

    fun mostrarMenu(): Int {

        println(MenuColores.azul("@======") + "¿Qué Desea Hacer?" + MenuColores.azul("======@"))
        println(MenuColores.azul("|                             |"))
        println(MenuColores.azul("|   ") + MenuColores.magenta("[1]") + "  Añadir Usuario" + MenuColores.azul("       |"))
        println(MenuColores.azul("|   ") + MenuColores.magenta("[2]") + "  Mostrar Usuarios" + MenuColores.azul("     |"))
        println(MenuColores.azul("|   ") + MenuColores.magenta("[3]") + "  Buscar Usuario" + MenuColores.azul("       |"))
        println(MenuColores.azul("|   ") + MenuColores.magenta("[4]") + "  Borrar Usuario" + MenuColores.azul("       |"))
        println(MenuColores.azul("|   ") + MenuColores.magenta("[5]") + "  Modificar Usuario" + MenuColores.azul("    |"))
        println(MenuColores.azul("|   ") + MenuColores.magenta("[6]") + "  Cambiar Permisos" + MenuColores.azul("     |"))
        println(MenuColores.azul("|   ") + MenuColores.magenta("[7]") + "  Salir" + MenuColores.azul("                |"))
        println(MenuColores.azul("|                             |"))
        println(MenuColores.azul("@=============================@"))

        return InputsMenus.seleccionarOpcionMenu(7)
    }

    fun menuAdmin(correo : String) {
        var opcion = true

        while (opcion) {
            when (mostrarMenu()) {
                1 -> anadirUsuario()
                2 -> mostrarUsuarios()
                3 -> buscarUsuario()
                4 -> borrarUsuario()
                5 -> modificarUsuario()
                6 -> cambiarPermisosUsuario()
                7 -> {
                    val logCerrarSesion = Log(correo, Gestor.fechaActual(), "Sesion de usuario cerrada.")
                    gestLogs.añadirLog(logCerrarSesion)
                    opcion = false
                }
            }
        }
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
            println(MenuColores.rojo("@====") + "¿Qué Desea Modificar?" + MenuColores.rojo("====@"))
            println(MenuColores.rojo("|   ") + MenuColores.amarillo("[1]") + "  Nombre" + MenuColores.rojo("               |"))
            println(MenuColores.rojo("|   ") + MenuColores.amarillo("[2]") + "  Apellido" + MenuColores.rojo("             |"))
            println(MenuColores.rojo("|   ") + MenuColores.amarillo("[3]") + "  Edad" + MenuColores.rojo("                 |"))
            println(MenuColores.rojo("|   ") + MenuColores.amarillo("[4]") + "  Email" + MenuColores.rojo("                |"))
            println(MenuColores.rojo("|   ") + MenuColores.amarillo("[5]") + "  Contraseña" + MenuColores.rojo("           |"))
            println(MenuColores.rojo("|   ") + MenuColores.amarillo("[6]") + "  Salir" + MenuColores.rojo("                |"))
            println(MenuColores.rojo("@=============================@"))

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
