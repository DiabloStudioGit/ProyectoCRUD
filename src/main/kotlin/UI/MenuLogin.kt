package UI

import Gestion.IGestorUsuarios
import Inputs.InputsLogin
import Inputs.InputsMenus
import Usuario.Usuario

class MenuLogin {

    val gestionarUsuarios : IGestorUsuarios
    val inputsLogin = InputsLogin()

    constructor(gestor : IGestorUsuarios) {
        this.gestionarUsuarios = gestor
    }

    fun imprimirOpciones() {
        println("@======¿Que Desea Hacer?======@")
        println("|                             |")
        println("|     [1]  Iniciar Sesion     |")
        println("|       [2]  Registrarse      |")
        println("|          [3]  Salir         |")
        println("|                             |")
        println("@=============================@")
    }

    fun iniciarSesion() : Usuario? {
        val correo = inputsLogin.ingresoEmail()
        val usuario = gestionarUsuarios.obtenerUsuario(correo)
        if (usuario == null) {
            println(MenuColores.error() + " No hay ningun usuario registrado con ese correo")
        }else if (inputsLogin.ingresoContrasenia(usuario)) {
            println(MenuColores.ok() + " Iniciando sesión...")
        } else {
            println(MenuColores.error() + " Error al iniciar sesión")
        }
        return usuario
    }

    fun pedirAdmin(): Int {

        println("Usuario Administrador")
        println("@======¿Que Desea Hacer?======@")
        println("|                             |")
        println("|         [1]  Jugar          |")
        println("|     [2]  Administracion     |")
        println("|                             |")
        println("@=============================@")

        return InputsMenus.seleccionarOpcionMenu(2)
    }

    companion object {
        fun bienvenida(){
            println()
            println("[Proyecto" + MenuColores.random("CRUD") + "]")
            println(MenuColores.set((" ██████╗   ██████╗    ██╗   ██╗   ██████╗    "), MenuColores.rojo))
            println(MenuColores.set(("██╔════╝   ██╔══██╗   ██║   ██║   ██╔══██╗   "), MenuColores.amarillo))
            println(MenuColores.set(("██║        ██████╔╝   ██║   ██║   ██║  ██║   "), MenuColores.verde))
            println(MenuColores.set(("██║        ██╔══██╗   ██║   ██║   ██║  ██║   "), MenuColores.cian))
            println(MenuColores.set(("╚██████╗██╗██║  ██║██╗╚██████╔╝██╗██████╔╝██╗"), MenuColores.azul))
            println(MenuColores.set((" ╚═════╝╚═╝╚═╝  ╚═╝╚═╝ ╚═════╝ ╚═╝╚═════╝ ╚═╝"), MenuColores.magenta))
            println(".:" + MenuColores.set("Carlos Canal", MenuColores.rojo) + " / " + MenuColores.set("Miguel León", MenuColores.amarillo) + " / " + MenuColores.set("Manuel Santos", MenuColores.verde) + " / " + MenuColores.set("Jose García", MenuColores.cian) + " / " + MenuColores.set("David Zamora", MenuColores.magenta) + ":.")
        }

        fun menuGestor(){
            println("@==Elija un Sistema de datos==@")
            println("|                             |")
            println("|     [1]  Sistema Ficheros   |")
            println("|       [2]  Base de Datos    |")
            println("|                             |")
            println("@=============================@")
        }
    }
}