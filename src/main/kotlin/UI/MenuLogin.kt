package UI

import gestion.GestionarUsuarios
import menusBackend.InputsLogin
import menusBackend.InputsMenus
import menusBackend.InputsRegistro
import usuario.Roles
import usuario.Usuario
import kotlin.system.exitProcess

class MenuLogin {

    val gestionarUsuarios : GestionarUsuarios = GestionarUsuarios()
    val inputsLogin = InputsLogin()

    constructor()

    fun imprimirOpciones() {
        println("@======¿Que Desea Hacer?======@")
        println("|                             |")
        println("|     [1]  Iniciar Sesion     |")
        println("|       [2]  Registrarse      |")
        println("|          [3]  Salir         |")
        println("|                             |")
        println("@=============================@")
    }

    fun pedirAccion(): Boolean {
        var exit = false

        when (InputsMenus.seleccionarOpcionMenu(3)) {
            1 -> {
                var usuario = iniciarSesion()
                if (usuario != null){
                    if (usuario.rol == Roles.ADMINISTRADOR) {
                        //Si el usuario es Administrador, pide a donde ir
                        pedirMenu()
                    } else {
                        //Si el usuario es estandar, ir al juego

                    }
                }
            }
            2 -> {
                gestionarUsuarios.añadirUsuario(Usuario(InputsRegistro.introducirNombre(), InputsRegistro.introducirApellidos(), InputsRegistro.introducirEdad(), InputsRegistro.introducirEmail(), InputsRegistro.introducirContrasenia(), Roles.ESTANDAR))
            }
            3 -> {
                exit = InputsMenus.salirMenu()
            }
        }
        return exit
    }

    fun iniciarSesion() : Usuario? {
        val correo = inputsLogin.ingresoEmail()
        val usuario = gestionarUsuarios.obtenerUsuario(correo)
        if (usuario == null) {
            println("[ERROR] No hay ningun usuario registrado con ese correo")
        }else if (inputsLogin.ingresoContrasenia(usuario)) {
            println("[OK] Iniciando sesión...")
        } else {
            println("[ERROR] Error al iniciar sesión")
        }
        return usuario
    }

    fun pedirMenu() {
        var opcion = 0
        println("Usuario Administrador")
        println("@======¿Que Desea Hacer?======@")
        println("|                             |")
        println("|     [1]  Jugar              |")
        println("|       [2]  Administracion   |")
        println("|                             |")
        println("@=============================@")

        when (InputsMenus.seleccionarOpcionMenu(2)) {
            1 -> {
                // Aqui iría al juego
                println("Juego")
            }
            2 -> {
                // Aquí iria al menu Admin
                println("Admin")
            }
        }
    }
}