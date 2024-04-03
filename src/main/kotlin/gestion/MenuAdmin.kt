package gestion

class MenuAdmin {
    companion object {
        fun mostrarMenu() {

            println("**************************************")
            println("** 1. Añadir usuario                **")
            println("** 2. Mostrar usuarios              **")
            println("** 3. Buscar usuario por email      **")
            println("** 4. Borrar usuario                **")
            println("** 5. Modificar usuario             **")
            println("** 6. Cambiar permisos de usuario   **")
            println("** 7. Salir                         **")
            println("**************************************")

            print("Elige una opción: ")
        }
    }
}
        /*
                when (opcion) {
                    1 -> anadirUsuario()
                    2 -> mostrarUsuarios()
                    3 -> buscarUsuarioPorEmail()
                    4 -> borrarUsuario()
                    5 -> modificarUsuario()
                    6 -> cambiarPermisosUsuario()
                    7 -> println("¡Hasta luego, administrador!")
                    else -> println("Opción no válida. Inténtalo de nuevo.")
                }
            } while (opcion != 7)
        }

*/
