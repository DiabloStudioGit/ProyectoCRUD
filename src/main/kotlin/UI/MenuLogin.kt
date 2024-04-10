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
        println(MenuColores.set("@======", MenuColores.cian) + "¿Que Desea Hacer?" + MenuColores.set("======@",MenuColores.cian))
        println(MenuColores.set("|                             |", MenuColores.cian))
        println(MenuColores.set("|     ", MenuColores.cian) + MenuColores.set("[1]", MenuColores.amarillo) + "  Iniciar Sesion" + MenuColores.set("     |", MenuColores.cian))
        println(MenuColores.set("|       ", MenuColores.cian) + MenuColores.set("[2]", MenuColores.amarillo) + "  Registrarse" + MenuColores.set("      |", MenuColores.cian))
        println(MenuColores.set("|          ", MenuColores.cian) + MenuColores.set("[3]", MenuColores.amarillo) + "  Salir" + MenuColores.set("         |", MenuColores.cian))
        println(MenuColores.set("|                             |", MenuColores.cian))
        println(MenuColores.set("@=============================@", MenuColores.cian))
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

    fun pedirAdmin(correoAdmin: String): Int {

        println("Administrador: " + MenuColores.random(correoAdmin))
        println(MenuColores.set("@======", MenuColores.amarillo) + "¿Que Desea Hacer?" + MenuColores.set("======@", MenuColores.amarillo))
        println(MenuColores.set("|                             |", MenuColores.amarillo))
        println(MenuColores.set("|         ", MenuColores.amarillo) + MenuColores.set("[1]", MenuColores.magenta) + "  Jugar" + MenuColores.set("          |", MenuColores.amarillo))
        println(MenuColores.set("|     ", MenuColores.amarillo) + MenuColores.set("[2]", MenuColores.magenta) + "  Administracion" + MenuColores.set("     |", MenuColores.amarillo))
        println(MenuColores.set("|                             |", MenuColores.amarillo))
        println(MenuColores.set("@=============================@", MenuColores.amarillo))

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
            println(MenuColores.set("@==", MenuColores.magenta) + "Elija un " + MenuColores.set("Sistema de datos", MenuColores.azul) + MenuColores.set("==@", MenuColores.magenta))
            println(MenuColores.set("|                             |", MenuColores.magenta))
            println(MenuColores.set("|     ", MenuColores.magenta) + MenuColores.set("[1]", MenuColores.cian) +"   Sistema Ficheros" + MenuColores.set("  |", MenuColores.magenta))
            println(MenuColores.set("|       ", MenuColores.magenta) + MenuColores.set("[2]", MenuColores.cian) + "  Base de Datos" + MenuColores.set("    |", MenuColores.magenta))
            println(MenuColores.set("|                             |", MenuColores.magenta))
            println(MenuColores.set("@=============================@", MenuColores.magenta))
        }
    }
}