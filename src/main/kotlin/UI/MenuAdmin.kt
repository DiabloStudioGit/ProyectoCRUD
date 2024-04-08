package UI

import Gestion.GestionarUsuarios
import Inputs.InputsMenus
import Inputs.InputsRegistro
import Usuario.Usuario

class MenuAdmin {
    var gestUsuarios = GestionarUsuarios()

    fun mostrarMenu(): Int {

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

        return InputsMenus.seleccionarOpcionMenu(7)
    }

    fun anadirUsuario(){
        gestUsuarios.añadirUsuario(Usuario(InputsRegistro.introducirNombre(), InputsRegistro.introducirApellidos(), InputsRegistro.introducirEdad(), InputsRegistro.introducirEmail(), InputsRegistro.introducirContrasenia(), InputsRegistro.introducirRol()))

    }
    fun mostrarUsuarios(){
        gestUsuarios.mostrarUsuarios()
    }
    fun buscarUsuario(){
        println(gestUsuarios.obtenerUsuario(InputsRegistro.introducirEmail()))
    }
    fun borrarUsuario(){
        val usuario = gestUsuarios.obtenerUsuario(InputsRegistro.introducirEmail())
        if (usuario == null) {
            println("[ERROR] Usuario no encontrado")
        } else {
            gestUsuarios.borrarUsuario(usuario)
        }
    }
    fun modificarUsuario(){
        val usuario = gestUsuarios.obtenerUsuario(InputsRegistro.introducirEmail())
        if (usuario == null) {
            println("[ERROR] Usuario no encontrado")
        } else {
            val modificaciones = usuario

            println("Usuario seleccionado: ${usuario.nombre}")
            println("@====¿Qué Desea Modificar?====@")
            println("|   [1]  Nombre               |")
            println("|   [2]  Apellido             |")
            println("|   [3]  Edad                 |")
            println("|   [4]  Email                |")
            println("|   [5]  Contraseña           |")
            println("|   [6]  Salir                |")
            println("@=============================@")

            val opcion = InputsMenus.seleccionarOpcionMenu(6)
            when (opcion) {
                1 -> modificaciones.nombre = InputsRegistro.introducirNombre()
                2 -> modificaciones.apellido = InputsRegistro.introducirApellidos()
                3 -> modificaciones.edad = InputsRegistro.introducirEdad()
                4 -> modificaciones.email = InputsRegistro.introducirEmail()
                5 -> modificaciones.contrasenia = InputsRegistro.introducirContrasenia()
                6 -> {}
            }
            if (opcion != 6) {
                gestUsuarios.modificarUsuario(usuario, modificaciones)
            }
        }
    }
    fun cambiarPermisosUsuario(){
        val usuario = gestUsuarios.obtenerUsuario(InputsRegistro.introducirEmail())
        if (usuario == null) {
            println("[ERROR] Usuario no encontrado")
        } else {
            gestUsuarios.modificarPermisos(usuario, InputsRegistro.introducirRol())
        }
    }
}
