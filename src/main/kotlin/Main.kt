import UI.MenuLogin
import Gestion.Gestores
import Inputs.InputsMenus
import Inputs.InputsRegistro
import UI.MenuAdmin
import UI.MenuColores
import UI.MenuJuego
import Data.Usuario.Roles
import Data.Usuario.Usuario
import java.sql.SQLException

fun main() {
    //Imprimir eleccion entre bbdd o fichero

    MenuLogin.bienvenida()
    MenuLogin.menuGestor()
    val gestor = InputsMenus.seleccionarOpcionMenu(2)
    when (gestor) {
        1 -> {
            println("Se va a proceder con el " + MenuColores.azul("sistema de ficheros."))
            Gestores.establecerFicheros()
        }
        else -> {
            try{
                Gestores.establecerBaseDeDatos()
                println("Se va a proceder con el sistema de " + MenuColores.azul("Base de Datos."))
            }catch (ex : SQLException) {
                println(MenuColores.error() + " No se ha podido conectar con la base de datos.")
                println(MenuColores.info() + " Se va a proceder con el " + MenuColores.azul("sistema de ficheros."))
                Gestores.establecerFicheros()
            }
        }
    }

    //Añade un administrador y un Staff para Debug
    if (Gestores.gestorUsuarios.obtenerUsuario("admin@test.es") == null) {
        Gestores.gestorUsuarios.añadirUsuario(Usuario("Admin", "Root", 0, "admin@test.es", "12345", Roles.ADMINISTRADOR))
    }
    if (Gestores.gestorUsuarios.obtenerUsuario("staff@test.es") == null) {
        Gestores.gestorUsuarios.añadirUsuario(Usuario("Staff", "Root", 0, "staff@test.es", "12345", Roles.STAFF))
    }

    val menuLogin = MenuLogin()

    do {
        menuLogin.imprimirOpciones()
        val opcion = InputsMenus.seleccionarOpcionMenu(3)
        when (opcion) {
            1 -> {
                val usuario = menuLogin.iniciarSesion()
                if (usuario != null){
                    val menuJuego = MenuJuego(usuario)

                    when (usuario.rol) {
                        Roles.ADMINISTRADOR -> {
                            //Si el usuario es Administrador, pide a donde ir
                            when (menuLogin.pedirAdmin(usuario.email)) {
                                1 -> {
                                    menuJuego.juego()
                                }

                                2 -> {
                                    val menuAdmin = MenuAdmin()
                                    menuAdmin.menuAdmin(usuario.email)
                                }
                            }
                        }
                        Roles.STAFF -> {
                            //Si el usuario es Administrador NO ESTANDAR, va al menuAdmin
                            val menuAdmin = MenuAdmin()
                            menuAdmin.menuAdmin(usuario.email)

                        }
                        else -> {
                            menuJuego.juego()
                        }
                    }
                }
            }
            2 -> {
                Gestores.gestorUsuarios.añadirUsuario(Usuario(InputsRegistro.introducirNombre(), InputsRegistro.introducirApellidos(), InputsRegistro.introducirEdad(), InputsRegistro.introducirEmail(), InputsRegistro.introducirContrasenia(), Roles.ESTANDAR))
            }
        }

    } while (opcion != 3)
}
