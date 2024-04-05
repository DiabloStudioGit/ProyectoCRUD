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
    val inputsLogin : InputsLogin = InputsLogin()

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

    fun pedirAccion() {

        when (InputsMenus.seleccionarOpcionMenu(3)) {
            1 -> {
                iniciarSesion()
            }
            2 -> {
                gestionarUsuarios.añadirUsuario(Usuario(InputsRegistro.introducirNombre(), InputsRegistro.introducirApellidos(), InputsRegistro.introducirEdad(), InputsRegistro.introducirEmail(), InputsRegistro.introducirContrasenia(), Roles.ESTANDAR))
            }
            3 -> {
                exitProcess(0)
            }
        }
    }

    fun iniciarSesion() : Usuario {
        val correo = inputsLogin.ingresoEmail()
        val usuario = gestionarUsuarios.obtenerUsuario(correo)
        if (usuario == null) {
            println("[ERROR] No hay ningun usuario registrado con ese correo")
        }else if (InputsLogin.ingresoContrasenia(usuario)) {
            return Usuario
        }
    }
}