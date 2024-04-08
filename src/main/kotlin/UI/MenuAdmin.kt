package UI

import Inputs.InputsMenus

class MenuAdmin {

    companion object {
        fun mostrarMenu() {

            println("@======¿Qué Desea Hacer?======@")
            println("|                             |")
            println("|   [1]  Añadir Usuario       |")
            println("|   [2]  Mostrar Usuarios     |")
            println("|   [3]  Buscar Usuario       |")
            println("|   [4]  Borrar Usuario       |")
            println("|   [5]  Modificar Usuario    |")
            println("|   [6]  Cambiar Permisos     |")
            println("|   [7]  Salir                |")
            println("|                             |")
            println("@=============================@")


            print("Elige una opción: ")
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

}
