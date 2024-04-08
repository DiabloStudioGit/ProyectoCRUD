package UI

import Gestion.GestionarUsuarios
import Inputs.InputsLogin
import Inputs.InputsMenus
import Usuario.Usuario

class MenuLogin() {

    val gestionarUsuarios : GestionarUsuarios = GestionarUsuarios()
    val inputsLogin = InputsLogin()

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
            println("[ERROR] No hay ningun usuario registrado con ese correo")
        }else if (inputsLogin.ingresoContrasenia(usuario)) {
            println("[OK] Iniciando sesión...")
        } else {
            println("[ERROR] Error al iniciar sesión")
        }
        return usuario
    }

    fun pedirAdmin(): Int {

        println("Usuario Administrador")
        println("@======¿Que Desea Hacer?======@")
        println("|                             |")
        println("|     [1]  Jugar              |")
        println("|       [2]  Administracion   |")
        println("|                             |")
        println("@=============================@")

        return InputsMenus.seleccionarOpcionMenu(2)
    }

    companion object {
        fun menuJuego(usuario: Usuario): Int {
            println("Hola! [${usuario.nombre}]")
            println("@======¿Que Desea Hacer?======@")
            println("|                             |")
            println("|     [1]  Jugar              |")
            println("|       [2]  Puntuacion       |")
            println("|          [3]  Cerrar Sesion |")
            println("|                             |")
            println("@=============================@")

            return InputsMenus.seleccionarOpcionMenu(3)
        }
    }
}