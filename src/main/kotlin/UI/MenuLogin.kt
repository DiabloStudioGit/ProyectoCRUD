package UI

import gestion.GestionarUsuarios
import menusBackend.InputsLogin
import menusBackend.InputsMenus
import menusBackend.InputsRegistro
import usuario.Roles
import usuario.Usuario

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

    fun pedirAccion(): Int {
        var opcion = 0

        when (InputsMenus.seleccionarOpcionMenu(3)) {
            1 -> {
                opcion = 1
            }
            2 -> {
                opcion = 2
            }
            3 -> {
                opcion = 3
            }
        }
        return opcion
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
                opcion = 1
            }
            2 -> {
                opcion = 2
            }
        }
        return opcion
    }

    companion object {
        fun menuJuego(usuario: Usuario): Int {
            var opcion = 0

            println("@======¿Que Desea Hacer?======@")
            println("|                             |")
            println("|   Hola [${usuario.nombre}]  |")
            println("|     [1]  Jugar              |")
            println("|       [2]  Puntuacion       |")
            println("|          [3]  Cerrar Sesion |")
            println("|                             |")
            println("@=============================@")

            when (InputsMenus.seleccionarOpcionMenu(3)) {
                1 -> {
                    opcion = 1
                }
                2 -> {
                    opcion = 2
                }
                3 -> {
                    opcion = 3
                }
            }
            return opcion
        }
    }
}