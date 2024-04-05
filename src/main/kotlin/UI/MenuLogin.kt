package UI

import gestion.GestionarUsuarios
import menusBackend.InputsLogin
import menusBackend.InputsMenus
import usuario.Usuario

class MenuLogin {

    val gestionarUsuarios : GestionarUsuarios = GestionarUsuarios()

    constructor()

    fun imprimirOpciones() {
        println("@======¿Que Desea Hacer?======@")
        println("|                             |")
        println("|     [1]  Iniciar Sesion     |")
        println("|       [2]  Registrase       |")
        println("|          [3]  Salir         |")
        println("|                             |")
        println("@=============================@")
    }

    fun pedirAccion() {

        when (InputsMenus.seleccionarOpcionMenu(3)) {
            1 -> {

            }
        }
    }

    fun iniciarSesion() : Usuario {
        val correo = InputsLogin.ingresoEmail()
        val usuario = gestionarUsuarios.obtenerUsuario(correo)
        if (usuario == null) {
            println("[ERROR] No hay ningun usuario registrado con ese correo")
        }else if (InputsLogin.ingresoContrasenia(usuario)) {

        }
    }
}