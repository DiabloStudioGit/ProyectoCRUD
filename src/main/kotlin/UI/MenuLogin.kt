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
        println(MenuColores.cian("@======") + "¿Que Desea Hacer?" + MenuColores.cian("======@"))
        println(MenuColores.cian("|                             |"))
        println(MenuColores.cian("|     ") + MenuColores.amarillo("[1]") + "  Iniciar Sesion" + MenuColores.cian("     |"))
        println(MenuColores.cian("|       ") + MenuColores.amarillo("[2]") + "  Registrarse" + MenuColores.cian("      |"))
        println(MenuColores.cian("|          ") + MenuColores.amarillo("[3]") + "  Salir" + MenuColores.cian("         |"))
        println(MenuColores.cian("|                             |"))
        println(MenuColores.cian("@=============================@"))
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
        println(MenuColores.amarillo("@======") + "¿Que Desea Hacer?" + MenuColores.amarillo("======@"))
        println(MenuColores.amarillo("|                             |"))
        println(MenuColores.amarillo("|         ") + MenuColores.magenta("[1]") + "  Jugar" + MenuColores.amarillo("          |"))
        println(MenuColores.amarillo("|     ") + MenuColores.magenta("[2]") + "  Administracion" + MenuColores.amarillo("     |"))
        println(MenuColores.amarillo("|                             |"))
        println(MenuColores.amarillo("@=============================@"))

        return InputsMenus.seleccionarOpcionMenu(2)
    }

    companion object {
        fun bienvenida(){
            println()
            println("[Proyecto" + MenuColores.random("CRUD") + "]")
            println(MenuColores.rojo((" ██████╗   ██████╗    ██╗   ██╗   ██████╗    ")))
            println(MenuColores.amarillo(("██╔════╝   ██╔══██╗   ██║   ██║   ██╔══██╗   ")))
            println(MenuColores.verde(("██║        ██████╔╝   ██║   ██║   ██║  ██║   ")))
            println(MenuColores.cian(("██║        ██╔══██╗   ██║   ██║   ██║  ██║   ")))
            println(MenuColores.azul(("╚██████╗██╗██║  ██║██╗╚██████╔╝██╗██████╔╝██╗")))
            println(MenuColores.magenta((" ╚═════╝╚═╝╚═╝  ╚═╝╚═╝ ╚═════╝ ╚═╝╚═════╝ ╚═╝")))
            println(".:" + MenuColores.rojo("Carlos Canal") + " / " + MenuColores.amarillo("Miguel León") + " / " + MenuColores.verde("Manuel Santos") + " / " + MenuColores.cian("Jose García") + " / " + MenuColores.magenta("David Zamora") + ":.")
        }

        fun menuGestor(){
            println(MenuColores.magenta("@==") + "Elija un " + MenuColores.azul("Sistema de datos") + MenuColores.magenta("==@"))
            println(MenuColores.magenta("|                             |"))
            println(MenuColores.magenta("|     ") + MenuColores.cian("[1]") +"   Sistema Ficheros" + MenuColores.magenta("  |"))
            println(MenuColores.magenta("|       ") + MenuColores.cian("[2]") + "  Base de Datos" + MenuColores.magenta("    |"))
            println(MenuColores.magenta("|                             |"))
            println(MenuColores.magenta("@=============================@"))
        }
    }
}