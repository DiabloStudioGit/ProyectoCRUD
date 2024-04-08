import UI.MenuLogin
import Gestion.GestionarUsuarios
import Inputs.InputsMenus
import Inputs.InputsRegistro
import UI.MenuAdmin
import UI.MenuJuego
import Usuario.Roles
import Usuario.Usuario

fun main() {
    val gestionarUsuarios = GestionarUsuarios()

    //Añade un administrador para Debug
    if (gestionarUsuarios.obtenerUsuario("admin@test.es") == null) {
        gestionarUsuarios.añadirUsuario(Usuario("Admin", "Root", 0, "admin@test.es", "12345", Roles.ADMINISTRADOR))
    }

    val menuLogin = MenuLogin(gestionarUsuarios)

    do {
        menuLogin.imprimirOpciones()
        val opcion = InputsMenus.seleccionarOpcionMenu(3)
        when (opcion) {
            1 -> {
                val usuario = menuLogin.iniciarSesion()
                if (usuario != null){
                    val menuJuego = MenuJuego(usuario)

                    if (usuario.rol == Roles.ADMINISTRADOR) {
                        //Si el usuario es Administrador, pide a donde ir
                        when (menuLogin.pedirAdmin()) {
                            1 -> {
                                menuJuego.juego()
                            }
                            2 -> {
                                val menuAdmin = MenuAdmin(gestionarUsuarios)
                                do {
                                    val continuar = menuAdmin.menuAdmin()
                                }while (continuar)
                            }
                        }
                    } else {
                        //Si el usuario es Estandar, va al juego
                        do {
                            val continuar = menuJuego.juego()
                        }while (continuar)
                    }
                }
            }
            2 -> {
                gestionarUsuarios.añadirUsuario(Usuario(InputsRegistro.introducirNombre(), InputsRegistro.introducirApellidos(), InputsRegistro.introducirEdad(), InputsRegistro.introducirEmail(), InputsRegistro.introducirContrasenia(), Roles.ESTANDAR))
            }
        }

    } while (opcion != 3)
}
